import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;


//todo подумать как класть элементы из бд в дерево
public class Controller extends DatabaseHandler implements Initializable {
    public Label lable;
    @FXML
    private TextArea textArea;
    @FXML
    private Button okButton;
    @FXML
    private Button info;
    @FXML
    private Text text;
    @FXML
    private TreeView<String> treeView;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        text.setText("Введите слово для поиска");
        try {
            getDbConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // действие на кнопку ok

        // понять как добавить информацию из таблиц бд........
        TreeItem<String> rootItem = new TreeItem<>("Самолеты");

        // добавление картиночки
        // TreeItem<String> rootItem = new TreeItem<>("Files", new ImageView(new Image("Folder_Icon.png")));
        ArrayList<String> airplane = getName("plane");

        for (int i = 0; i < airplane.size(); i++) {
            TreeItem<String> planeTree = new TreeItem<>(airplane.get(i));
            rootItem.getChildren().add(planeTree);
            ArrayList<String> eff = getEff(i + 1);
            // второе вложение - эффективити
            for (int j = 0; j < eff.size(); j++) {
                TreeItem<String> effTree = new TreeItem<>(eff.get(j));
                planeTree.getChildren().add(effTree);
                ArrayList<String> doc = getDoc(eff.get(j));
                // третье вложение - документ
                for (int k = 0; k < doc.size(); k++) {
                    TreeItem<String> docTree = new TreeItem<>(doc.get(k));
                    effTree.getChildren().add(docTree);
                }
            }
        }

        //чтобы скрыть первоначальный элемент
        treeView.setShowRoot(false);
        this.treeView.setRoot(rootItem);
        this.drawFooter();
    }

    public void selectTreeView() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null) {
            System.out.println(item.getValue());
            //TODO переделать глупейшую проверку на цифру и сделать шо-то другое
            if (item.getValue().matches("\\d.*")) {
                openPdf("src/doc/" + item.getValue() + ".pdf");
            }
        }
    }

    // открытие файла в браузере
    private void openPdf(String path) {
        File file = new File(path);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawFooter() {
        //привязка кнопки информации к окну
        this.info.setOnAction((event) -> {
            try {
                this.infoOpen();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        });

        //подтверждение поиска слова по файлам
        this.okButton.setOnAction((event) -> {
            //если нет такого слова в книгах вывести сообщение об этом
            if (textArea.getText().length() < 50) {
                findDoc(textArea.getText());
                System.out.println("Input: " + textArea.getText());
            } else {
                text.setText("Слово должно быть покороче");
                System.out.println("больше");
            }
            // TODO если слово не найдено в файликах, то байбич
        });
    }

    //открытие окошка с информацией
    @FXML
    private void infoOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("info.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Информация");
        stage.setResizable(false);
        //TODO добавить каку-нибудь картиночку))()
        //stage.getIcons().add(new Image("file:com/company/pictures/Calendar.png"));
        stage.show();
    }
}