package com.grocery.portal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public Customer register(@RequestBody Customer customer) {
        String hashed = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashed);
        return customerRepository.save(customer);
    }
}