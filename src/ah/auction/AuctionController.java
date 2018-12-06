package ah.auction;

import java.util.Scanner;

public class AuctionController {
    private Scanner commandLine = new Scanner(System.in);
    private Boolean running = true;

    public void run() {
        while(running) {
            printCommands();
            switch(commandLine.nextLine()) {
                case ("quit"):
                    running = false;
                    //keep in mind you need a safe quit option
                    break;
                case("help"):
                    printCommands();
                    break;




//                case ("ahMsg"): {
//                    System.out.println("Type a message to send to the Auction House service");
//                    auctionHouse.sendMsg(commandLine.nextLine());
//                    break;
//                }
            }
        }
    }

    private void printCommands() {
        System.out.println("Auction House Client Console Options:");
        System.out.println("ahMsg -- Send a message to the AH Server");
        // list auction
        // start bidding
        // etc.
    }

}
