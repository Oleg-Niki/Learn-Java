import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button loginLink;

    private final String DATABASE_URL = "jdbc:postgresql://<your-yugabyte-cloud-host>:5433/<your-database-name>";
    private final String DATABASE_USER = "<your-username>";
    private final String DATABASE_PASSWORD = "<your-password>";

    @FXML
    public void initialize() {
        signUpButton.setOnAction(event -> handleSignUp());
        loginLink.setOnAction(event -> switchToLogin());
    }

    private void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Passwords do not match.");
            return;
        }

        if (saveUserToDatabase(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Account created successfully!");
            switchToLogin();
        }
    }

    private boolean saveUserToDatabase(String username, String password) {
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error saving user to the database: " + e.getMessage());
            return false;
        }
    }

    private void switchToLogin() {
        Scene scene = SceneManager.loadScene("login.fxml");
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
