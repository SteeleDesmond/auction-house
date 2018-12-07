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
                String name = "temp name";
                String hostName = "temp host";
                String portNumber = "temp port";
                Account agent = new Account(accountId, name, this, out, in, hostName, portNumber);
                accountId++;
                agents.add(agent);
                executor.execute(agent);
                break;
            }
            case("auction house"): {
                //AuctionHouseAccount aHouse = new AuctionHouseAccount(this, out, in);
                String name = in.readLine(); // Name of auction house is sent after its type
                String hostName = in.readLine();
                String portNumber = in.readLine();
                Account aHouse = new Account(accountId, name, this, out, in, hostName, portNumber);
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
    public ArrayList<Account> getAuctionHouses() {
        return auctionHouses;
    }

    public ArrayList<Account> getAgents() {
        return agents;
    }

    /**
     * Used when an agent requests a transfer to an aHouse.
     * @param agentKey The agent's key
     * @param ahKey The aHouse's key
     * @param amount The amount to transfer
     * @return True if successful, false if there was an error with key validation or insufficient funds
     */
    public boolean transferFunds(String agentKey, String ahKey, int amount) {
        Account agent = null;
        Account aHouse = null;
        for(Account a : agents) {
            if(a.getPersonalKey().equals(agentKey)) {
                agent = a;
            }
        }
        for(Account a : auctionHouses) {
            if(a.getPersonalKey().equals(ahKey)) {
                aHouse = a;
            }
        }
        if(agent == null || aHouse == null) {
            return false;
        }
        if(agent.getAccountBalance() < amount) {
            return false;
        }
        agent.setAccountBalance(agent.getAccountBalance() - amount);
        aHouse.setAccountBalance(aHouse.getAccountBalance() + amount);
        return true;
    }

    public boolean blockFunds(String ahKey, String agentKey, int amount) {
        Account aHouse = null;
        Account agent = null;
        for(Account a : auctionHouses) {
            if(a.getPersonalKey().equals(ahKey)) {
                aHouse = a;
            }
        }
        for(Account a : agents) {
            if(a.getPersonalKey().equals(agentKey)) {
                agent = a;
            }
        }
        if(agent == null || aHouse == null) {
            return false;
        }
        if(agent.getAccountBalance() < amount) {
            return false;
        }
        agent.setHoldsAmount(agent.getHoldsAmount() + amount);
        agent.setAccountBalance(agent.getAccountBalance() - amount);
        return true;
    }

    public boolean unblockFunds(String ahKey, String agentKey, int amount) {
        Account aHouse = null;
        Account agent = null;
        for(Account a : auctionHouses) {
            if(a.getPersonalKey().equals(ahKey)) {
                aHouse = a;
            }
        }
        for(Account a : agents) {
            if(a.getPersonalKey().equals(agentKey)) {
                agent = a;
            }
        }
        if(agent == null || aHouse == null) {
            return false;
        }
        if(agent.getHoldsAmount() < amount) { // Prevent negative holds from happening
            return false;
        }
        agent.setHoldsAmount(agent.getHoldsAmount() - amount);
        agent.setAccountBalance(agent.getAccountBalance() + amount);
        return true;
    }
}
