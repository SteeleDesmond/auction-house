package ah.shared;


import ah.shared.enums.BankMessages;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;

public class BankProxy {

    private PrintWriter bankOut;
    private BufferedReader bankIn;
    private boolean waiting;
    private String input;

    public BankProxy(PrintWriter bankOut, BufferedReader bankIn) {
        this.bankOut = bankOut;
        this.bankIn = bankIn;
    }

    public void sendMsg(String msg) {
        bankOut.println(msg);
    }

    public void checkBalance() throws IOException {
        waiting = true;
        //bankOut.println("TESTING");
        bankOut.println(BankMessages.GETBALANCE);
        System.out.println("Waiting for response from Bank...");
        while(waiting) {
            if((input = bankIn.readLine()) != null) {
                System.out.println(input);
                if(input.equalsIgnoreCase(BankMessages.SUCCESS.name())) { // If success message was sent
                    System.out.println(bankIn.readLine()); // Read the next input. 2 messages should be sent
                }
                //System.out.println(input);
                waiting = false;
            }
        }
    }

    public void deposit(int amount) {
        bankOut.println(BankMessages.DEPOSIT);
        bankOut.println(amount);

    }

    public void withdraw(int amount) {
        bankOut.println(BankMessages.WITHDRAW);
        bankOut.println(amount);
    }
}
