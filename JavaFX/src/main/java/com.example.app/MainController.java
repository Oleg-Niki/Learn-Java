package com.example.bankaccountapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    private final String DATABASE_URL = "jdbc:postgresql://us-west-1.97ec9ded-3d43-498d-b5ab-59bf6e92effa.aws.yugabyte.cloud:5433/yugabyte?ssl=true&sslmode=require";
    private final String DATABASE_USER = "db_admin";
    private final String DATABASE_PASSWORD = "securepassword";

    private String currentUsername;

    @FXML
    private Label welcomeUserField;

    @FXML
    private Button logoutButton;

    /**
     * Set the welcome message and store the current username.
     */
    public void setWelcomeMessage(String username) {
        this.currentUsername = username; // Store only the username
        welcomeUserField.setText("Welcome, " + username + "!"); // Display the welcome message
    }



    @FXML
    private void handleDeposit() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Deposit Funds");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the deposit amount:");

            dialog.showAndWait().ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount <= 0) {
                        showAlert("Invalid Input", "Deposit amount must be positive.");
                        return;
                    }

                    // Update balance
                    String query = "UPDATE users SET balance = balance + ? WHERE username = ?";
                    try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setDouble(1, amount);
                        preparedStatement.setString(2, currentUsername);
                        preparedStatement.executeUpdate();
                    }

                    // Log transaction
                    String transactionQuery = "INSERT INTO transactions (username, type, amount) VALUES (?, 'deposit', ?)";
                    try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                         PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery)) {
                        System.out.println("Current Username (before insert): " + currentUsername);
                        transactionStatement.setString(1, currentUsername); // Use correct username
                        transactionStatement.setDouble(2, amount);
                        transactionStatement.executeUpdate();
                    }


                    showAlert("Success", "Successfully deposited $" + amount + "!");
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number.");
                } catch (SQLException e) {
                    showAlert("Database Error", "Error processing the deposit.");
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWithdraw() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Withdraw Funds");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the withdrawal amount:");

            dialog.showAndWait().ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount <= 0) {
                        showAlert("Invalid Input", "Withdrawal amount must be positive.");
                        return;
                    }

                    String query = "SELECT balance FROM users WHERE username = ?";
                    try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                         PreparedStatement selectStatement = connection.prepareStatement(query)) {

                        selectStatement.setString(1, currentUsername);
                        ResultSet resultSet = selectStatement.executeQuery();

                        if (resultSet.next()) {
                            double currentBalance = resultSet.getDouble("balance");

                            if (currentBalance < amount) {
                                showAlert("Insufficient Funds", "You do not have sufficient funds for this withdrawal.");
                                return;
                            }

                            // Deduct the amount
                            query = "UPDATE users SET balance = balance - ? WHERE username = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
                                updateStatement.setDouble(1, amount);
                                updateStatement.setString(2, currentUsername);
                                updateStatement.executeUpdate();
                            }

                            // Log the withdrawal
                            String transactionQuery = "INSERT INTO transactions (username, type, amount) VALUES (?, 'withdraw', ?)";
                            try (PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery)) {
                                transactionStatement.setString(1, currentUsername);
                                transactionStatement.setDouble(2, amount);
                                transactionStatement.executeUpdate();
                            }

                            showAlert("Success", "Successfully withdrew $" + amount + "!");
                        }
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number.");
                } catch (SQLException e) {
                    showAlert("Database Error", "Error processing the withdrawal.");
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleStatement() {
        try {
            String balanceQuery = "SELECT balance FROM users WHERE username = ?";
            String transactionQuery = "SELECT type, amount, timestamp FROM transactions WHERE username = ? ORDER BY timestamp DESC";

            StringBuilder statementDetails = new StringBuilder();

            try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {

                // Get the current balance
                try (PreparedStatement balanceStatement = connection.prepareStatement(balanceQuery)) {
                    balanceStatement.setString(1, currentUsername);
                    ResultSet balanceResultSet = balanceStatement.executeQuery();

                    if (balanceResultSet.next()) {
                        double balance = balanceResultSet.getDouble("balance");
                        statementDetails.append("Current Balance: $").append(balance).append("\n\n");
                    }
                }

                // Get the transaction history
                statementDetails.append("Transaction History:\n");
                try (PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery)) {
                    transactionStatement.setString(1, currentUsername);
                    ResultSet transactionResultSet = transactionStatement.executeQuery();

                    while (transactionResultSet.next()) {
                        String type = transactionResultSet.getString("type");
                        double amount = transactionResultSet.getDouble("amount");
                        String timestamp = transactionResultSet.getTimestamp("timestamp").toString();

                        statementDetails.append(type)
                                .append(" - $")
                                .append(amount)
                                .append(" (")
                                .append(timestamp)
                                .append(")\n");
                    }
                }
            }

            showAlert("Account Statement", statementDetails.toString());

        } catch (SQLException e) {
            showAlert("Database Error", "Error fetching the account statement.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            // Go back to the login screen
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/example/bankaccountapp/login.fxml"));
            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to return to the login screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        // Close the application
        Stage stage = (Stage) welcomeUserField.getScene().getWindow(); // Replace `welcomeUserField` with a valid node
        stage.close();
    }

    @FXML
    private void handleDeleteAccount() {
        // Confirm deletion with the user
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Account");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete your account?");
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response.getText().equalsIgnoreCase("OK")) {
                // Delete account logic
                deleteAccountFromDatabase();
            }
        });
    }
    private void deleteAccountFromDatabase() {
        String deleteUserQuery = "DELETE FROM users WHERE username = ?";
        String deleteTransactionsQuery = "DELETE FROM transactions WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            // Delete transactions first due to foreign key constraints
            try (PreparedStatement deleteTransactions = connection.prepareStatement(deleteTransactionsQuery)) {
                deleteTransactions.setString(1, currentUsername);
                deleteTransactions.executeUpdate();
            }

            // Delete user account
            try (PreparedStatement deleteUser = connection.prepareStatement(deleteUserQuery)) {
                deleteUser.setString(1, currentUsername);
                deleteUser.executeUpdate();
            }

            // Notify user
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Account Deleted");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Your account has been deleted successfully.");
            successAlert.showAndWait();

            // Close the application or return to login screen
            handleClose();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to delete account. Please try again.");
            errorAlert.showAndWait();
        }
    }
    @FXML
    private void handleAbout() {
        // Show an about dialog
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Built for CIS 254 Java class: Banking App v1.0");
        aboutAlert.setContentText("This app allows you to manage your bank account, perform deposits and withdrawals, and view transaction history.\n\nFor support, contact oleg@oleg-nik.com");
        aboutAlert.showAndWait();
    }

    private void showAlert(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
