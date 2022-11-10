import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class RegController {
    public TextField user;
    public TextField password;
    public Text textRegError;
    public TextField name;
    public TextField surname;
    public TextField passwordCheck;
    public Button buttonReg;

    public void initManager(final LoginManager loginManager) {
        //TODO написать сюда проверку регистрации
        buttonReg.setOnAction((event) -> {
            boolean regBool = true;
            if (!name.getText().isEmpty()) {
                //положить в бд)))
            } else {
                //потрясти
                regBool = false;
            }
            if (!surname.getText().isEmpty()) {
                //положить в бд
            } else {
                //трясемся
                regBool = false;
            }
            if(!password.getText().isEmpty() && !passwordCheck.getText().isEmpty()) {
                if (password.getText().matches(passwordCheck.getText())) {
                    if (password.getText().matches("((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(\\_*).{8,})")) {
                        //бд
                    } else {
                        //пароль не по критериям
                        regBool = false;
                    }
                } else {
                    //пароли не совпадают
                    regBool = false;
                }
            } else {
                //трясемся
                regBool = false;
            }
            if(!user.getText().isEmpty()){
                //бд
            } else {
                //тряска
                regBool = false;
            }
            if(regBool){
                //заносим все в бд)))))
                //закрываем окошко идем в приложуху
            }
    });
}
}
