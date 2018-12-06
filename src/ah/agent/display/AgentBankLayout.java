package ah.agent.display;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AgentBankLayout  extends BorderPane {


    VBox vBox;

    Text balance = new Text();


    public AgentBankLayout(HBox options) {

        //Head
        //options
        setTop(options);

        //body
        vBox = new VBox();
        setCenter(vBox);

        HBox hBox;
        Button button;

        vBox.getChildren().add(balance);

        //Deposit
        hBox = new HBox();
        button = new Button("Deposit");
        button.setOnMouseClicked(handleDeposit());
        hBox.getChildren().add(button);
        hBox.getChildren().add(new TextField());

        vBox.getChildren().add(hBox);

        //Withdrawl
        hBox = new HBox();
        button = new Button("Withdrawl");
        button.setOnMouseClicked(handleWithdrawl());

        hBox.getChildren().add(button);
        hBox.getChildren().add(new TextField());

        vBox.getChildren().add(hBox);

    }

    private EventHandler<? super MouseEvent> handleDeposit() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("deposit");
            }
        };
    }

    private EventHandler<? super MouseEvent> handleWithdrawl() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("withdrawl");
            }
        };
    }

    public void displayBalance(double balance) {

        this.balance.setText(Double.toString(balance));
    }
}
