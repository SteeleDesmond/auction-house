package ah.agent.display;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * GUI Controller for the Agent
 */
public class DisplayController extends Application {

    // The display would be launched from the Agent class --> Moved for restructuring
    public static void main(String[] args) {

        //hello
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        UserDisplay userDisplay = new UserDisplay();


        primaryStage.setScene(new Scene(userDisplay, 400, 400));
        primaryStage.show();

    }


}
