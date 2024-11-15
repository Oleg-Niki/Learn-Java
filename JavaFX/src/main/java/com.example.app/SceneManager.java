import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

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
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFile));
            return new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
