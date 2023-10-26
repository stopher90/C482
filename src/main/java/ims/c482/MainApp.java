package ims.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**This class creates the Inventory Management System application
 * FUTURE ENHANCEMENT: New feature that would enhance the application for better inventory manage
 * will be to take the parts that are removed from a product that is to be deleted and add it back to the inventory
 * list so the parts are not lost or unaccounted for in inventory. */

public class MainApp extends Application {

    /** This Method sets the primary stage for the app.
     * Launches the Main Screen FXML. */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 500);
        stage.setTitle("Inventory Management System C482");
        stage.setScene(scene);
        stage.show();
    }
/**This is the main method of the program. This is what is called first when the program is run.
 * RUNTIME ERROR: location at Modify Product class above the searchfield method.
 * Javadoc's location: C482 -> Javadoc */
    public static void main(String[] args) {
        launch();
    }
}