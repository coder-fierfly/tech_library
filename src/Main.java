import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
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
        AuthorManager loginManager = new AuthorManager(scene);
        loginManager.showLoginScreen();
        stageLogin.setScene(scene);
        stageLogin.show();
    }
}