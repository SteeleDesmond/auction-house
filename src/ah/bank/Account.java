package ah.bank;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * The Account class is used for methods that both AuctionHouse Accounts and Agent Accounts can use.
 */
public class Account implements Runnable {

    private BankService parent;
    private PrintWriter out;
    private BufferedReader in;

    public Account() {

    }

    public Account(BankService parent, PrintWriter out, BufferedReader in) {
        this.parent = parent;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {

    }
}
