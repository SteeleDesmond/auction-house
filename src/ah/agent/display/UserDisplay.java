package ah.agent.display;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserDisplay extends BorderPane {

    HBox center;

    UserDisplay(){

        center = new HBox();
        setCenter(center);




        VBox leftPane = leftPane();
        setLeft(leftPane);

    }

    VBox leftPane(){

        VBox vBox = new VBox();

        Button startBank = new Button("Start Bank");
        startBank.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Start Bank");
                //start the bank

                center.getChildren().add(makeBankGroup());
            }

            Group makeBankGroup(){

                Group g = new Group();
                g.getChildren().add(new Rectangle(30, 30 ));
                g.getChildren().add(new Text("Bank"));
                g.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("display bank window");
                    }
                });

                return g;
            }
        });
        vBox.getChildren().add(startBank);

        Button startAH = new Button("Start AuctionHouse");
        startBank.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                System.out.println("start Auction House");
                //window to enter name of auction house
                //starts backround auction house

                center.getChildren().add(makeAHGroup());
            }

            private Group makeAHGroup() {

                Group g = new Group();
                g.getChildren().add(new Rectangle(30, 30 ));
                g.getChildren().add(new Text("AH"));
                g.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("display AH window");
                    }


                });

                return g;
            }
        });
        vBox.getChildren().add(startAH);

        Button startAgent = new Button("Start Agent");
        startBank.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                System.out.println("start agent");
                //make new window with list of auction houses
                //when an auction house is chosen the window will change to a
            }
        });
        vBox.getChildren().add(startAgent);

        return vBox;

    }

}
