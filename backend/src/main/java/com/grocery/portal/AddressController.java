package com.grocery.portal;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressController(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/customers/{customerId}/addresses")
    public Address addAddress(@PathVariable int customerId, @RequestBody Address address) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        if (!address.validateAddress()) {
            throw new IllegalArgumentException("Address is missing required fields " +
                    "or has an invalid zip code");
        }

        address.setCustomer(customer);
        return addressRepository.save(address);
    }

    @GetMapping("/customers/{customerId}/addresses")
    public List<Address> getAddresses(@PathVariable int customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    @DeleteMapping("/addresses/{addressId}")
    public void removeAddress(@PathVariable int addressId) {
        addressRepository.deleteById(addressId);
    }
}
