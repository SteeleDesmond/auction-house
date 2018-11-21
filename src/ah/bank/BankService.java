package ah.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class BankService implements Runnable {

    private ArrayList<AuctionHouseAccount> auctionHouses = new ArrayList<>();
    private ArrayList<AgentAccount> agents = new ArrayList<>();

    public BankService() {

    }

    @Override
    public void run() {

    }

    public void addNewClient(Socket s) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        while(true) {
            if(in.readLine() != null)
            System.out.println("BankService received message: " + in.readLine());
        }
    }
}
