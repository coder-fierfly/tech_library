import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/* TODO сделать 3 пользователей
  варики:
- как минимум можно сделать зарегистрированного пользователя и модера/админа
- сделать для зареганного пользователя свою "библиотеку"
- документы могут открывать только зарегистрированные пользователи
- модератор может добавлять доки, а админ может все
*/
//TODO сделать картинки на некоторых иконках
public class LoginDemoApplication extends Application {
    public static Stage stage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stageLogin) {
        stageLogin = stage;
        stageLogin.getIcons().add(new Image("picture/plane.png"));
        stageLogin.setTitle("ATL");
        Scene scene = new Scene(new StackPane());
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stageLogin.setScene(scene);
        stageLogin.show();
    }
}