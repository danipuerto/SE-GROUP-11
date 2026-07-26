package com.grocery.portal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(ShoppingCartRepository shoppingCartRepository,
        CartItemRepository cartItemRepository,
        CustomerRepository customerRepository,
        ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }
    private ShoppingCart getOrCreateCart(int customerId) {
        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId);

        if (cart == null) {
            Customer customer = customerRepository.findById(customerId).orElseThrow();
            cart = new ShoppingCart();
            cart.setCustomer(customer);
            cart.setCreatedDate(LocalDate.now());
            cart = shoppingCartRepository.save(cart);
        }

        return cart;
    }
    public CartItem addItem(int customerId, int productId, int quantity) {
        ShoppingCart cart = getOrCreateCart(customerId);
        Product product = productRepository.findById(productId).orElseThrow();

        // Option B: is this product already in the cart?
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(item.getQuantity() + quantity);   // bump existing
                return cartItemRepository.save(item);
            }
        }

        // Not in cart yet — create a new line
        CartItem newItem = new CartItem();
        newItem.setShoppingCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setItemPrice(product.getPrice());
        return cartItemRepository.save(newItem);
    }
    public List<CartItem> getCartItems(int customerId) {
        ShoppingCart cart = getOrCreateCart(customerId);
        return cart.getItems();
    }
    public void removeItem(int customerId, int productId) {
        ShoppingCart cart = getOrCreateCart(customerId);

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId() == productId) {
                cartItemRepository.delete(item);
                return;
            }
        }
    }
}