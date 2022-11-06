import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/* Main application class for the login demo application */
public class LoginDemoApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new StackPane());

        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

        stage.setScene(scene);
        stage.show();


        Parent root = (Parent) FXMLLoader.load((URL) Objects.requireNonNull(this.getClass().getResource("fx/login.fxml")));

        //Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}