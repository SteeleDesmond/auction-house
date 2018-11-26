package ah.auction;

import java.util.Scanner;
import ah.shared.CommunicationService;
import ah.shared.NotificationServer;

/**
 * The AuctionHouse class connects the auction house server and client to the bank service, opens the auction house
 * service, and starts the auction house client.
 */
public class AuctionHouse {

    private CommunicationService bankServer; // The bank to connect to
    private NotificationServer auctionServer; // This service

    public static void main(String[] args) {

        AuctionHouse auctionHouse = new AuctionHouse();
        String name; // The name of the auction house
        String hostName; // Host name of the bank
        int bankPortNumber; // Port number of the bank
        int auctionPortNumber; // Port number of this auction house's service

        Scanner commandLine = new Scanner(System.in);
        System.out.println("Starting Auction House!");
        System.out.println("Please enter the name of this auction house:");
        name = commandLine.nextLine();
        System.out.println("Please enter the port number to start this auction house on:");
        auctionPortNumber = commandLine.nextInt();
        System.out.println("Starting auction house service...");

        // Start the AuctionHouse server (AuctionHouseService)
        try {
            auctionHouse.auctionServer = new NotificationServer(auctionPortNumber, "auction house");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Please enter the host name of the bank to connect to:");
        hostName = commandLine.nextLine();
        System.out.println("Please enter the bank's port number:");
        bankPortNumber = commandLine.nextInt();
        System.out.println("Connecting to bank service...");

        // Connect to the bank server, start the AuctionHouse client (AuctionHouseController)
        try {
            auctionHouse.bankServer = new CommunicationService(hostName, bankPortNumber, "auction house");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
