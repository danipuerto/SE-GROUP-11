package com.grocery.ui.model;

public class Product {

    private final String name;
    private final String category;
    private final double price;
    private final String emoji;

    public Product(String name, String category, double price, String emoji) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.emoji = emoji;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getEmoji() {
        return emoji;
    }
}
