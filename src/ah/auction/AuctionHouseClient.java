package ah.auction;

import ah.shared.CommunicationService;

import java.util.Scanner;

public class AuctionHouseClient {

    private CommunicationService bankServer; // The bank to connect to

    public static void main(String[] args) {

        AuctionHouseClient auctionHouseClient = new AuctionHouseClient();
        String hostName;
        int portNumber;

        Scanner commandLine = new Scanner(System.in);
        System.out.println("Please enter the host name of the bank to connect to:");
        hostName = commandLine.nextLine();
        System.out.println("Please enter the bank's port number:");
        portNumber = commandLine.nextInt();
        System.out.println("Connecting to bank service...");

        // Connect to the bank server, start the AuctionHouse client (AuctionHouseController)
        try {
            auctionHouseClient.bankServer = new CommunicationService(hostName, portNumber, "auction house");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
