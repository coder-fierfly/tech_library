import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
// класс для тряски пустых элементов
public class Shake {
    private final TranslateTransition translate;
    public Shake (Node node) {
        translate = new TranslateTransition(Duration.millis(70), node);
        translate.setFromX(-10f);
        translate.setByX(10f);
        translate.setCycleCount(3);
        translate.setAutoReverse(true);
    }
    public void playAnim() {
        translate.playFromStart();
    }
}