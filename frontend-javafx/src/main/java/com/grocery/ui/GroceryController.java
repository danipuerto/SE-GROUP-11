package com.grocery.ui;

import com.grocery.ui.components.ProductCard;
import com.grocery.ui.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

public class GroceryController {

    @FXML
    private TilePane productContainer;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        loadSampleProducts();
    }

    private void loadSampleProducts() {
        productContainer.getChildren().clear();

        productContainer.getChildren().addAll(
                new ProductCard(
                        new Product("Bananas", "Fruit", 1.99, "🍌")
                ),
                new ProductCard(
                        new Product("Milk", "Dairy", 4.29, "🥛")
                ),
                new ProductCard(
                        new Product("Steak", "Meat", 12.99, "🥩")
                ),
                new ProductCard(
                        new Product("Bread", "Bakery", 3.49, "🍞")
                ),
                new ProductCard(
                        new Product("Eggs", "Dairy", 5.49, "🥚")
                ),
                new ProductCard(
                        new Product("Apples", "Fruit", 2.99, "🍎")
                )
        );

        statusLabel.setText(
                productContainer.getChildren().size() + " products loaded."
        );
    }

    @FXML
    private void refreshProducts() {
        loadSampleProducts();
        statusLabel.setText("Products refreshed.");
    }

    @FXML
    private void openCart() {
        statusLabel.setText("Your cart is currently empty.");
    }
}