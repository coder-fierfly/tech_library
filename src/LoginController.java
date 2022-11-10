import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/*УРОВНИ ДОСТУПА
 * 0 - гость
 * 1 - зарегистрированный пользователь
 * 2 - админ
 * */

/**
 * Controls the login screen
 */

public class LoginController {
    @FXML
    private Button guest;

    @FXML
    private Button buttonReg;
    @FXML
    private Text textReg;
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    public void initialize() {
    }

    public void initManager(final LoginManager loginManager) {

        buttonReg.setOnAction((event) -> {
            try {
                showReg();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
           // System.out.println("регистрация? напиши ее сначала");
            //TODO сделать окошка регистрации)))
        });
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                authenticated();
            }
        });

        this.guest.setOnAction((event) -> {
            System.out.println("гость кнопка");
            try {
                Controller.permit = 0;
                showMainView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean authorize() {
        // TODO сделать проверку из бд
        // здесь сделать также проверку на права, если чел админ, то открывать ему интерфейс админа,
        // если нет зареганого чела
        Boolean bool = false;
        if ("123".equals(user.getText()) && "123".equals(password.getText())) {
            bool = true;
            Controller.permit = 1;
            //permit.setPermit(12);
        } else {
            textReg.setText("Не совпадает пароль или логин");
        }
        return bool;
    }

    public void authenticated() {
        try {
            if (authorize()) {
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
        //stage.setTitle("Информация");
        stage.setResizable(false);
        stage.show();
    }

    static void showReg() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("fx/reg.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Регистрация");
        stage.setResizable(false);
        stage.show();
    }
}