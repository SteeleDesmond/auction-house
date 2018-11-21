package ah.bank;

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

    public void addNewClient(Socket s) {

    }
}
