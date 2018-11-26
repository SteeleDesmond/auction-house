package ah.agent;

import ah.shared.BankProxy;

import java.util.Scanner;

public class AgentController implements Runnable {

    private ActiveBids activeBids;
    private UserCommands userCommands;
    private UserDisplay userDisplay;
    private UserAccount userAccount;

    private BankProxy bank;
    private Scanner commandLine = new Scanner(System.in);
    Boolean running = true;

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
                }
            }
        }
    }

    private void printCommands() {
        System.out.println("User Options:");
        System.out.println("BankMsg -- Send a message to the bank");
        // Check account balance
        // check active bids
        // bid
        // etc
    }
}
