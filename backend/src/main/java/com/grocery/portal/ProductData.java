package com.grocery.portal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductData implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductData(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            productRepository.save(createProduct("Apples", "Fresh red apples from Texas", "1.98", "apple.jpg", 7));
            productRepository.save(createProduct("Carrots", "Fresh orange carrots from Texas", "2.29", "carrot.jpg", 5));
            productRepository.save(createProduct("Eggs", "Grass-fed eggs", "4.99", "eggs.jpg", 12));
            productRepository.save(createProduct("Bread", "100% whole wheat bread loaf", "2.49", "bread.jpg", 1));
            productRepository.save(createProduct("Chicken Tenders", "3pc fried to perfection", "6.99", "chicken_tenders.jpg", 3));
        }
    }

    private Product createProduct(String name, String description, String price, String image, int quantity) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(new BigDecimal(price));
        product.setImage(image);
        product.setQuantity(quantity);
        return product;
    }
}
