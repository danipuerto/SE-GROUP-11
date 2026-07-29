package com.grocery.portal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService(
                shoppingCartRepository,
                cartItemRepository,
                customerRepository,
                productRepository
        );
    }

    @Test
    void addItemShouldAddNewProductToExistingCart() {
        int customerId = 1;
        int productId = 10;
        int quantity = 2;

        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new ArrayList<>());

        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("4.99"));

        when(shoppingCartRepository.findByCustomerId(customerId))
                .thenReturn(cart);

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(cartItemRepository.save(any(CartItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CartItem result = cartService.addItem(
                customerId,
                productId,
                quantity
        );

        assertNotNull(result);
        assertEquals(product, result.getProduct());
        assertEquals(quantity, result.getQuantity());
        assertEquals(new BigDecimal("4.99"), result.getItemPrice());
        assertEquals(cart, result.getShoppingCart());
        assertEquals(1, cart.getItems().size());

        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    void addItemShouldIncreaseQuantityWhenProductAlreadyExists() {
        int customerId = 1;
        int productId = 10;

        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("4.99"));

        CartItem existingItem = new CartItem();
        existingItem.setProduct(product);
        existingItem.setQuantity(2);

        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new ArrayList<>(List.of(existingItem)));

        when(shoppingCartRepository.findByCustomerId(customerId))
                .thenReturn(cart);

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(cartItemRepository.save(existingItem))
                .thenReturn(existingItem);

        CartItem result = cartService.addItem(
                customerId,
                productId,
                3
        );

        assertEquals(5, result.getQuantity());
        assertEquals(1, cart.getItems().size());

        verify(cartItemRepository).save(existingItem);
    }

    @Test
    void addItemShouldRejectZeroQuantity() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cartService.addItem(1, 10, 0)
        );

        assertEquals(
                "Quantity must be greater than zero.",
                exception.getMessage()
        );

        verifyNoInteractions(
                shoppingCartRepository,
                productRepository,
                cartItemRepository
        );
    }

    @Test
    void addItemShouldRejectNegativeQuantity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> cartService.addItem(1, 10, -2)
        );

        verifyNoInteractions(
                shoppingCartRepository,
                productRepository,
                cartItemRepository
        );
    }

    @Test
    void addItemShouldThrowWhenProductDoesNotExist() {
        int customerId = 1;
        int productId = 99;

        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new ArrayList<>());

        when(shoppingCartRepository.findByCustomerId(customerId))
                .thenReturn(cart);

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cartService.addItem(customerId, productId, 1)
        );

        assertEquals(
                "Product not found with ID: 99",
                exception.getMessage()
        );

        verify(cartItemRepository, never())
                .save(any(CartItem.class));
    }

    @Test
    void getCartItemsShouldReturnItemsFromExistingCart() {
        Product product = new Product();
        product.setId(10);

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(2);

        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new ArrayList<>(List.of(item)));

        when(shoppingCartRepository.findByCustomerId(1))
                .thenReturn(cart);

        List<CartItem> result =
                cartService.getCartItems(1);

        assertEquals(1, result.size());
        assertEquals(item, result.get(0));
    }

    @Test
    void removeItemShouldRemoveProductFromCart() {
        int customerId = 1;
        int productId = 10;

        Product product = new Product();
        product.setId(productId);

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(1);

        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new ArrayList<>(List.of(item)));

        when(shoppingCartRepository.findByCustomerId(customerId))
                .thenReturn(cart);

        cartService.removeItem(customerId, productId);

        assertTrue(cart.getItems().isEmpty());

        verify(cartItemRepository).delete(item);
    }

    @Test
    void removeItemShouldThrowWhenProductIsNotInCart() {
        int customerId = 1;
        int productId = 99;

        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new ArrayList<>());

        when(shoppingCartRepository.findByCustomerId(customerId))
                .thenReturn(cart);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cartService.removeItem(
                        customerId,
                        productId
                )
        );

        assertEquals(
                "Product 99 is not in customer 1's cart.",
                exception.getMessage()
        );

        verify(cartItemRepository, never())
                .delete(any(CartItem.class));
    }

    @Test
    void addItemShouldCreateCartWhenCustomerHasNoCart() {
        int customerId = 1;
        int productId = 10;

        Customer customer = new Customer();

        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("3.50"));

        when(shoppingCartRepository.findByCustomerId(customerId))
                .thenReturn(null);

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(shoppingCartRepository.save(any(ShoppingCart.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(cartItemRepository.save(any(CartItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CartItem result =
                cartService.addItem(customerId, productId, 1);

        assertNotNull(result);

        ArgumentCaptor<ShoppingCart> cartCaptor =
                ArgumentCaptor.forClass(ShoppingCart.class);

        verify(shoppingCartRepository)
                .save(cartCaptor.capture());

        ShoppingCart createdCart =
                cartCaptor.getValue();

        assertEquals(customer, createdCart.getCustomer());
        assertNotNull(createdCart.getCreatedDate());
        assertNotNull(createdCart.getItems());
    }
}