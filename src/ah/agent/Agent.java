package ah.agent;

import ah.auction.AuctionHouse;
import ah.bank.Bank;
import ah.shared.CommunicationService;

import java.io.IOError;
import java.io.IOException;
import java.util.Scanner;

public class Agent {

    private AuctionHouseProxy ahProxy;
    private BankProxy bankProxy;
    private CommunicationService bankServer;



    public static void main(String[] args) {
        //AgentControl agentControl = new AgentControl(args);
        String hostName;
        int portNumber;

        Scanner commandLine = new Scanner(System.in);
        System.out.println("Please enter host name:");
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
        //agentControl.start();
    }
}
