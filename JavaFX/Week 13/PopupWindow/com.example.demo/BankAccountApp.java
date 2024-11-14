package com.example.demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankAccountApp {

    // JDBC URL, username, and password
    private static final String URL = "jdbc:postgresql://us-west-1.97ec9ded-3d43-498d-b5ab-59bf6e92effa.aws.yugabyte.cloud:5433/yugabyte";
    private static final String USER = "db_admin";
    private static final String PASSWORD = "securepassword";  // Change to your password

    public static void main(String[] args) {
        // Example usage of the method
        addAccount("Oleg Nik", 1000.50, "checking");
    }

    public static void addAccount(String accountHolderName, double balance, String accountType) {
        String sql = "INSERT INTO accounts (account_holder_name, balance, account_type) VALUES (?, ?, ?)";

        // Establishing a connection to the database
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Create a PreparedStatement to prevent SQL injection
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Set the parameters for the prepared statement
                pstmt.setString(1, accountHolderName);
                pstmt.setDouble(2, balance);
                pstmt.setString(3, accountType);

                // Execute the query
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Account successfully added!");
                } else {
                    System.out.println("Failed to add the account.");
                }

            } catch (SQLException e) {
                System.out.println("Error while inserting account: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
