package ah.bank;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AuctionHouseAccount extends Account {

    private BankService parent;
    private PrintWriter out;
    private BufferedReader in;

    public AuctionHouseAccount(BankService parent, PrintWriter out, BufferedReader in) {
        this.parent = parent;
        this.out = out;
        this.in = in;
        System.out.println("New AH account created!");

    }

//    @Override
//    public void run() {
//
//    }

    public boolean holdFunds(int amount, KeyGenerator key) {

        return true;
    }
}
