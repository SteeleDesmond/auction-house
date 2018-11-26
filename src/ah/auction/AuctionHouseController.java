package ah.auction;

import ah.shared.AuctionHouseProxy;
import ah.shared.BankProxy;

import java.util.Scanner;

public class AuctionHouseController implements Runnable {

    private AuctionHouseProxy auctionHouse;
    private BankProxy bank;
    private Scanner commandLine = new Scanner(System.in);
    private Boolean running = true;

    public AuctionHouseController(BankProxy bank, AuctionHouseProxy auctionHouse) {
        this.bank = bank;
        this.auctionHouse = auctionHouse;
    }

    @Override
    public void run() {
        while(running) {
            printCommands();
            switch(commandLine.nextLine()) {
                case ("sendMsg"): {
                    System.out.println("Type a message to send to the bank");
                    bank.sendMsg(commandLine.nextLine());
                }
            }
        }
    }

    private void printCommands() {
        System.out.println("Auction House Client Console Options:");
        System.out.println("sendMsg -- Send a message to the bank");
        // list auction
        // start bidding
        // etc.
    }
}
