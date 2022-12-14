import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    public CheckBox adminCheck;
    public Text admText;

    public void initialize() {
        if (!Controller.admin) {
            adminCheck.setVisible(false);
            admText.setVisible(false);
        }
        try {
            getDbConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        buttonReg.setOnAction((event) -> {
            boolean adminBool = adminCheck.isSelected();
            boolean regBool = true;
            if (name.getText().isEmpty()) {
                regBool = false;
                playShake(name);
            }
            if (surname.getText().isEmpty()) {
                regBool = false;
                playShake(surname);
            }
            if (!password.getText().isEmpty() && !passwordCheck.getText().isEmpty()) {
                if (password.getText().equals(passwordCheck.getText())) {
                    if (!password.getText().matches("((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,})")) {
                        //пароль не по критериям
                        regBool = false;
                        textRegError.setText("Пароль должен содержать 8 символов из которых, хотя бы одна прописная " +
                                "и одна строчная буквы латинского алфавита, а также цифра.");
                    }
                } else {
                    textRegError.setText("Пароли не совпадают.");
                    playShake(passwordCheck);
                    regBool = false;
                }
            } else {
                playShake(password);
                regBool = false;
            }
            if (login.getText().isEmpty()) {
                playShake(login);
                regBool = false;
            } else if (checkLogin(login.getText().toLowerCase())) {
                textRegError.setText("Такой логин уже существует");
                regBool = false;
            }

            if (regBool) {
                //заносим все в бд)))))
                //закрываем окошко идем в основное окно
                addNewUser(name.getText(), surname.getText(), login.getText().toLowerCase(), password.getText(), adminBool);
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
        new Main().start(Main.stage);
    }

    static void playShake(TextField tf) {
        Shake shake = new Shake(tf);
        shake.playAnim();
    }
}
