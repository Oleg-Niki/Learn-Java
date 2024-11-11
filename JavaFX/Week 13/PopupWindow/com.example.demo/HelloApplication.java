package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML or create main layout
        Button popupButton = new Button("Open Popup");

        // Set the action to open the popup when clicked
        popupButton.setOnAction(e -> {
            PopUpWindow popup = new PopUpWindow();
            popup.show(); // Show the popup window
        });

        VBox layout = new VBox(10, popupButton); // Add the button to the layout
        Scene scene = new Scene(layout, 320, 240);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
