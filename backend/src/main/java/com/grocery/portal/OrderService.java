package com.grocery.portal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(ShoppingCartRepository shoppingCartRepository,
                        OrderRepository orderRepository,
                        DiscountCodeRepository discountCodeRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
        this.discountCodeRepository = discountCodeRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order checkout(int customerId, String discountCodeInput, DeliveryOption deliveryOption) {
        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId);
        List<CartItem> items = cart == null ? Collections.emptyList() : cart.getItems();
        Order order = priceOrder(customerId, items, discountCodeInput, deliveryOption);

        // Copy cart items into order items (snapshot price at purchase), and
        // draw down each product's stock by the quantity purchased.
        for (CartItem cartItem : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getItemPrice());
            order.getItems().add(orderItem);

            Product product = cartItem.getProduct();
            int remaining = Math.max(0, product.getQuantity() - cartItem.getQuantity());
            product.setQuantity(remaining);
            if (remaining == 0) {
                product.setSoldOut(true);
            }
            productRepository.save(product);
        }

        // Save order (cascade saves order items too), then clear the cart.
        // The cart's items collection is cascade + orphanRemoval, so it must be
        // cleared through the parent rather than deleted directly through the
        // repository — otherwise Hibernate re-inserts them when the cart is flushed.
        Order savedOrder = orderRepository.save(order);
        if (cart != null) {
            cart.getItems().clear();
            shoppingCartRepository.save(cart);
        }

        return savedOrder;
    }

    // Prices out the cart (subtotal, discount, tax, total) without saving anything,
    // so the UI can preview the final total for a discount code before placing the order.
    public Order previewCheckout(int customerId, String discountCodeInput, DeliveryOption deliveryOption) {
        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId);
        List<CartItem> items = cart == null ? Collections.emptyList() : cart.getItems();
        return priceOrder(customerId, items, discountCodeInput, deliveryOption);
    }

    private Order priceOrder(int customerId, List<CartItem> items, String discountCodeInput,
                             DeliveryOption deliveryOption) {

        // Subtotal: sum of (item price × quantity)
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem item : items) {
            BigDecimal lineTotal = item.getItemPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            subtotal = subtotal.add(lineTotal);
        }

        // Delivery fee from the chosen option
        BigDecimal deliveryFee = deliveryOption.getDeliveryFee();

        // Discount: apply only if the code exists and is active. The discount
        // rate applies to both the item subtotal and the delivery fee.
        BigDecimal discountRate = BigDecimal.ZERO;
        if (discountCodeInput != null && !discountCodeInput.isEmpty()) {
            DiscountCode code = discountCodeRepository.findByCode(discountCodeInput);
            if (code != null && code.isActive()) {
                discountRate = code.getValue();
            }
        }

        BigDecimal itemDiscount = subtotal.multiply(discountRate);
        BigDecimal deliveryDiscount = deliveryFee.multiply(discountRate);
        BigDecimal totalDiscount = itemDiscount.add(deliveryDiscount);

        BigDecimal discountedSubtotal = subtotal.subtract(itemDiscount);
        BigDecimal discountedDeliveryFee = deliveryFee.subtract(deliveryDiscount);

        // Tax: 8.25% on the discounted item subtotal
        BigDecimal taxRate = new BigDecimal("0.0825");
        BigDecimal tax = discountedSubtotal.multiply(taxRate);

        // Total
        BigDecimal total = discountedSubtotal.add(tax).add(discountedDeliveryFee);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setSubtotal(subtotal);
        order.setDiscount(totalDiscount);
        order.setTax(tax);
        order.setDeliveryFee(discountedDeliveryFee);
        order.setTotal(total);
        order.setStatus(OrderStatus.PLACED);

        return order;
    }
}