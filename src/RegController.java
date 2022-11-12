import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegController extends DatabaseHandler {
    public TextField login;
    public TextField password;
    public Text textRegError;
    public TextField name;
    public TextField surname;
    public TextField passwordCheck;
    public Button buttonReg;

    public void initialize() {
        try {
            getDbConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        buttonReg.setOnAction((event) -> {
            boolean regBool = true;
            if (name.getText().isEmpty()) {
                regBool = false;
                //портясти
            }
            if (surname.getText().isEmpty()) {
                regBool = false;
                //портясти
            }
            if (!password.getText().isEmpty() && !passwordCheck.getText().isEmpty()) {
                if (password.getText().equals(passwordCheck.getText())) {
                    if (!password.getText().matches("((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(\\_*).{8,})")) {
                        //пароль не по критериям
                        regBool = false;
                        textRegError.setText("Пароль должен содержать хотя бы одну прописную " +
                                "и одну строчную букву латинского алфавита, а также цифру.");
                    }
                } else {
                    textRegError.setText("Пароли не совпадают.");
                    regBool = false;
                }
            } else {
                // потрясти
                regBool = false;
            }
            if (login.getText().isEmpty()) {
                //тряска
                regBool = false;
            } else if (checkLogin(login.getText())) {
                textRegError.setText("Такой логин уже существет");
                regBool = false;
            }

            if (regBool) {
                //заносим все в бд)))))
                //закрываем окошко идем в приложуху
                addNewUser(name.getText(), surname.getText(), login.getText(), password.getText());
                System.out.println("добавлен новый юзер");
                try {
                    showAuthorization(buttonReg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    static void showAuthorization(Button buttonReg) throws IOException {
        buttonReg.getScene().getWindow().hide();
        new LoginDemoApplication().start(LoginDemoApplication.stage);
    }
}
