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
 * permit = false - гость
 * permit = true - зарегистрированный пользователь
 * admin = true админ
 * */

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

    public void initManager() {
        buttonReg.setOnAction((event) -> {
            try {
                showReg(buttonReg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        loginButton.setOnAction(event -> authenticated());

        this.guest.setOnAction((event) -> {
            System.out.println("гость кнопка");
            try {
                Controller.permit = false;
                showMainView(loginButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean authorize() {
        String userStr = user.getText().toLowerCase();
        boolean bool = checkLogPass(userStr, password.getText());
        if (!bool) {
            textReg.setText("Не совпадает пароль или логин");
        } else {
            Controller.permit = true;
            Controller.admin = checkAdminBool(userStr);
            System.out.println("ADMIN  " + checkAdminBool(userStr));
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