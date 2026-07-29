package com.grocery.portal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(
        ShoppingCartRepository shoppingCartRepository,
        CartItemRepository cartItemRepository,
        CustomerRepository customerRepository,
        ProductRepository productRepository
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    private ShoppingCart getOrCreateCart(int customerId) {

        ShoppingCart cart =
            shoppingCartRepository.findByCustomerId(customerId);

        if (cart == null) {

            Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Customer not found with ID: " + customerId
                    )
                );

            cart = new ShoppingCart();
            cart.setCustomer(customer);
            cart.setCreatedDate(LocalDate.now());
            cart.setItems(new ArrayList<>());

            cart = shoppingCartRepository.save(cart);
        }

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        return cart;
    }

    @Transactional
    public CartItem addItem(
        int customerId,
        int productId,
        int quantity
    ) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be greater than zero."
            );
        }

        ShoppingCart cart = getOrCreateCart(customerId);

        Product product = productRepository
            .findById(productId)
            .orElseThrow(() ->
                new RuntimeException(
                    "Product not found with ID: " + productId
                )
            );

        for (CartItem item : cart.getItems()) {

            if (
                item.getProduct() != null
                && item.getProduct().getId() == productId
            ) {
                item.setQuantity(
                    item.getQuantity() + quantity
                );

                return cartItemRepository.save(item);
            }
        }

        CartItem newItem = new CartItem();
        newItem.setShoppingCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setItemPrice(product.getPrice());

        CartItem savedItem =
            cartItemRepository.save(newItem);

        cart.getItems().add(savedItem);

        return savedItem;
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCartItems(int customerId) {

        ShoppingCart cart = getOrCreateCart(customerId);

        return cart.getItems();
    }

    @Transactional
    public void removeItem(
        int customerId,
        int productId
    ) {
        ShoppingCart cart = getOrCreateCart(customerId);

        CartItem itemToRemove = null;

        for (CartItem item : cart.getItems()) {

            if (
                item.getProduct() != null
                && item.getProduct().getId() == productId
            ) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove == null) {
            throw new RuntimeException(
                "Product " + productId
                    + " is not in customer "
                    + customerId
                    + "'s cart."
            );
        }

        cart.getItems().remove(itemToRemove);
        cartItemRepository.delete(itemToRemove);
    }
}