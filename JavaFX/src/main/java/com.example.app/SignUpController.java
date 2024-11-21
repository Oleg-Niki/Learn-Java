package com.example.bankaccountapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {
    //database address and credentials
    private final String DATABASE_URL = "jdbc:postgresql://us-west-1.97ec9ded-3d43-498d-b5ab-59bf6e92effa.aws.yugabyte.cloud:5433/yugabyte?ssl=true&sslmode=require";
    private final String DATABASE_USER = "db_admin";
    private final String DATABASE_PASSWORD = "securepassword";

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField depositField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String depositText = depositField.getText();

        if (username.isEmpty() || password.isEmpty() || depositText.isEmpty()) {
            showAlert("Sign Up Error", "Username or password or deposit cannot be empty!");
            return;
        }
        // Validate deposit amount
        double deposit;
        try {
            deposit = Double.parseDouble(depositText);
            if (deposit < 0) {
                showAlert("Sign Up Error", "Initial deposit must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Sign Up Error", "Invalid deposit amount. Please enter a numeric value.");
            return;
        }

        if (isUsernameTaken(username)) {
            showAlert("Sign Up Error", "Username is already taken. Please choose a different one.");
            return;
        }

        String query = "INSERT INTO users (username, password, balance) VALUES (?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setDouble(3, deposit);
            preparedStatement.executeUpdate();

            showAlert("Success", "User registered successfully!");

            // Close the sign-up window
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            showAlert("Database Error", "Error registering user.");
            e.printStackTrace();
        }
    }
    //checking if the name is already exist in the database, otherwise another procedure required because we could have two John Doe but different in something else
    private boolean isUsernameTaken(String username) {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // If count > 0, the username already exists
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Error checking username.");
            e.printStackTrace();
        }

        return false;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        // Close the sign-up window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
