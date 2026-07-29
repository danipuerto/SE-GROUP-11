package com.grocery.ui;

import com.grocery.ui.components.ProductCard;
import com.grocery.ui.model.Product;
import com.grocery.ui.service.ProductService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

import java.util.List;

public class GroceryController {

    @FXML
    private TilePane productContainer;

    @FXML
    private Label statusLabel;

    private final ProductService productService = new ProductService();

    @FXML
    public void initialize() {
        loadProducts();
    }

    private void loadProducts() {
        statusLabel.setText("Loading products...");
        productContainer.getChildren().clear();

        Thread loadingThread = new Thread(() -> {
            try {
                List<Product> products = productService.getProducts();

                Platform.runLater(() -> {
                    for (Product product : products) {
                        productContainer.getChildren().add(
                                new ProductCard(product)
                        );
                    }

                    statusLabel.setText(
                            products.size() + " products loaded."
                    );
                });

            } catch (Exception exception) {
                exception.printStackTrace();

                Platform.runLater(() ->
                        statusLabel.setText(
                                "Could not load products. Make sure the backend is running."
                        )
                );
            }
        });

        loadingThread.setDaemon(true);
        loadingThread.start();
    }

    @FXML
    private void refreshProducts() {
        loadProducts();
    }

    @FXML
    private void openCart() {
        statusLabel.setText("Shopping cart feature coming next.");
    }
}