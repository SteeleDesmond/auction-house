package ah.bank;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AgentAccount extends Account {

    private BankService parent;
    private PrintWriter out;
    private BufferedReader in;

    public AgentAccount(BankService parent, PrintWriter out, BufferedReader in) {
        this.parent = parent;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {

    }
}
