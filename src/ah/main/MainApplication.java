package ah.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public static void main(String[] args) {

        //hello
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();

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
