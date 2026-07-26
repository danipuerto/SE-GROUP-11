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
    @PostMapping("/login")
    public Customer login(@RequestBody Customer loginRequest) {
        Customer found = customerRepository.findByEmail(loginRequest.getEmail());

        if (found == null) {
            return null; // no customer with that email
        }

        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), found.getPassword());

        if (matches) {
            return found;
        } else {
            return null; // wrong password
        }
    }
}