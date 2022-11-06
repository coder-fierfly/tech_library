import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/** Controls the login screen */
public class LoginController {
    @FXML private Button guest;

    @FXML private Button buttonReg;
    @FXML private Text textReg;
    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Button loginButton;

    public void initialize() {}

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                authenticated();
            }
        });
        this.guest.setOnAction((event) -> {
            try {
                showMainView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public boolean authorize() {
        // TODO сделать проверку из бд
        Boolean bool = false;
        if("123".equals(user.getText()) && "123".equals(password.getText())){
            bool = true;
        } else {
            textReg.setText("Не совпадает пароль или логин");
        }
        return bool;
    }
    public void authenticated() {
        try {
            if(authorize()){
                showMainView();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("fx/library.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Информация");
        stage.setResizable(false);
        stage.show();
    }
}