package ah.auction;

import ah.shared.BankProxy;

import java.util.Scanner;

public class AuctionHouseController implements Runnable {

    private BankProxy bank;
    private Scanner commandLine = new Scanner(System.in);

    public AuctionHouseController(BankProxy bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        printCommands();
        switch(commandLine.nextLine()) {
            case ("BankMsg"): {
                System.out.println("Type a message to send to the bank");
                bank.sendMsg(commandLine.nextLine());
            }
        }
    }

    private void printCommands() {
        System.out.println("Auction House Client Console Options:");
        System.out.println("BankMsg -- Send a message to the bank");
        // list auction
        // start bidding
        // etc.
    }
}
