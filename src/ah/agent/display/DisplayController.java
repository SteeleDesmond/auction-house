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
        HBox pane = new HBox();

        try {

        }catch (Exception e) {
            e.printStackTrace();
        }

        //agent button
        Button runAgent = new Button("Run Agent");
        runAgent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("running agent");
            }
        });
        pane.getChildren().add(runAgent);

        //run Bank
        Button runBank = new Button("Run Bank");
        runBank.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("running bank");
            }
        });
        pane.getChildren().add(runBank);

        //run Auction house
        Button runAuctionHouse = new Button("Run Auction House");
        runAuctionHouse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("running auction House");
            }
        });
        pane.getChildren().add(runAuctionHouse);

        primaryStage.setScene(new Scene(pane, 400, 400));
        primaryStage.show();


    }
}
