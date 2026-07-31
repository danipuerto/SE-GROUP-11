package com.grocery.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productRepository);
    }

    @Test
    void getAllProductsShouldReturnAllProducts() {
        Product apples = new Product();
        apples.setId(1);
        apples.setName("Apples");

        Product milk = new Product();
        milk.setId(2);
        milk.setName("Milk");

        when(productRepository.findAll())
                .thenReturn(List.of(apples, milk));

        List<Product> result = productController.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Apples", result.get(0).getName());
        assertEquals("Milk", result.get(1).getName());

        verify(productRepository).findAll();
    }

    @Test
    void addProductShouldSaveProduct() {
        Product bread = new Product();
        bread.setName("Bread");
        bread.setPrice(new BigDecimal("3.49"));

        when(productRepository.save(bread))
                .thenReturn(bread);

        Product result = productController.addProduct(bread);

        assertSame(bread, result);
        assertEquals("Bread", result.getName());

        verify(productRepository).save(bread);
    }
}
