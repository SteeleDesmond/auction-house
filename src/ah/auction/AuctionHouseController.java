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
                case ("bankMsg"): {
                    System.out.println("Type a message to send to the bank service");
                    bank.sendMsg(commandLine.nextLine());
                    break;
                }
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
        System.out.println("bankMsg -- Send a message to the bank");
        System.out.println("ahMsg -- Send a message to the AH Server");
        // list auction
        // start bidding
        // etc.
    }
}
