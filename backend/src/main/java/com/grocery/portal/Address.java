package com.grocery.portal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private int id;
    private String street;
    private String state;
    private String zipCode;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean validateAddress() {
        if (street == null || street.isBlank()) {
            return false;
        }
        if (state == null || state.isBlank()) {
            return false;
        }
        if (zipCode == null || !zipCode.matches("\\d{5}")) { // regex is 5 digits
            return false;
        }
        return true;
    }
}
