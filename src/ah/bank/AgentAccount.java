package ah.bank;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AgentAccount extends Account {

    private boolean accountIsOpen;
    private int accountBalance;
    private BankService parent;
    private PrintWriter out;
    private BufferedReader in;
    private String personalKey = new KeyGenerator().getKey();

    public AgentAccount(BankService parent, PrintWriter out, BufferedReader in) {
        this.parent = parent;
        this.out = out;
        this.in = in;
        accountBalance = 0;
        accountIsOpen = true;
        System.out.println("New Agent account created!");
    }

    public String getAgentKey() {
        return personalKey;
    }

}
