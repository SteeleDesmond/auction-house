package ah.agent;

import ah.shared.AuctionHouseProxy;
import ah.shared.BankProxy;
import ah.shared.CommunicationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AgentController implements Runnable {

    private ActiveBids activeBids;
    private UserCommands userCommands;
    private UserDisplay userDisplay;
    private UserAccount userAccount;
    private CommunicationService connector = new CommunicationService();
    private AuctionHouseProxy auctionHouse;

    private BankProxy bank;
    private Scanner commandLine = new Scanner(System.in);
    private boolean running = true;
    private String hostName;
    private int portNumber;

    public AgentController(BankProxy bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        while(running) {
            printCommands();
            switch(commandLine.nextLine()) {
                case ("BankMsg"): {
                    System.out.println("Type a message to send to the bank");
                    bank.sendMsg(commandLine.nextLine());
                    break;
                }
                case("a"): {
                    try {
                        ArrayList<String> aHouses = bank.getAuctionHouses();
                        for(String s : aHouses) {
                            System.out.println(s);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case("b"): {
                    try {
                        bank.checkBalance();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case("d"): {
                    System.out.println("Enter amount to deposit:");
                    String input = commandLine.nextLine();
                    try {
                        bank.deposit(Integer.valueOf(input));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case("w"): {
                    System.out.println("Enter amount to withdraw:");
                    String input = commandLine.nextLine();
                    try {
                        bank.withdraw(Integer.valueOf(input));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case("key"): {
                    try {
                        bank.getBiddingKey();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case("c"): {
                    System.out.println("Please give the host name of the auction house:");
                    hostName = commandLine.nextLine();
                    System.out.println("Please give the port number:");
                    portNumber = commandLine.nextInt();
                    commandLine.nextLine(); // This is to get rid of the new line character after reading an int
                    System.out.println("Connecting to auction house...");
                    try {
                        auctionHouse = connector.connectToAuctionHouseServer(hostName, portNumber);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case("t"): {
                    System.out.println("Enter the amount to transfer:");
                    int amountToTransfer = Integer.valueOf(commandLine.nextLine());
                    System.out.println("Please enter the AH Key given:");
                    String ahKey = commandLine.nextLine();
                    try {
                        bank.transferFunds(bank.getBiddingKey(), ahKey, amountToTransfer);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private void printCommands() {
        System.out.println();
        System.out.println("User Options:");
        System.out.println("BankMsg -- Send a message to the bank");
        System.out.println("(a)uction houses -- Get a list of auction houses from the bank");
        System.out.println(("(c)onnect to an auction house"));
        System.out.println("(b)get account balance");
        System.out.println("(d)eposit money in bank account");
        System.out.println("(w)ithdraw money from bank account");
        System.out.println("(key) get bidding key");

        // If the agent is connected to an auction house then print additional options
        if(auctionHouse != null) {
            System.out.println();
            System.out.println("Auction House:");
            System.out.println("(g)et list of current auctions");
            System.out.println("(t)ransfer funds to an Auction House");
        }
        // Check account balance
        // check active bids
        // bid
        // etc
    }

    private void getAuctionHouses() {

    }
}
