import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable{

    @FXML
    private TreeView<String> treeView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        TreeItem<String> rootItem = new TreeItem<>("Files");
        //TreeItem<String> rootItem = new TreeItem<>("Files", new ImageView(new Image("Folder_Icon.png")));

        TreeItem<String> branchItem1 = new TreeItem<>("Pictures");
        TreeItem<String> branchItem2 = new TreeItem<>("Videos");
        TreeItem<String> branchItem3 = new TreeItem<>("Music");
        TreeItem<String> branchItem4 = new TreeItem<>("Music");

        TreeItem<String> branchItem5 = new TreeItem<>("Music");


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
        TreeItem<String> leafItem11 = new TreeItem<>("music523");
        TreeItem<String> leafItem12 = new TreeItem<>("music62");

        branchItem1.getChildren().addAll(leafItem1, leafItem2);
        branchItem2.getChildren().addAll(leafItem3, leafItem4);
        branchItem3.getChildren().addAll(leafItem5, leafItem6, leafItem7, leafItem8, leafItem9,
                leafItem10, leafItem11, leafItem12);

        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3);

        //treeView.setShowRoot(false);
        treeView.setRoot(rootItem);
    }

    public void selectItem() {

        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        //TODO написать сюда вызов файла
        if(item != null) {
            System.out.println(item.getValue());
        }
    }
}