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
        //TODO написать сюда проверку регистрации
        //TODO сделать проверку по не сорвпадению логинов, если логин совпадает попросить перепридумать
        //
        buttonReg.setOnAction((event) -> {
            System.out.println("зашли в регу");
            boolean regBool = true;
            if (name.getText().isEmpty()) {
                regBool = false;
            }
            if (surname.getText().isEmpty()) {
                //положить в бд

                regBool = false;
            }
            if(!password.getText().isEmpty() && !passwordCheck.getText().isEmpty()) {
                if (password.getText().matches(passwordCheck.getText())) {
                    if (!password.getText().matches("((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(\\_*).{8,})")) {
                        //пароль не по критериям
                        System.out.println("пароль не по критериям");
                        regBool = false;
                    }
                }
            } else {
                regBool = false;
            }
            if(login.getText().isEmpty()){
                //тряска
                regBool = false;
            }
            if(regBool){
                //заносим все в бд)))))
                //закрываем окошко идем в приложуху
                addNewUser(name.getText(), surname.getText(), login.getText(), password.getText());
                try {
                    showMainView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    });

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
}
