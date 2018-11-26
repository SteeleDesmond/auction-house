package ah.auction;

import ah.shared.AuctionHouseProxy;
import ah.shared.BankProxy;
import ah.shared.CommunicationService;

import java.util.Scanner;

public class AuctionHouseClient {

    private CommunicationService bankServer; // The bank to connect to

    public static void main(String[] args) {
        BankProxy bankProxy;
        AuctionHouseProxy ahProxy;
        AuctionHouseController auctionHouseController;
        AuctionHouseClient auctionHouseClient = new AuctionHouseClient();
        String hostName;
        int portNumber;

        Scanner commandLine = new Scanner(System.in);

        auctionHouseClient.bankServer = new CommunicationService();
        // Connect to the bank and auction house servers, start the AuctionHouse client (AuctionHouseController)
        try {

            System.out.println("Please enter the host name of the auction house to connect to:");
            hostName = commandLine.nextLine();
            System.out.println("Please enter the Auction House's port number:");
            portNumber = commandLine.nextInt();
            System.out.println("Connecting to auction house service...");
            ahProxy = auctionHouseClient.bankServer.connectToAuctionHouseServer(hostName, portNumber);

            auctionHouseController = new AuctionHouseController(ahProxy);
            Thread t = new Thread(auctionHouseController);
            t.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
