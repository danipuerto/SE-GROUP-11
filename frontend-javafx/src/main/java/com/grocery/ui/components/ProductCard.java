package com.grocery.ui.components;

import com.grocery.ui.model.Product;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProductCard extends VBox {

    public ProductCard(Product product) {

        setSpacing(10);
        setPadding(new Insets(15));

        setPrefWidth(220);

        setStyle("""
            -fx-background-color:white;
            -fx-background-radius:12;
            -fx-border-color:#dddddd;
            -fx-border-radius:12;
        """);

        Label emoji = new Label(product.getEmoji());
        emoji.setStyle("-fx-font-size:34;");

        Label name = new Label(product.getName());
        name.setStyle("-fx-font-size:18; -fx-font-weight:bold;");

        Label category = new Label(product.getCategory());

        Label price = new Label("$" + String.format("%.2f", product.getPrice()));
        price.setStyle("-fx-font-size:16; -fx-text-fill:#245c3b;");

        Button add = new Button("Add to Cart");
        add.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(
                emoji,
                name,
                category,
                price,
                add
        );
    }
}