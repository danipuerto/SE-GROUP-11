package com.grocery.portal;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class OrderService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(ShoppingCartRepository shoppingCartRepository,
                        OrderRepository orderRepository,
                        DiscountCodeRepository discountCodeRepository,
                        CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
        this.discountCodeRepository = discountCodeRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Order checkout(int customerId, String discountCodeInput, DeliveryOption deliveryOption) {
        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId);

        // Subtotal: sum of (item price × quantity)
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            BigDecimal lineTotal = item.getItemPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            subtotal = subtotal.add(lineTotal);
        }

        // Discount: apply only if the code exists and is active
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (discountCodeInput != null && !discountCodeInput.isEmpty()) {
            DiscountCode code = discountCodeRepository.findByCode(discountCodeInput);
            if (code != null && code.isActive()) {
                discountAmount = subtotal.multiply(code.getPercentage());
            }
        }

        BigDecimal discountedSubtotal = subtotal.subtract(discountAmount);

        // Tax: 8.25% on the discounted subtotal
        BigDecimal taxRate = new BigDecimal("0.0825");
        BigDecimal tax = discountedSubtotal.multiply(taxRate);

        // Delivery fee from the chosen option
        BigDecimal deliveryFee = deliveryOption.getDeliveryFee();

        // Total
        BigDecimal total = discountedSubtotal.add(tax).add(deliveryFee);

        // Build the order
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderDate(LocalDate.now());
        order.setSubtotal(subtotal);
        order.setDiscount(discountAmount);
        order.setTax(tax);
        order.setDeliveryFee(deliveryFee);
        order.setTotal(total);
        order.setStatus(OrderStatus.PLACED);

        // Copy cart items into order items (snapshot price at purchase)
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getItemPrice());
            order.getItems().add(orderItem);
        }

        // Save order (cascade saves order items too), then clear the cart
        Order savedOrder = orderRepository.save(order);
        cartItemRepository.deleteAll(cart.getItems());

        return savedOrder;
    }
}