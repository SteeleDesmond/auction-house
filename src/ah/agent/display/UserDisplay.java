package ah.agent.display;

import ah.bank.Bank;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserDisplay extends BorderPane {

    VBox center;
    VBox leftPane;
    VBox rightPane;

    UserDisplay(){

        center = new VBox();
        setCenter(center);

        leftPane = leftPane();
        setLeft(leftPane);

        rightPane = new VBox();
        setRight(rightPane);
    }

    void agentInfo(){

    }

    VBox leftPane(){

        VBox vBox = new VBox();

        Button startBank = new Button("Start Bank");
        startBank.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Start Bank");
                //start the bank

                BankDisplay bankDisplay = new BankDisplay();

                center.getChildren().add(makeBankGroup());
            }

            private Group makeBankGroup(){
                Group g = new Group();
                Rectangle r = new Rectangle(30, 30);
                r.setFill(Color.LIGHTBLUE);
                r.setStroke(Color.BLUE);
                g.getChildren().add(r);
                g.getChildren().add(new Text("Bank"));
                return g;
            }

        });
        vBox.getChildren().add(startBank);

        Button startAH = new Button("Start AuctionHouse");
        startAH.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                System.out.println("start Auction House");
                //window to enter name of auction house
                //starts backround auction house

                center.getChildren().add(makeAHGroup());
            }

            private Group makeAHGroup() {
                Group g = new Group();
                Rectangle r = new Rectangle(30, 30);
                r.setFill(Color.LIGHTGREEN);
                r.setStroke(Color.GREEN);
                g.getChildren().add(r);
                g.getChildren().add(new Text("AH"));
                return g;
            }

        });
        vBox.getChildren().add(startAH);

        Button startAgent = new Button("Start Agent");
        startAgent.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                System.out.println("start agent");
                //make new window with list of auction houses
                //when an auction house is chosen the window will change to a

                rightPane.getChildren().add(makeAgentGroup());
            }
            private Group makeAgentGroup(){
                Group g = new Group();
                Rectangle r = new Rectangle(30, 30);
                r.setFill(Color.RED);
                r.setStroke(Color.DARKRED);
                g.getChildren().add(r);
                g.getChildren().add(new Text("Agent"));
                return g;
            }
        });
        vBox.getChildren().add(startAgent);

        return vBox;

    }

    Stage makeWindow(Parent content){

        Stage window = new Stage();
        Scene scene = new Scene(content, 400, 400);
        window.setScene(scene);
        return window;
    }

}
