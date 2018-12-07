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

        userAccount = new UserAccount();
    }

    @Override
    public void run() {
        while(running) {
            printCommands();
            switch(commandLine.nextLine()) {
                case ("BankMsg"): {
                    System.out.println("Please enter a command:");
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

                        System.out.println("Balance: "+ bank.checkBalance());
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
                        if(bank.deposit(Integer.valueOf(input))){
                            System.out.println("transaction success");
                        } else {
                            System.out.println("transaction failed");
                        }
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
                        if (bank.withdraw(Integer.valueOf(input))){
                            System.out.println("transaction success");

                        } else {
                            System.out.println("transaction failed");
                        }
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
                        auctionHouse.sendBiddingKey(bank.getBiddingKey());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case("g"): {
                    try {
                        getAuctionsList();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case("t"): {
                    System.out.println("Enter the amount to transfer:");
                    String input = commandLine.nextLine();
                    if(input.equals("")) {
                        System.out.println("Invalid amount entered");
                        break;
                    }
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
                case("bid"): {
                    System.out.println("Enter the name of the item to bid on");
                    String item = commandLine.nextLine();
                    System.out.println("Enter the amount to bid:");
                    String input = commandLine.nextLine();
                    if(input.equals("")) {
                        System.out.println("Invalid amount entered");
                        break;
                    }
                    int amount = Integer.valueOf(input);
                    try {
                        auctionHouse.bid(item, amount);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default: {
                    System.out.println("Invalid input given");
                }
            }
        }
    }

    private void printCommands() {
        System.out.println();
        System.out.println("User Options:");
        System.out.println();
        System.out.println("Bank Options");
        System.out.println("(b)get account balance");
        System.out.println("(d)eposit money in bank account");
        System.out.println("(w)ithdraw money from bank account");
        System.out.println("(a)uction houses -- Get a list of auction houses from the bank");
        System.out.println("(c)onnect to an auction house");
        System.out.println("(key) -- get bidding key");

        // If the agent is connected to an auction house then print additional options
        if(auctionHouse != null) {
            System.out.println();
            System.out.println("Auction House Options:");
            System.out.println("(g)et list of current auctions");
            System.out.println("(t)ransfer funds to an Auction House");
            System.out.println("(bid) bid on an auction");
            System.out.println("(won) get a list of auctions that this agent has won and the amount owed");
        }
        System.out.println();
    }

    /**
     * Request a list of auctions from the auction house
     */
    private void getAuctionsList() throws IOException{
        ArrayList<String> auctions = auctionHouse.getAuctions();
        int i = 1;
        for(String s : auctions) {
            System.out.print(i + ": ");
            i++;
            System.out.println(s);
        }

         //System.out.println(" enter 'x' to exit");

//        while (true) {
//            System.out.println(" enter number of item to getAuctionsList: ");
//            Scanner sc = new Scanner(System.in);
//            String input = sc.nextLine();
//
//            if (input.equals('x')) {
//                break;
//            }
//            int itemNumber = Integer.parseInt(sc.nextLine());
//        }

    }
}
