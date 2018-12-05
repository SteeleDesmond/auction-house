package ah.agent;

import ah.auction.AuctionHouse;
import ah.auction.Item;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserDisplay {

    BorderPane borderPane;

    ArrayList<AuctionHouse> auctionHouseList = new ArrayList<>();

    public UserDisplay() {

        Stage stage = new Stage();
        borderPane = new BorderPane();

        //auction house select
        borderPane.setLeft(new VBox());

        stage.setScene(new Scene(borderPane, 400, 400));
        stage.show();


    }

    void addAuctionHouse(AuctionHouse auctionHouse){

        auctionHouseList.add(auctionHouse);

        Group g = new Group();

        ((VBox)borderPane.getLeft()).getChildren().add(g);

        g.getChildren().add(new Text(auctionHouse.getName()));

        Button b = new Button("visit");
        g.getChildren().add(b);

        b.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                VBox vBox = new VBox();
                borderPane.setCenter(vBox);

                for (Item item : auctionHouse.getItemList());

                HBox hBox = new HBox();
                vBox.getChildren().add(hBox);

            }
        });

    }

    void displayAuctionHouse(){

    }
}
