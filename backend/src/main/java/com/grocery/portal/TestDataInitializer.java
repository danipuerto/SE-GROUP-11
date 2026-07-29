package com.grocery.portal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public TestDataInitializer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (customerRepository.findById(1).isEmpty()) {

            Customer customer = new Customer();

            customer.setFirstName("Test");
            customer.setLastName("Customer");
            customer.setEmail("test@grocery.com");
            customer.setPassword(passwordEncoder.encode("password"));
            customer.setPhone("2105551234");

            Customer savedCustomer = customerRepository.save(customer);

            System.out.println(
                    "Created test customer with ID: "
                            + savedCustomer.getId()
            );
        } else {
            System.out.println("Test customer ID 1 already exists.");
        }

        if (customerRepository.findByEmail("admin@grocery.com") == null) {

            Customer admin = new Customer();

            admin.setFirstName("Store");
            admin.setLastName("Admin");
            admin.setEmail("admin@grocery.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhone("2105559999");
            admin.setAdmin(true);

            Customer savedAdmin = customerRepository.save(admin);

            System.out.println(
                    "Created admin customer with ID: "
                            + savedAdmin.getId()
            );
        } else {
            System.out.println("Admin customer already exists.");
        }
    }
}