import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

public class Controller extends DatabaseHandler implements Initializable{
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
        try {
            getDbConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //действие на кнопку ok
        text.setText("Введите слово для поиска");

        //понять как добавить информацию из таблиц
        TreeItem<String> rootItem = new TreeItem<>("Files");
        //  TreeItem<String> rootItem = new TreeItem<>("Files", new ImageView(new Image("Folder_Icon.png")));

        //второе вложение
        TreeItem<String> branchItem1 = new TreeItem<>("Pictures");
        TreeItem<String> branchItem2 = new TreeItem<>("Videos");
        TreeItem<String> branchItem3 = new TreeItem<>("Music");

        //третье вложение
        TreeItem<String> leafItem1 = new TreeItem<>("picture1");
        TreeItem<String> leafItem2 = new TreeItem<>("picture2");
        TreeItem<String> leafItem3 = new TreeItem<>("video1");
        TreeItem<String> leafItem4 = new TreeItem<>("video2");
        TreeItem<String> leafItem5 = new TreeItem<>("music1");
        TreeItem<String> leafItem6 = new TreeItem<>("music2");
        TreeItem<String> leafItem7 = new TreeItem<>("music3");
        TreeItem<String> leafItem8 = new TreeItem<>("music4");
        TreeItem<String> leafItem9 = new TreeItem<>("music5");
        TreeItem<String> leafItem10 = new TreeItem<>("music6");
        TreeItem<String> leafItem11 = new TreeItem<>("music7");
        TreeItem<String> leafItem12 = new TreeItem<>("music8");

        //описание что к чему относиться, что во что вкладывать
        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3);

        branchItem1.getChildren().addAll(leafItem1, leafItem2);
        branchItem2.getChildren().addAll(leafItem3, leafItem4);
        branchItem3.getChildren().addAll(leafItem5, leafItem6, leafItem7, leafItem8, leafItem9,
                leafItem10, leafItem11, leafItem12);

        //treeView.setShowRoot(false);

        treeView.setRoot(rootItem);
        this.drawFooter();
    }

    public void selectTreeView() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        //TODO написать сюда вызов файла
        if (item != null) {
            System.out.println(item.getValue());
        }
    }

    private void drawFooter() {
        this.info.setOnAction((event) -> {
            try {
                this.infoOpen();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        });
        this.okButton.setOnAction((event) -> {
            //если нет такого слова в книгах вывести сообщение об этом
            if (textArea.getText().length() < 50) {
                System.out.println("Input: " + textArea.getText());
            } else {
                text.setText("Слово должно быть покороче");
                System.out.println("больше");
            }
            // TODO если слово не найдено в файликах, то байбич
        });
    }

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