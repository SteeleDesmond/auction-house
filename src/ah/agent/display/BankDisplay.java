package ah.agent.display;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BankDisplay extends Stage {

    VBox vBox = new VBox();
    ObservableList<Node> list = vBox.getChildren();


    public BankDisplay() {



        setScene(new Scene(vBox, 400, 400));
    }
}
