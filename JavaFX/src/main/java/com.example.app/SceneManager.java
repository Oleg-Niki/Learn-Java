package com.example.bankaccountapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    /**
     * Loads an FXML file and returns the corresponding Scene object.
     *
     * @param fxmlFile The name of the FXML file to load (e.g., "login.fxml").
     * @return The Scene object for the FXML file, or null if an error occurs.
     */
    public static Scene loadScene(String fxmlFile) {
        try {
            // Use the class loader to locate the FXML file
            System.out.println("Loading FXML file: " + fxmlFile);
            String fxmlPath = "/com/example/bankaccountapp/" + fxmlFile;
            System.out.println("Loading FXML file: " + fxmlPath);
            URL resource = SceneManager.class.getResource(fxmlPath);
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            System.out.println("FXML file loaded successfully!");
            if (resource == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }
            return new Scene(loader.load());
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlFile);
            e.printStackTrace();
            return null;
        }
    }
}
