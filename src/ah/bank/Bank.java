package ah.bank;

import ah.shared.NotificationServer;

import java.util.Scanner;

public class Bank {

    private NotificationServer bankServer;

    public static void main(String[] args) {

        String hostName;
        int portNumber;

        Scanner commandLine = new Scanner(System.in);
        System.out.println("Please enter host name:");
        hostName = commandLine.nextLine();
        System.out.println("Please enter port name:");
        portNumber = commandLine.nextInt();

        Bank bank = new Bank();

        try {
            bank.bankServer = new NotificationServer(hostName, portNumber);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
