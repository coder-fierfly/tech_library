import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/*УРОВНИ ДОСТУПА
 * permit = false - гость
 * permit = true - зарегистрированный пользователь
 * admin = true админ
 * */

public class AuthorController extends DatabaseHandler {
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
            Shake shakeUser = new Shake(user);
            shakeUser.playAnim();
            Shake shakePass = new Shake(password);
            shakePass.playAnim();
            textReg.setText("Не совпадает пароль или логин");
        } else {
            Controller.permit = true;
            Controller.admin = checkAdminBool(userStr);
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
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("picture/plane.png"));
        stage.setScene(scene);
        stage.setTitle("ATL");
        stage.setResizable(false);
        stage.show();
    }

    private void showReg(Button buttonReg) throws IOException {
        buttonReg.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("fx/reg.fxml"));
        Parent root = loader.load();
        Stage stageReg = new Stage();
        Scene scene = new Scene(root);
        stageReg.getIcons().add(new Image("picture/plane.png"));
        stageReg.setScene(scene);
        stageReg.setTitle("Регистрация");
        stageReg.setResizable(false);
        stageReg.show();
    }
}