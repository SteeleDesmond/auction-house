package ah.agent;

import ah.agent.display.AgentDisplay;
import ah.shared.BankProxy;
import ah.shared.CommunicationService;
import javafx.stage.Stage;

import java.util.Scanner;


/**
 * The Agent class connects to the bank service via given host/port information and starts the AgentController.
 * AgentController can request information from the bank and connect to AuctionHouseServer services.
 */
public class Agent {

    AgentController agentController;
    AgentDisplay agentDisplay;

    private CommunicationService bankServer;

    public Agent() {




    }

    public static void main(String[] args) {

        BankProxy bankProxy; // The BankProxy is used by agent and auction
        AgentController agentController;
        Agent agent = new Agent();

        //Bank Connect user input
        String hostName;
        int portNumber;

        Scanner commandLine = new Scanner(System.in);
        System.out.println("Starting Agent!");
        System.out.println("Please enter the host name of the bank to connect to:");
        hostName = commandLine.nextLine();
        System.out.println("Please enter the port number:");
        portNumber = commandLine.nextInt();

        agent.bankServer = new CommunicationService();
        System.out.println("Connecting to the bank server...");
        try {
            bankProxy = agent.bankServer.connectToBankServer(hostName, portNumber, "agent");
            agentController = new AgentController(bankProxy);
            Thread t = new Thread(agentController);
            t.start();

            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
