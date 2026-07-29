package com.grocery.ui.components;

import com.grocery.ui.model.Product;
import com.grocery.ui.service.CartService;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProductCard extends VBox {

    private final CartService cartService = new CartService();

    public ProductCard(Product product) {

        setSpacing(10);
        setPadding(new Insets(15));
        setPrefWidth(220);

        setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 12;
                -fx-border-color: #dddddd;
                -fx-border-radius: 12;
                """);

        // Product Emoji
        Label emojiLabel = new Label(getEmoji(product.getName()));
        emojiLabel.setStyle("-fx-font-size: 34;");

        // Product Name
        Label nameLabel = new Label(product.getName());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("""
                -fx-font-size: 18;
                -fx-font-weight: bold;
                """);

        // Description
        Label descriptionLabel = new Label(product.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-text-fill: #687068;");

        // Quantity
        Label quantityLabel = new Label(
                "In Stock: " + product.getQuantity()
        );
        quantityLabel.setStyle("-fx-text-fill: #687068;");

        // Price
        Label priceLabel = new Label(
                "$" + String.format("%.2f", product.getPrice())
        );
        priceLabel.setStyle("""
                -fx-font-size: 16;
                -fx-font-weight: bold;
                -fx-text-fill: #245c3b;
                """);

        // Add Button
        Button addButton = new Button("Add to Cart");
        addButton.setMaxWidth(Double.MAX_VALUE);

        addButton.setStyle("""
                -fx-background-color: #245c3b;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 7;
                -fx-padding: 9;
                """);

        addButton.setOnAction(e -> {

            try {

                cartService.addToCart(product.getId());

                addButton.setText("✓ Added");

            } catch (Exception ex) {

                ex.printStackTrace();

                addButton.setText("Error");

            }

        });

        getChildren().addAll(
                emojiLabel,
                nameLabel,
                descriptionLabel,
                quantityLabel,
                priceLabel,
                addButton
        );
    }

    private String getEmoji(String productName) {

        String name = productName.toLowerCase();

        if (name.contains("banana")) return "🍌";
        if (name.contains("milk")) return "🥛";
        if (name.contains("egg")) return "🥚";
        if (name.contains("bread")) return "🍞";
        if (name.contains("chicken")) return "🍗";
        if (name.contains("apple")) return "🍎";
        if (name.contains("rice")) return "🍚";
        if (name.contains("coffee")) return "☕";

        return "🛒";
    }
}