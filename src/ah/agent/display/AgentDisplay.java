package ah.agent.display;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class AgentDisplay {

    Stage stage;

    private BorderPane agSceneChoiceLayout;
    Scene agSceneChoiceScene;

    private AgentAHLayout agAHLayout;
    Scene agAHScene;

    private AgentBankLayout agBankLayout;
    Scene agBankScene;

    public AgentDisplay(Stage stage) {

        this.stage = stage;

//        HBox sceneChoiceHBox = sceneChoiceHBox();

        agAHLayout = new AgentAHLayout(sceneChoiceHBox());
        agAHScene = new Scene(agAHLayout, 400, 400, Color.GREY);

        agBankLayout = new AgentBankLayout(sceneChoiceHBox());
        agBankScene = new Scene(agBankLayout, 400, 400, Color.LIGHTGREEN);

        stage.setScene(agBankScene);

    }

    HBox sceneChoiceHBox(){
        HBox hBox = new HBox();

        Button button = new Button("Auctions");
        button.setOnMouseClicked(handleAHScene());
        hBox.getChildren().add(button);

        button = new Button("Bank");
        button.setOnMouseClicked(handleBankScene());
        hBox.getChildren().add(button);

        return hBox;

    }

    EventHandler<MouseEvent> handleAHScene(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(agAHScene);
            }
        };
    }

    EventHandler<MouseEvent> handleBankScene(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setScene(agBankScene);
            }
        };
    }

}
