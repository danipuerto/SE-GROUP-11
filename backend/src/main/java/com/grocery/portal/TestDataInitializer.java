package com.grocery.portal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public TestDataInitializer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {

        if (customerRepository.findById(1).isEmpty()) {

            Customer customer = new Customer();

            customer.setFirstName("Test");
            customer.setLastName("Customer");
            customer.setEmail("test@grocery.com");
            customer.setPassword("password");
            customer.setPhone("2105551234");

            Customer savedCustomer = customerRepository.save(customer);

            System.out.println(
                    "Created test customer with ID: "
                            + savedCustomer.getId()
            );
        } else {
            System.out.println("Test customer ID 1 already exists.");
        }
    }
}