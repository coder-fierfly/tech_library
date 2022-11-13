import javafx.application.Application;
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
    public static Stage stage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stageLogin){
        stageLogin = stage;
        Scene scene = new Scene(new StackPane());
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stageLogin.setScene(scene);
        stageLogin.show();
    }
}