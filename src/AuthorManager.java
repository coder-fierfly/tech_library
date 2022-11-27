import java.io.IOException;
import java.util.logging.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;

public class AuthorManager {
    public static Scene scene;

    public AuthorManager(Scene scene) {
        AuthorManager.scene = scene;
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("fx/authorization.fxml")
            );
            scene.setRoot(loader.load());
            AuthorController controller =
                    loader.getController();
            controller.initManager();
        } catch (IOException ex) {
            Logger.getLogger(AuthorManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}