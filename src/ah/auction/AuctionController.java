package ah.auction;

import ah.shared.BankProxy;
import ah.shared.CommunicationService;

import java.util.Scanner;

public class AuctionController {
    private Scanner commandLine = new Scanner(System.in);
    private Boolean running = true;

    private AuctionHouse store;
    private BankProxy bankProxy;
    private CommunicationService bankServer;

    public AuctionController(Scanner input){
        store = new AuctionHouse(input);
        System.out.println("What machine is the bank on?");
        String bankHost = input.nextLine();
        System.out.println("What port number is it on?");
        int bankPort = input.nextInt();
        try {
            bankProxy = bankServer.connectToBankServer(bankHost, bankPort,"bank");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Alright, now, ");
    }

    public static void main(String[] args){
        Scanner commandLine = new Scanner(System.in);
        AuctionController ac = new AuctionController(commandLine);
        ac.run();


    }













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
