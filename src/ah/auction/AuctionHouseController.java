package ah.auction;

import ah.shared.AuctionHouseProxy;

import java.util.Scanner;

public class AuctionHouseController implements Runnable {

    private AuctionHouseProxy auctionHouse;
    private Scanner commandLine = new Scanner(System.in);
    private Boolean running = true;

    public AuctionHouseController(AuctionHouseProxy auctionHouse) {
        this.auctionHouse = auctionHouse;
    }

    @Override
    public void run() {
        while(running) {
            printCommands();
            switch(commandLine.nextLine()) {
                case ("ahMsg"): {
                    System.out.println("Type a message to send to the Auction House service");
                    auctionHouse.sendMsg(commandLine.nextLine());
                    break;
                }
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
