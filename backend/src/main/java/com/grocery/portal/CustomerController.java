package com.grocery.portal;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Customer existing = customerRepository.findByEmail(customer.getEmail());

        if (existing != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with that email already exists");
        }

        String hashed = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashed);
        customer.setAdmin(false); // admin rights are granted manually, never through self-registration
        return customerRepository.save(customer);
    }

    @PostMapping("/login")
    public Customer login(@RequestBody Customer loginRequest) {
        Customer found = customerRepository.findByEmail(loginRequest.getEmail());

        if (found == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), found.getPassword());

        if (!matches) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return found;
    }
}