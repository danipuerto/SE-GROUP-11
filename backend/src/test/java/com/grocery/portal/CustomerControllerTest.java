package com.grocery.portal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerController =
                new CustomerController(customerRepository, passwordEncoder);
    }

    @Test
    void registerShouldEncodePasswordAndSaveCustomer() {
        Customer customer = new Customer();
        customer.setEmail("customer@email.com");
        customer.setPassword("plainPassword");

        when(passwordEncoder.encode("plainPassword"))
                .thenReturn("encodedPassword");

        when(customerRepository.save(customer))
                .thenReturn(customer);

        Customer result = customerController.register(customer);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());

        verify(passwordEncoder).encode("plainPassword");
        verify(customerRepository).save(customer);
    }

    @Test
    void loginShouldReturnCustomerWhenPasswordMatches() {
        Customer loginRequest = new Customer();
        loginRequest.setEmail("customer@email.com");
        loginRequest.setPassword("plainPassword");

        Customer storedCustomer = new Customer();
        storedCustomer.setEmail("customer@email.com");
        storedCustomer.setPassword("encodedPassword");

        when(customerRepository.findByEmail("customer@email.com"))
                .thenReturn(storedCustomer);

        when(passwordEncoder.matches(
                "plainPassword",
                "encodedPassword"
        )).thenReturn(true);

        Customer result = customerController.login(loginRequest);

        assertNotNull(result);
        assertEquals("customer@email.com", result.getEmail());

        verify(customerRepository)
                .findByEmail("customer@email.com");

        verify(passwordEncoder).matches(
                "plainPassword",
                "encodedPassword"
        );
    }

    @Test
    void loginShouldReturnNullWhenCustomerDoesNotExist() {
        Customer loginRequest = new Customer();
        loginRequest.setEmail("missing@email.com");
        loginRequest.setPassword("password");

        when(customerRepository.findByEmail("missing@email.com"))
                .thenReturn(null);

        Customer result = customerController.login(loginRequest);

        assertNull(result);

        verify(customerRepository)
                .findByEmail("missing@email.com");

        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void loginShouldReturnNullWhenPasswordDoesNotMatch() {
        Customer loginRequest = new Customer();
        loginRequest.setEmail("customer@email.com");
        loginRequest.setPassword("wrongPassword");

        Customer storedCustomer = new Customer();
        storedCustomer.setEmail("customer@email.com");
        storedCustomer.setPassword("encodedPassword");

        when(customerRepository.findByEmail("customer@email.com"))
                .thenReturn(storedCustomer);

        when(passwordEncoder.matches(
                "wrongPassword",
                "encodedPassword"
        )).thenReturn(false);

        Customer result = customerController.login(loginRequest);

        assertNull(result);

        verify(customerRepository)
                .findByEmail("customer@email.com");

        verify(passwordEncoder).matches(
                "wrongPassword",
                "encodedPassword"
        );
    }
}