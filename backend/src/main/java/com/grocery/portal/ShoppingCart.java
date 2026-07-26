package com.grocery.portal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue
    private int id;
    private LocalDate createdDate;
    @OneToMany(mappedBy = "shoppingCart")
    private List<CartItem> items;
    @OneToOne
    @JsonIgnore
    private Customer customer;

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public int getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
