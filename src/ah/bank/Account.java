package ah.bank;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * The Account class is used for methods that both AuctionHouse Accounts and Agent Accounts can use.
 */
public class Account implements Runnable {

    private int accountBalance;
    private BankService parent;
    private PrintWriter out;
    private BufferedReader in;

    public Account() {

    }

    public Account(BankService parent, PrintWriter out, BufferedReader in) {
        this.parent = parent;
        this.out = out;
        this.in = in;
        accountBalance = 0;
    }

    @Override
    public void run() {

    }

    public int getBalance() {
        return accountBalance;
    }

    public void depositFunds(int amount) {
        accountBalance += amount;
    }
}
