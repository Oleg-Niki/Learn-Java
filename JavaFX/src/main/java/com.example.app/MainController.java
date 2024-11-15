import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Button depositButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button statementButton;
    @FXML
    private Button logoutButton;

    private double balance = 0.0;

    @FXML
    public void initialize() {
        depositButton.setOnAction(event -> handleDeposit());
        withdrawButton.setOnAction(event -> handleWithdraw());
        statementButton.setOnAction(event -> handleStatement());
        logoutButton.setOnAction(event -> handleLogout());
    }

    private void handleDeposit() {
        balance += 100; // Replace with input dialog
        showAlert("Deposit Successful", "New Balance: $" + balance);
    }

    private void handleWithdraw() {
        if (balance >= 50) {
            balance -= 50; // Replace with input dialog
            showAlert("Withdrawal Successful", "New Balance: $" + balance);
        } else {
            showAlert("Insufficient Balance", "Balance: $" + balance);
        }
    }

    private void handleStatement() {
        showAlert("Account Statement", "Current Balance: $" + balance);
    }

    private void handleLogout() {
        Scene scene = SceneManager.loadScene("login.fxml");
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
