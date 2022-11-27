import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FileController extends DatabaseHandler {
    final FileChooser fileChooser = new FileChooser();
    public File fileDoc;
    public Button addButton;
    public ChoiceBox<String> planeChoice;
    public ChoiceBox<String> effChoice;
    public TextArea effText;
    public TextArea planeText;
    public TextArea docText;
    public Text warningText;
    public TextArea textText;
    public Button addDoc;
    public Text success;
    public static String effStr;

    public void initialize() {
        try {
            getDbConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        drawFooter();
    }

    public void drawFooter() {
        addButton.setDisable(true);
        ArrayList<String> planeArr = getName("plane");
        ObservableList<String> observableListPlane = FXCollections.observableArrayList();
        observableListPlane.add("");
        observableListPlane.addAll(planeArr);
        planeChoice.getItems().addAll(observableListPlane);

        ArrayList<String> effArr = getName("effectivity");
        ObservableList<String> observableListEff = FXCollections.observableArrayList();
        observableListEff.add("");
        observableListEff.addAll(effArr);
        effChoice.getItems().addAll(observableListEff);

        addButton.setOnAction((event) -> {
            //обработка пустых полей
            if ((planeChoice.getValue() == null && planeText.getText().isEmpty())) {
                Shake shakePlane = new Shake(planeText);
                shakePlane.playAnim();
                Shake shPlaneChoice = new Shake(planeChoice);
                shPlaneChoice.playAnim();
            } else if (effChoice.getValue() == null && effText.getText().isEmpty()) {
                Shake shakeEff = new Shake(effText);
                shakeEff.playAnim();
                Shake shEffChoice = new Shake(effChoice);
                shEffChoice.playAnim();
            } else if (docText.getText().isEmpty() || fileDoc == null) {
                Shake shakeDoc = new Shake(docText);
                shakeDoc.playAnim();
            } else if (textText.getText().isEmpty()) {
                Shake shakeText = new Shake(textText);
                shakeText.playAnim();
                //обработка верных ситуаций
            } else {
                warningText.setText("");
                String planeStr;
                String docStr;
                int planeId;
                int effId;
                int docId;
                effText.getText();
                planeText.getText();
                docText.getText();
                String planeBuff = planeText.getText();
                // если самолет введен в текстовое поле
                if (!planeBuff.isEmpty()) {
                    if (planeArr.contains(planeBuff)) {
                        planeStr = planeBuff;
                    } else {
                        planeStr = planeBuff;
                        addNew(planeStr, "plane");
                        planeArr.add(planeStr);
                        ObservableList<String> observableListPlane1 = FXCollections.observableArrayList();
                        observableListPlane1.add(planeStr);
                        planeChoice.getItems().addAll(observableListPlane1);
                    }
                    // если самолет выбран
                } else {
                    planeStr = planeChoice.getValue();
                }
                planeId = getId(planeStr, "plane");

                // если eff введен в текстовое поле
                if (!effText.getText().isEmpty()) {
                    String effBuff = effText.getText();
                    if (effArr.contains(effBuff)) {
                        effStr = effBuff;
                        effId = getId(effStr, "effectivity");
                    } else {
                        effStr = effBuff;
                        addNew(effStr, "effectivity");
                        effId = getId(effStr, "effectivity");
                        bundlePlaneEff(planeId, effId);
                        effArr.add(effStr);
                        ObservableList<String> observableListEff1 = FXCollections.observableArrayList();
                        observableListEff1.add(effStr);
                        effChoice.getItems().addAll(observableListEff1);
                    }
                    // если eff выбран
                } else {
                    effStr = effChoice.getValue();
                    effId = getId(effStr, "effectivity");
                }
                docStr = docText.getText();
                docId = addNewGetId(docStr, "doc");
                bundleEffDoc(effId, docId);
                bundleTextOnDoc(docId, textText.getText());

                success.setText("Успешно");
                hide(success);

                File rootFile = new File("src/Configs.java");
                String slash = "\\";
                String path = rootFile.getAbsolutePath().replace("Configs.java", "").replace(slash, "/");
                File theDir = new File(path + "doc/" + effStr + "/");
                if (!theDir.exists()) {
                    theDir.mkdirs();
                }
                File fff = new File(path + "doc/" + effStr + "/" + fileDoc.getName());
                try {
                    Files.copy(Paths.get(fileDoc.getPath()), Paths.get(fff.getPath()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            docText.setText("");
            textText.setText("");
            addButton.setDisable(true);
        });
        addDoc.setOnAction(
                e -> {
                    fileDoc = fileChooser.showOpenDialog(Main.stage);
                    if (fileDoc != null && fileDoc.getName().matches(".+\\.pdf")) {
                        docText.setText(fileDoc.getName());
                        getTextPdf(fileDoc.getPath());
                        addButton.setDisable(false);
                    } else {
                        warningText.setText("Файл должен иметь разрешение .pdf");
                        hide(warningText);
                    }
                });
    }

    public void getTextPdf(String path) {
        PDFParser parser;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;

        String parsedText;
        try {
            parser = new PDFParser(new RandomAccessBufferedFileInputStream(path));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            textText.setText(parsedText);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void hide(Text text) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                text.setText("");
            }
        }, 2 * 1000);
    }
}
