package ah.bank;

import ah.shared.NotificationServer;

import java.util.Scanner;

/**
 * Main method used to start a BankService. Takes a port number and starts a BankService, opens a ServerSocket
 */
public class Bank {

    private NotificationServer bankServer;

    public Bank() {
    }

    public Bank(int portNumber) {

        try {
            bankServer = new NotificationServer(portNumber, "bank");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Bank bank = new Bank();
        String serverType = "bank";
        int portNumber;
        System.out.println("Starting Bank!");

        if (args.length > 0){
            portNumber = Integer.parseInt(args[0]);

        } else if (args.length == 0) {

            portNumber = 2000;

        } else {
            Scanner commandLine = new Scanner(System.in);
            System.out.println("Please enter the port number to start on:");
            portNumber = commandLine.nextInt();
        }

        System.out.println("Starting bank service...");
        try {
            bank.bankServer = new NotificationServer(portNumber, serverType);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
