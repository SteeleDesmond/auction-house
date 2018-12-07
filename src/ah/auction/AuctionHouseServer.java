package ah.auction;

import java.util.Scanner;
import ah.shared.CommunicationService;
import ah.shared.NotificationServer;

/**
 * The AuctionHouseServer class connects the auction house server and client to the bank service, opens the auction house
 * service, and starts the auction house client.
 */
public class AuctionHouseServer {

    private NotificationServer auctionServer; // This service

    public static void main(String[] args) {
        int portNumber; // Port number of this auction house's service
        Scanner commandLine = new Scanner(System.in);
//        if(args.length>0) {
//            //get the info from args, if we want that set up
//
//        }else{
            System.out.println("Please enter the port number to start this auction house on:");
            portNumber = commandLine.nextInt();
//        }
        AuctionHouseServer auctionHouseServer = new AuctionHouseServer();

        try {
            auctionHouseServer.auctionServer = new NotificationServer(portNumber, "auction house");
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        //ah, found the main...alright then...Going to need to
        //a) ensure that args is set up
        //b) if not, use command line
        //then, I need to Figure out the server type of thing.
        //also, the stuff for the Ah proxy.

    }
}
