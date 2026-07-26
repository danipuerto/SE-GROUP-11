package com.grocery.portal;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("/cart/{customerId}")
    public List<CartItem> viewCart(@PathVariable int customerId) {
        return cartService.getCartItems(customerId);
    }
    @PostMapping("/cart/{customerId}/add/{productId}")
    public CartItem addToCart(@PathVariable int customerId,
                              @PathVariable int productId,
                              @RequestParam(defaultValue = "1") int quantity) {
        return cartService.addItem(customerId, productId, quantity);
    }
    @DeleteMapping("/cart/{customerId}/remove/{productId}")
    public void removeFromCart(@PathVariable int customerId,
                               @PathVariable int productId) {
        cartService.removeItem(customerId, productId);
    }
}