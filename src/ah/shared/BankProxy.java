package ah.shared;


import java.io.BufferedReader;
import java.io.PrintWriter;

public class BankProxy {

    private PrintWriter bankOut;
    private BufferedReader bankIn;

    public BankProxy(PrintWriter bankOut, BufferedReader bankIn) {
        this.bankOut = bankOut;
        this.bankIn = bankIn;
    }

    public void sendMsg(String msg) {
        bankOut.println(msg);
    }

    public void checkBalance() {

    }
}
