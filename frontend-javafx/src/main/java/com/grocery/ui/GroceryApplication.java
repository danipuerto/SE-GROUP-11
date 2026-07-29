package com.grocery.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GroceryApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                GroceryApplication.class.getResource(
                        "/com/grocery/ui/grocery-view.fxml"
                )
        );

        Scene scene = new Scene(loader.load(), 1100, 720);

        stage.setTitle("Online Grocery Store");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}