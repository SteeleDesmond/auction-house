package ah.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankService {

    private ExecutorService executor = Executors.newCachedThreadPool();
    //private ArrayList<AuctionHouseAccount> auctionHouses = new ArrayList<>();
    //private ArrayList<AgentAccount> agents = new ArrayList<>();
    private ArrayList<Account> auctionHouses = new ArrayList<>();
    private ArrayList<Account> agents = new ArrayList<>();
    private int accountId;

    public BankService() {
        accountId = 0;
    }

    /**
     * This method is called by the NotificationServer when a new client has connected to this service. It handles the
     * new connection by creating an appropriate account thread for the client.
     * @param s The client socket that just connected
     * @throws IOException Error connecting
     */
    public void handleNewConnection(Socket s) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        /*
         * When a client first connects to the BankService it sends a message of "agent" or "auction house" specifying
         * which type of client it is.
         */
        String type;
        if((type = in.readLine()) != null) {
            System.out.println("BankService received connection type: " + type);
        }
        else {
            // There should be a better way to handle the connection in case the message isn't sent
            System.out.println("Connection message not received");
            return;
        }
        // Create an Account for the client and start its thread to listen for and handle requests individually
        switch(type) {
            case("agent"): {
                //AgentAccount agent = new AgentAccount(this, out, in);
                Account agent = new Account(accountId, this, out, in);
                accountId++;
                agents.add(agent);
                executor.execute(agent);
                break;
            }
            case("auction house"): {
                //AuctionHouseAccount aHouse = new AuctionHouseAccount(this, out, in);
                Account aHouse = new Account(accountId, this, out, in);
                accountId++;
                auctionHouses.add(aHouse);
                executor.execute(aHouse);
                break;
            }
            default: {
                System.out.println("Issue connecting --> BankService in handleNewConnection");
            }
        }
    }

//    public int getAccountBalance(int accountId) {
//        return agents.get(accountId).getBalance();
//    }
}
