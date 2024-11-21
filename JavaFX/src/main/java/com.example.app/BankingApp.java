package com.example.bankaccountapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankingApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Application started!"); // Ensure this appears (debugging)
        primaryStage.setTitle("Oleg N - CIS 254 - Banking App");
        Scene scene = SceneManager.loadScene("login.fxml");
        if (scene == null) throw new Exception("Failed to load 'login.fxml'");
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("Primary stage is set up and displayed!"); // Ensure this appears (debugging)
    }

    public static void main(String[] args) {
        launch(args);
    }
}
