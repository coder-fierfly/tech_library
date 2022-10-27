import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;


// в бд должно быть разграничение по типам самолетов, по их выпуску, по словам из глав

public class PDFViewTest extends Application {

    public PDFViewTest() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        final PDDocument document;
//        try {
//            document = PDDocument.load(new File("C:\\Users\\lena\\Desktop\\ref.pdf"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        document.getClass();
//        if( document.isEncrypted() )
//        {
//            try
//            {
//               // document.decrypt( "" );
//            }
//            catch( InvalidPasswordException e )
//            {
//                System.err.println( "Error: Document is encrypted with a password." );
//                System.exit( 1 );
//            }
//        }

        Parent root = (Parent) FXMLLoader.load((URL) Objects.requireNonNull(this.getClass().getResource("help.fxml")));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("PDF simple viewer by PDFBox");
        primaryStage.show();


//
//        PDFTextStripperByArea stripper = null;
//        try {
//            stripper = new PDFTextStripperByArea();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        stripper.setSortByPosition(true);
//        PDFTextStripper strippe = null;
//        try {
//            strippe = new PDFTextStripper();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        String st = null;
//        try {
//            st = strippe.getText(document);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(st);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//
//import java.awt.Desktop;
//import java.awt.Toolkit;
//import java.io.File;
//import java.io.IOException;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.HBox;
//import javafx.stage.FileChooser;
//import javafx.stage.FileChooser.ExtensionFilter;
//import javafx.stage.Stage;
//
//public class PDFViewTest extends Application {
//
//    @Override
//    public void start (final Stage primaryStage)  {
//        final HBox root = new HBox(5);
//        root.setPadding(new Insets(5));
//        final Button button = new Button("Browse");
//        final FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File(System
//                .getProperty("user.home")));
//        fileChooser.getExtensionFilters().add(
//                new ExtensionFilter("PDF Files", "*.pdf", "*.PDF"));
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event)  {
//                final File file = fileChooser.showOpenDialog(primaryStage);
//                if (file != null) { new Thread(new Runnable() {
//                    @Override
//                    public void run () {
//                        try {
//                            Desktop.getDesktop().open(file);
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//                }
//            }
//        });
//
//        root.getChildren().add(button);
//        primaryStage.setScene(new Scene(root, 150, 75));
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) throws Exception  {
//        System.setProperty("javafx.macosx.embedded", "true");
//        Toolkit.getDefaultToolkit();
//        launch(args);
//    }
//}
