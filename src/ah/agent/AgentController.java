package ah.agent;

import ah.agent.display.AgentDisplay;
import ah.shared.AuctionHouseProxy;
import ah.shared.BankProxy;
import ah.shared.CommunicationService;
import javafx.stage.Stage;

import java.util.Scanner;

public class AgentController implements Runnable {

    AgentDisplay agentDisplay;

    private ActiveBids activeBids;
    private UserCommands userCommands;
    private UserAccount userAccount;
    private CommunicationService connector = new CommunicationService();
    private AuctionHouseProxy auctionHouse;

    private BankProxy bank;
    private Scanner commandLine = new Scanner(System.in);
    private boolean running = true;
    private String hostName;
    private int portNumber;

    public AgentController(BankProxy bank) {
        this.bank = bank;

//        Stage stage = new Stage();
//        agentDisplay = new AgentDisplay(stage);
//
//        stage.show();

    }

    @Override
    public void run() {
        while(running) {
            printCommands();
            switch(commandLine.nextLine()) {
                case ("BankMsg"): {
                    System.out.println("Type a message to send to the bank");
                    bank.sendMsg(commandLine.nextLine());
                    break;
                }
                case("a"): {
                    getAuctionHouses();
                    break;
                }
                case("b"): {
                    bank.checkBalance();
                    break;
                }
                case("c"): {
                    System.out.println("Please give the host name of the auction house:");
                    hostName = commandLine.nextLine();
                    System.out.println("Please give the port number:");
                    portNumber = commandLine.nextInt();
                    commandLine.nextLine(); // This is to get rid of the new line character after reading an int
                    System.out.println("Connecting to auction house...");
                    try {
                        auctionHouse = connector.connectToAuctionHouseServer(hostName, portNumber);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    private void printCommands() {
        System.out.println("User Options:");
        System.out.println("BankMsg -- Send a message to the bank");
        System.out.println("(a)uction houses -- Get a list of auction houses from the bank");
        System.out.println(("(c)onnect to an auction house"));
        System.out.println("(b)get account balance");

        // If the agent is connected to an auction house then print additional options
        if(auctionHouse != null) {
            System.out.println();
            System.out.println("Auction House:");
            System.out.println("(g)et list of current auctions");
        }
        // Check account balance
        // check active bids
        // bid
        // etc
    }

    private void getAuctionHouses() {

    }
}
