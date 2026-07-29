package com.grocery.ui;

import com.grocery.ui.components.ProductCard;
import com.grocery.ui.model.CartItem;
import com.grocery.ui.model.Product;
import com.grocery.ui.service.CartService;
import com.grocery.ui.service.ProductService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class GroceryController {

    @FXML
    private TilePane productContainer;

    @FXML
    private Label statusLabel;

    private final ProductService productService =
            new ProductService();

    private final CartService cartService =
            new CartService();

    @FXML
    public void initialize() {
        loadProducts();
    }

    private void loadProducts() {
        statusLabel.setText("Loading products...");
        productContainer.getChildren().clear();

        Thread loadingThread = new Thread(() -> {
            try {
                List<Product> products =
                        productService.getProducts();

                Platform.runLater(() -> {
                    for (Product product : products) {
                        productContainer.getChildren().add(
                                new ProductCard(product)
                        );
                    }

                    statusLabel.setText(
                            products.size()
                                    + " products loaded."
                    );
                });

            } catch (Exception exception) {
                exception.printStackTrace();

                Platform.runLater(() ->
                        statusLabel.setText(
                                "Could not load products. "
                                        + "Make sure the backend is running."
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
        statusLabel.setText("Loading shopping cart...");

        Thread cartThread = new Thread(() -> {
            try {
                List<CartItem> cartItems =
                        cartService.getCartItems();

                Platform.runLater(() -> {
                    showCartWindow(cartItems);
                    statusLabel.setText(
                            cartItems.size()
                                    + " cart item(s) loaded."
                    );
                });

            } catch (Exception exception) {
                exception.printStackTrace();

                Platform.runLater(() ->
                        statusLabel.setText(
                                "Could not load cart. "
                                        + "Make sure the backend is running."
                        )
                );
            }
        });

        cartThread.setDaemon(true);
        cartThread.start();
    }

    private void showCartWindow(List<CartItem> cartItems) {
        Stage cartStage = new Stage();

        cartStage.setTitle("Shopping Cart");
        cartStage.initModality(Modality.APPLICATION_MODAL);

        Label titleLabel = new Label("Shopping Cart");
        titleLabel.setStyle(
                "-fx-font-size: 26px;"
                        + "-fx-font-weight: bold;"
        );

        VBox cartItemsBox = new VBox(12);
        cartItemsBox.setPadding(new Insets(10));

        Label totalLabel = new Label();
        totalLabel.setStyle(
                "-fx-font-size: 18px;"
                        + "-fx-font-weight: bold;"
        );

        if (cartItems.isEmpty()) {
            Label emptyLabel =
                    new Label("Your shopping cart is empty.");

            emptyLabel.setStyle("-fx-font-size: 16px;");

            cartItemsBox.getChildren().add(emptyLabel);

        } else {
            for (CartItem cartItem : cartItems) {
                HBox itemRow = createCartItemRow(
                        cartItem,
                        cartItemsBox,
                        totalLabel
                );

                cartItemsBox.getChildren().add(itemRow);
                cartItemsBox.getChildren().add(
                        new Separator()
                );
            }
        }

        updateTotal(cartItemsBox, totalLabel);

        ScrollPane scrollPane =
                new ScrollPane(cartItemsBox);

        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(350);

        Button closeButton = new Button("Continue Shopping");
        closeButton.setOnAction(
                event -> cartStage.close()
        );

        HBox bottomBar = new HBox(20);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.setPadding(new Insets(15));
        bottomBar.getChildren().addAll(
                totalLabel,
                closeButton
        );

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setTop(titleLabel);
        root.setCenter(scrollPane);
        root.setBottom(bottomBar);

        BorderPane.setMargin(
                titleLabel,
                new Insets(0, 0, 15, 0)
        );

        Scene scene = new Scene(root, 600, 500);

        cartStage.setScene(scene);
        cartStage.showAndWait();
    }

    private HBox createCartItemRow(
            CartItem cartItem,
            VBox cartItemsBox,
            Label totalLabel
    ) {
        Product product = cartItem.getProduct();

        String productName =
                product == null
                        ? "Unknown Product"
                        : product.getName();

        Label nameLabel = new Label(productName);
        nameLabel.setStyle(
                "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
        );

        Label quantityLabel = new Label(
                "Quantity: " + cartItem.getQuantity()
        );

        Label priceLabel = new Label(
                String.format(
                        "$%.2f",
                        cartItem.getTotalPrice()
                )
        );

        priceLabel.setStyle(
                "-fx-font-size: 15px;"
                        + "-fx-font-weight: bold;"
        );

        VBox productInformation = new VBox(
                5,
                nameLabel,
                quantityLabel
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button removeButton = new Button("Remove");

        removeButton.setOnAction(event -> {
            if (product == null) {
                return;
            }

            removeButton.setDisable(true);
            removeButton.setText("Removing...");

            Thread removeThread = new Thread(() -> {
                try {
                    cartService.removeItem(
                            product.getId()
                    );

                    Platform.runLater(() -> {
                        removeCartRow(
                                cartItemsBox,
                                removeButton,
                                totalLabel
                        );

                        statusLabel.setText(
                                productName
                                        + " removed from cart."
                        );
                    });

                } catch (Exception exception) {
                    exception.printStackTrace();

                    Platform.runLater(() -> {
                        removeButton.setDisable(false);
                        removeButton.setText("Remove");

                        statusLabel.setText(
                                "Could not remove "
                                        + productName
                                        + "."
                        );
                    });
                }
            });

            removeThread.setDaemon(true);
            removeThread.start();
        });

        HBox itemRow = new HBox(
                15,
                productInformation,
                spacer,
                priceLabel,
                removeButton
        );

        itemRow.setAlignment(Pos.CENTER_LEFT);
        itemRow.setPadding(new Insets(10));
        itemRow.setUserData(cartItem);

        return itemRow;
    }

    private void removeCartRow(
            VBox cartItemsBox,
            Button removeButton,
            Label totalLabel
    ) {
        HBox itemRow =
                (HBox) removeButton.getParent();

        int rowIndex =
                cartItemsBox.getChildren().indexOf(itemRow);

        if (rowIndex >= 0) {
            cartItemsBox.getChildren().remove(itemRow);

            if (rowIndex
                    < cartItemsBox.getChildren().size()
                    && cartItemsBox.getChildren()
                    .get(rowIndex) instanceof Separator) {

                cartItemsBox.getChildren().remove(rowIndex);
            }
        }

        boolean hasCartItems =
                cartItemsBox.getChildren()
                        .stream()
                        .anyMatch(node -> node instanceof HBox);

        if (!hasCartItems) {
            cartItemsBox.getChildren().clear();

            Label emptyLabel =
                    new Label("Your shopping cart is empty.");

            emptyLabel.setStyle("-fx-font-size: 16px;");

            cartItemsBox.getChildren().add(emptyLabel);
        }

        updateTotal(cartItemsBox, totalLabel);
    }

    private void updateTotal(
            VBox cartItemsBox,
            Label totalLabel
    ) {
        double total = 0.0;

        for (javafx.scene.Node node
                : cartItemsBox.getChildren()) {

            if (node instanceof HBox itemRow
                    && itemRow.getUserData()
                    instanceof CartItem cartItem) {

                total += cartItem.getTotalPrice();
            }
        }

        totalLabel.setText(
                String.format("Total: $%.2f", total)
        );
    }
}