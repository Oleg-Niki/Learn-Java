import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink signupLink;

    // Replace with actual validation
    private final String validUsername = "user";
    private final String validPassword = "pass";

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        signupLink.setOnAction(event -> switchToSignUp());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals(validUsername) && password.equals(validPassword)) {
            switchToMain();
        } else {
            showAlert("Invalid username or password");
        }
    }

    private void switchToSignUp() {
        Scene scene = SceneManager.loadScene("signup.fxml");
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
    }

    private void switchToMain() {
        Scene scene = SceneManager.loadScene("main.fxml");
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
