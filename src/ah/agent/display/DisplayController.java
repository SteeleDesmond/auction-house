package ah.agent.display;

import javafx.application.Application;
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

    /**
     * GUI Controller
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //GUI layout

        AgentDisplay agentDisplay = new AgentDisplay(primaryStage);

        primaryStage.show();

    }


}
