import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/*УРОВНИ ДОСТУПА
 * 0 - гость
 * 1 - зарегистрированный пользователь
 * 2 - админ
 * */

/**
 * Controls the login screen
 */

public class LoginController extends DatabaseHandler {
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
    static Stage stage = new Stage();
    public void initialize() {
        try {
            getDbConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initManager(final LoginManager loginManager) {
        buttonReg.setOnAction((event) -> {
            try {
                showReg(buttonReg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
                Controller.permit = Boolean.parseBoolean(null);
                showMainView(loginButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean authorize() {
        String userStr = user.getText();
        Boolean bool = checkLogPass(userStr, password.getText());
        if (!bool) {
            textReg.setText("Не совпадает пароль или логин");
        } else {
            Controller.permit = checkAdminBool(userStr);
        }
        return bool;
    }

    public void authenticated() {
        try {
            if (authorize()) {
                showMainView(loginButton);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMainView(Button loginButton) throws IOException {
        loginButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("fx/library.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        //stage.setTitle("Информация");
        stage.setResizable(false);
        stage.show();
    }

    private void showReg(Button buttonReg) throws IOException {
        buttonReg.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("fx/reg.fxml"));
        Parent root = loader.load();
        Stage stageReg = new Stage();
        stageReg.setScene(new Scene(root));
        stageReg.setTitle("Регистрация");
        stageReg.setResizable(false);
        stageReg.show();
    }
}