package com.grocery.portal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ProductController {
    private final ProductRepository productRepository;
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Admin-only in the client: lets an admin update stock quantity and
    // sold-out status for a product without touching its other fields.
    public record StockUpdate(int quantity, boolean soldOut) {
    }

    @PutMapping("/products/{id}/stock")
    public Product updateStock(@PathVariable int id, @RequestBody StockUpdate stockUpdate) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + id));

        product.setQuantity(stockUpdate.quantity());
        product.setSoldOut(stockUpdate.soldOut());

        return productRepository.save(product);
    }
}
