package ah.agent.display;

import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BankDisplay {

    public BankDisplay() {

        Stage stage = new Stage();
        HBox hBox = new HBox();

        TableView<String> tableView = new TableView<>();

        tableView.getItems().add("Account: jhsdkjhcbg, bal: $100,000");

        stage.setScene( new Scene(hBox, 400, 400));
    }
}
