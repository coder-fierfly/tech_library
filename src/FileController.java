import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FileController extends DatabaseHandler{
    public Button addButton;
    public ChoiceBox plainChoice;
    public ChoiceBox effChoice;
    public CheckBox planeCheckBox;
    public CheckBox effCheckBox;
    public TextArea effText;
    public TextArea planeText;
    public TextArea docText;

    //TODO сделать чтобы если выбран choicebox то тогда в него кладется инфа. Брать изначальные значения из бд.
    // Если не совпадение самолета и эффективити писать об этом
    // добавлять доки сразу с .pdf
    // доку присваивать id и связывать док с эфф по id и потом эфф с самолетом
    public void initialize() {
//        plainChoice.setOnAction((event) -> {
//            plainChoice.set
//        });
        addButton.setOnAction((event) -> {
            effText.getText();
            planeText.getText();
            docText.getText();
            System.out.println("effText.getText() " + effText.getText());
            System.out.println("planeText.getText() " + planeText.getText());
            System.out.println("docText.getText() " + docText.getText());
        });
    }
}
