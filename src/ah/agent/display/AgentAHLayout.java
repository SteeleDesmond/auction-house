package ah.agent.display;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AgentAHLayout extends BorderPane {

    VBox houseChoiceVBox;

    public AgentAHLayout(HBox options) {

        //Head
        //options
        setTop(options);

        //Body : Left
        //AH items
        houseChoiceVBox = houseChoiceVBox();
        setLeft(houseChoiceVBox);

    }

    VBox houseChoiceVBox(){
        VBox vBox = new VBox();

        vBox.getChildren().add(new Text("Houses:"));

        return vBox;
    }
}
