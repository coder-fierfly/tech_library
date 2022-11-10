import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/* TODO сделать 3 пользователей
  варики:
- как минимум можно сделать зарегистрированного пользователя и модера/админа
- сделать для зареганного пользователя свою "библиотеку"
- документы могут открывать только зарегистрированные пользователи
- модератор может добавлять доки, а админ может все
*/
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
        stage.setScene(scene);
        stage.show();
    }
}