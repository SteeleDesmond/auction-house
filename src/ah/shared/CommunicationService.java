package ah.shared;


import ah.agent.AgentController;
import ah.auction.AuctionHouseController;

import java.io.*;
import java.net.*;

/**
 * Used by clients to connect to a given host and port. The Agent and the AuctionHouseServer both have clients that connect to
 * the Bank service. When an Agent or Auction House are created they are given the location of the bank and they setup
 * a local proxy connection to the bank.
 */
public class CommunicationService {

    private String clientType; // agent or auction house for this application
    private String hostName;
    private int portNumber;

    public CommunicationService(String hostName, int portNumber, String clientType) throws IOException {
        this.clientType = clientType;
        this.hostName = hostName;
        this.portNumber = portNumber;
        connectToServer();
    }

    private void connectToServer() throws IOException {

        BankProxy bankProxy; // The BankProxy is used by agent and auction
        AuctionHouseProxy ahProxy;
        AgentController agentController;
        AuctionHouseController auctionHouseController;

        try {
            // Relevant information: https://alvinalexander.com/blog/post/java/java-class-writes-reads-remote-socket
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(hostName), portNumber);
            Socket socket = new Socket();
            socket.connect(socketAddress);

            // Create a BankProxy object which uses the socket input/output streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bankProxy = new BankProxy(out, in);

            // Instantiate either an agent or an auction house client with the given BankProxy
            switch(clientType.toLowerCase()) {
                case("agent"): {
                    agentController = new AgentController(bankProxy);
                    Thread t = new Thread(agentController);
                    t.start();
                    break;
                }
                case("auction house"): {
                    // Connect to the Auction House Server
                    SocketAddress ahServerAddress = new InetSocketAddress(InetAddress.getByName(hostName), portNumber);
                    Socket ahSocket = new Socket();
                    ahSocket.connect(ahServerAddress);

                    // Create an AuctionHouseProxy object which uses the socket input/output streams
                    PrintWriter AhOut = new PrintWriter(ahSocket.getOutputStream(), true);
                    BufferedReader AhIn = new BufferedReader(new InputStreamReader(ahSocket.getInputStream()));
                    ahProxy = new AuctionHouseProxy(out, in);

                    auctionHouseController = new AuctionHouseController(bankProxy, ahProxy);
                    Thread t = new Thread(auctionHouseController);
                    t.start();
                    break;
                }
            }
            System.out.println("Connected successfully.");
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
