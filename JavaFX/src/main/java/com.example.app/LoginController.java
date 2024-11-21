package com.example.bankaccountapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    //database address and credentials
    private final String DATABASE_URL = "jdbc:postgresql://us-west-1.97ec9ded-3d43-498d-b5ab-59bf6e92effa.aws.yugabyte.cloud:5433/yugabyte?ssl=true&sslmode=require";
    private final String DATABASE_USER = "db_admin";
    private final String DATABASE_PASSWORD = "securepassword";

    // FXML-linked TextField for the username input. This field allows the user to enter their username.
    @FXML
    private TextField usernameField;
    // FXML-linked PasswordField for the password input. This field is used to securely input the user's password, hiding the text as they type.
    @FXML
    private PasswordField passwordField;
    // FXML-linked Button for login functionality. When clicked, this button triggers the login process defined in the handleLogin method.
    @FXML
    private Button loginButton;

    /**
     * Handles the login process.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Username or password cannot be empty!");
            return;
        }

        if (validateLogin(username, password)) {
            showAlert("Success", "Login successful!");
            // Navigate to the main menu and pass the username
            proceedToMainMenu(username);
        } else {
            showAlert("Login Error", "Invalid username or password.");
        }
    }
     /**
     * Load main menu
     */
    private void proceedToMainMenu(String username) {
        try {
            // Load the main menu FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankaccountapp/main.fxml"));
            Parent root = loader.load();

            // Access the MainController instance and set the username
            MainController mainController = loader.getController();
            mainController.setWelcomeMessage(username);

            // Set the main menu scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to load the main menu.");
            e.printStackTrace();
        }
    }

    /**
     * Opens the sign-up popup window.
     */
    @FXML
    private void handleSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bankaccountapp/signup.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Modal popup
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Wait until the sign-up window is closed

        } catch (Exception e) {
            showAlert("Error", "Failed to load the sign-up window.");
            e.printStackTrace();
        }
    }

    /**
     * Validates login credentials by querying the database.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return true if the credentials are valid, false otherwise.
     */
    private boolean validateLogin(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return storedPassword.equals(password); // Simple validation
            } else {
                System.out.println("Username not found in the database.");
            }

        } catch (SQLException e) {
            showAlert("Database Error", "Error validating login credentials.");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Displays an alert dialog with the specified title and content.
     *
     * @param title   The title of the alert.
     * @param content The content of the alert.
     */
    private void showAlert(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**
     * Handles the exit process.
     */
    @FXML
    private void handleExit() {
        System.out.println("Exiting application...");
        System.exit(0); // Closes the application
    }
}
