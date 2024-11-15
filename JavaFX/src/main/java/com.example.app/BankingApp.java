import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankingApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Banking App");
        Scene scene = SceneManager.loadScene("login.fxml");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
