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
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Controller extends DatabaseHandler implements Initializable {
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

        // todo либо строить с дока и выше выше, либо все построить а когда дойду до дока отображать
        // только нужное, например проверка на то есть ли children

        //чтобы скрыть первоначальный элемент
        treeView.setShowRoot(false);
        this.treeView.setRoot(rootItem);
        this.drawFooter();
    }

    public void selectTreeView() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null) {
            //TODO переделать проверку на .pdf в конце
            if (item.getValue().matches("\\d.*")) {
                openPdf("src/doc/" + item.getValue() + ".pdf");
                treeView.getSelectionModel().clearSelection();
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

        // действие на кнопку ok
        //подтверждение поиска слова по файлам
        this.okButton.setOnAction((event) -> {
            if (textArea.getText().length() < 50) {
                // убираю лишние пробелы, пробелы между словами заменяю на &
                String srt = textArea.getText().trim().replaceAll("[ ]{1,}", " & ");
                ArrayList<String> doc = findDocByText(srt);
                if (srt.isEmpty()) {
                    text.setText("Вы ничего не ввели");
                } else if (doc.isEmpty()) {
                    text.setText("По данному запросу ничего не нашлось");
                } else {
                    //TODO сделать сюда вывод нужных файлов
                    text.setText("Выполняю запрос...");
                    fileOutput(doc);
                    text.setText("Запрос выполнен");
                }
            } else {
                text.setText("Слово должно быть покороче");
            }
        });
    }

    private void fileOutput(ArrayList<String> doc) {
        TreeItem<String> rootItem = new TreeItem<>("Самолеты");
        // беру все самолеты из таблицы самолетов
        ArrayList<String> airplane = getName("plane");

        TreeItem<String> planeTree;
        TreeItem<String> effTree;
        TreeItem<String> docTree = null;
        for (int i = 0; i < airplane.size(); i++) {
            // самолет берем по id
            planeTree = new TreeItem<>(airplane.get(i));
            ArrayList<String> eff = getEff(i + 1);
            // второе вложение - эффективити
            for (int j = 0; j < eff.size(); j++) {
                // беру эффективити по id вывожу имя эффективити
                effTree = new TreeItem<>(eff.get(j));
                // третье вложение - документ
                for (int k = 0; k < doc.size(); k++) {
                    if (eff.get(j).matches(getEff(doc.get(k)))) {
                        docTree = new TreeItem<>(doc.get(k));
                    }
                    if (k == (doc.size() - 1) && docTree != null) {
                        effTree.getChildren().add(docTree);
                        if (effTree != null) {
                            planeTree.getChildren().add(effTree);
                        }
                    }
                }

            }
            if (docTree != null) {
                rootItem.getChildren().add(planeTree);
            }
        }

        //чтобы скрыть первоначальный элемент
        //treeView.setShowRoot(false);
        this.treeView.setRoot(rootItem);
        this.drawFooter();
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