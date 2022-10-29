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

        System.out.println("111111111");
        // добавление картиночки
        // TreeItem<String> rootItem = new TreeItem<>("Files", new ImageView(new Image("Folder_Icon.png")));
        ArrayList<String> airplane = getName("plane");
        ArrayList<String> eff = getName("effectivity");
        System.out.println("!!!!!!!!!!");
        // второе вложение

        for(int i = 0; i < airplane.size(); i++){
            TreeItem<String> planeTree = new TreeItem<>(airplane.get(i));
            rootItem.getChildren().add(planeTree);
            // TODO сделать привязку к самому самолету
            // сделать в getName еще добавук в виде самолета)))))))

            //сделать чтобы
            for(int j = 0; j < eff.size(); j++) {
                TreeItem<String> effTree = new TreeItem<>(eff.get(j));
                planeTree.getChildren().add(effTree);
            }
            //TODO
            //как-то написать сюды добавление следующих инструментов
        }
//        TreeItem<String> branchItem1 = new TreeItem<>(airplane.get(0));
//        TreeItem<String> branchItem2 = new TreeItem<>(airplane.get(1));


        //третье вложение
        TreeItem<String> leafItem1 = new TreeItem<>("эффективити 1(777)");
        TreeItem<String> leafItem2 = new TreeItem<>("эффективити 2(777)");
        TreeItem<String> leafItem3 = new TreeItem<>("эффективити 1(737)");
        TreeItem<String> leafItem4 = new TreeItem<>("эффективити 2(737)");

        File file = new File("C:\\Users\\lena\\Desktop\\1.pdf");
        TreeItem<String> leafItem5 = new TreeItem<>("1");
        TreeItem<String> leafItem6 = new TreeItem<>("2");
        TreeItem<String> leafItem7 = new TreeItem<>("типо файл 3");
        TreeItem<String> leafItem8 = new TreeItem<>("типо файл 4");
        TreeItem<String> leafItem9 = new TreeItem<>("типо файл 5");
        TreeItem<String> leafItem10 = new TreeItem<>("типо файл 6");
        TreeItem<String> leafItem11 = new TreeItem<>("типо файл 7");
        TreeItem<String> leafItem12 = new TreeItem<>("типо файл 8");

        //описание что к чему относиться, что во что вкладывать
        //toDO
//        rootItem.getChildren().addAll(branchItem1, branchItem2);
//
//
    //    branchItem1.getChildren().addAll(leafItem1, leafItem2);
//        branchItem2.getChildren().addAll(leafItem3, leafItem4);

       // leafItem1.getChildren().addAll(leafItem5, leafItem6);
//        leafItem2.getChildren().addAll(leafItem8, leafItem9);
//        leafItem3.getChildren().addAll(leafItem10, leafItem11);
//        leafItem4.getChildren().addAll(leafItem12);

//        TreeView<String> treeView = new TreeView<String>(rootItem);

        // получаем модель выбора
//        SelectionModel<TreeItem<String>> selectionModel = treeView.getSelectionModel();
//        System.out.println(selectionModel.getSelectedItem());
        //treeView.setShowRoot(false);

//        stage.setScene(scene);
//        stage.setTitle("TreeView in JavaFX");
//        stage.show();


        //чтобы скрыть первоначальный элемент
        treeView.setShowRoot(false);
        this.treeView.setRoot(rootItem);
        this.drawFooter();
      /*  selectionModel.selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){
            public void changed(ObservableValue<? extends TreeItem<String>> changed,
                                TreeItem<String> oldValue, TreeItem<String> newValue){
                lable.setText("Selected: " + newValue.getValue());
            }
        });*/
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