package ah.agent;

import ah.shared.CommunicationService;

import java.util.Scanner;


/**
 * The Agent class connects to the bank service via given host/port information and starts the AgentController.
 */
public class Agent {

    private AuctionHouseProxy ahProxy;
    private BankProxy bankProxy;
    private CommunicationService bankServer;



    public static void main(String[] args) {
        //AgentController agentController = new AgentController(args);
        String hostName;
        int portNumber;

        Scanner commandLine = new Scanner(System.in);
        System.out.println("Starting Agent!");
        System.out.println("Please enter host name of the bank to connect to:");
        hostName = commandLine.nextLine();
        System.out.println("Please enter port name:");
        portNumber = commandLine.nextInt();

        Agent agent = new Agent();
        try {
            agent.bankServer = new CommunicationService(hostName, portNumber);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //agentController.start();
    }
}
