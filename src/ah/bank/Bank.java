package ah.bank;

import ah.shared.NotificationServer;

import java.util.Scanner;

public class Bank {

    private NotificationServer bankServer;

    public static void main(String[] args) {

        String serverType = "bank";
        //String hostName;
        int portNumber;
        System.out.println("Starting Bank!");
        Scanner commandLine = new Scanner(System.in);
        //System.out.println("Please enter host name:");
        //hostName = commandLine.nextLine();
        System.out.println("Please enter port number to start on:");
        portNumber = commandLine.nextInt();

        Bank bank = new Bank();

        try {
            bank.bankServer = new NotificationServer(portNumber, serverType);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
