import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//TODO сделать админу возможность добавления документов

public class Controller extends DatabaseHandler implements Initializable {
    public Text sorry;
    public Button authorization;
    public Button addNewDoc;
    @FXML
    private Button reset;
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
    public static boolean permit;

    public static boolean admin;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        text.setText("Введите слово для поиска");

        if (!admin) {
            authorization.setVisible(false);
        } else {
            authorization.setText("Зарегистрировать");
        }
        try {
            getDbConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        downloadTree();
        this.drawFooter();
    }


    public void selectTreeView() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null && item.getValue().matches(".+\\.pdf")) {
            System.out.println("admin " + admin);
            if (permit || admin) {
                openPdf("src/doc/" + item.getValue());
                treeView.getSelectionModel().clearSelection();
            } else {
                sorry.setText("Чтобы открыть документ вам нужно зарегистрироваться");
                authorization.setVisible(true);
            }
        }
    }

    private void downloadTree() {
        TreeItem<String> rootItem = new TreeItem<>("Самолеты");
        // добавление картиночки
        // TreeItem<String> rootItem = new TreeItem<>("Files", new ImageView(new Image("Folder_Icon.png")));
        ArrayList<String> airplane = getNamePlane();

        for (int i = 0; i < airplane.size(); i++) {
            TreeItem<String> planeTree = new TreeItem<>(airplane.get(i));
            rootItem.getChildren().add(planeTree);
            ArrayList<String> eff = getEffNameByPlaneId(i + 1);
            // второе вложение - эффективити
            for (int j = 0; j < eff.size(); j++) {
                TreeItem<String> effTree = new TreeItem<>(eff.get(j));
                planeTree.getChildren().add(effTree);
                ArrayList<String> doc = getDocByEffName(eff.get(j));
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
        this.authorization.setOnAction((event) -> {
            try {
                showReg(authorization);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.reset.setOnAction((event) -> {
            textArea.setText("");
            text.setText("Выполняю...");
            downloadTree();
            text.setText("Введите слово для поиска");
        });

        // действие на кнопку ok
        //подтверждение поиска слова по файлам
        this.okButton.setOnAction((event) -> {
            if (textArea.getText().length() < 50) {
                // Убираю лишние пробелы до и после запроса. Пробелы между словами, заменяю на &
                String srt = textArea.getText().trim().replaceAll(" +", " & ");
                Map<Integer, String> doc = findDocByText(srt);

                if (srt.isEmpty()) {
                    text.setText("Вы ничего не ввели");
                } else if (doc.isEmpty()) {
                    text.setText("По данному запросу ничего не нашлось");
                } else {
                    text.setText("Выполняю запрос...");
                    fileOutput(doc);
                    text.setText("Запрос выполнен");
                }
            } else {
                text.setText("Запрос слишком длинный");
            }
        });
        this.addNewDoc.setOnAction((event) -> {
            try {
                showAdder(addNewDoc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // поиск нужных файлов по совпадению данных с бд
    private void fileOutput(Map<Integer, String> docMap) {
        TreeItem<String> planeTree;
        TreeItem<String> effTree;
        TreeItem<String> docTree;

        Iterator<Map.Entry<Integer, String>> itr = docMap.entrySet().iterator();
        Map<Integer, String> effMap = new HashMap<>();
        Map<Integer, String> planeMap = new HashMap<>();
        while (itr.hasNext()) {
            Map.Entry<Integer, String> entry = itr.next();
//            docTree = new TreeItem<>(entry.getValue());
            // без повторений добавляются имена эфф
            String str = getEffNameIdByDocId(entry.getKey()).str;
            int id = getEffNameIdByDocId(entry.getKey()).num;
            effMap.put(id, str);
        }
        Iterator<Map.Entry<Integer, String>> it = effMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            planeMap.put(getPlaneByEff(entry.getKey()).num, getPlaneByEff(entry.getKey()).str);
        }

        TreeItem<String> rootItem = new TreeItem<>("Самолеты");

        Iterator<Map.Entry<Integer, String>> planeIt = planeMap.entrySet().iterator();
        while (planeIt.hasNext()) {
            Map.Entry<Integer, String> planeEntry = planeIt.next();
            planeTree = new TreeItem<>(planeEntry.getValue());
            rootItem.getChildren().add(planeTree);
            Iterator<Map.Entry<Integer, String>> effIt = effMap.entrySet().iterator();

            while (effIt.hasNext()) {
                Map.Entry<Integer, String> effEnt = effIt.next();
                ArrayList<Integer> effArray = getEffIdByPlaneId(planeEntry.getKey());
                for (int i = 0; i < effArray.size(); i++) {
                    if (effArray.get(i).equals(effEnt.getKey())) {
                        effTree = new TreeItem<>(effEnt.getValue());
                        planeTree.getChildren().add(effTree);

                        Iterator<Map.Entry<Integer, String>> docIt = docMap.entrySet().iterator();

                        while (docIt.hasNext()) {
                            Map.Entry<Integer, String> docEnt = docIt.next();
                            ArrayList<Integer> docArray = getDocIdByEffId(effEnt.getKey());
                            for (int j = 0; j < docArray.size(); j++) {
                                if (docArray.get(j).equals(docEnt.getKey())) {
                                    docTree = new TreeItem<>(docEnt.getValue());
                                    effTree.getChildren().add(docTree);
                                }
                            }
                        }
                    }
                }
                //чтобы скрыть первоначальный элемент
                treeView.setShowRoot(false);
                this.treeView.setRoot(rootItem);
            }
        }
    }

    //открытие окошка с информацией
    @FXML
    private void infoOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("fx/info.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Информация");
        stage.setResizable(false);
        stage.getIcons().add(new Image("picture/plane.png"));
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

    private void showAdder(Button addNewDoc) throws IOException {
        addNewDoc.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("fx/file.fxml"));
        Parent root = loader.load();
        Stage stageReg = new Stage();
        stageReg.setScene(new Scene(root));
        stageReg.setResizable(false);
        stageReg.show();
    }
}
