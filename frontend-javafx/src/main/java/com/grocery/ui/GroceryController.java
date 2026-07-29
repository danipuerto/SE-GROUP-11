package com.grocery.ui;

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
        statusLabel.setText("Grocery store UI loaded.");
    }

    @FXML
    private void refreshProducts() {
        statusLabel.setText("Refresh products clicked.");
    }

    @FXML
    private void openCart() {
        statusLabel.setText("Cart button clicked.");
    }
}