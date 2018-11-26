package ah.shared;


import ah.agent.AgentController;
import ah.auction.AuctionHouseController;

import java.io.*;
import java.net.*;

/**
 * Used by clients to connect to a given host and port. The Agent and the AuctionHouse both have clients that connect to
 * the Bank service. When an Agent or Auction House are created they are given the location of the bank and they setup
 * a local proxy connection to the bank.
 */
public class CommunicationService {

    /**
     * The bank server receives connections from agent clients and auction house services. For this application, the
     * bank server receives a message after a client connects telling it which type of client it is.
     * @param hostName The host name of the bank server
     * @param portNumber The port number of the bank server
     * @param clientType The type of client trying to connect. Must be "agent" or "auction house".
     * @return A bank proxy that handles the interactions with the auction house server.
     * @throws IOException
     */
    public BankProxy connectToBankServer(String hostName, int portNumber, String clientType) throws IOException {
        BankProxy bankProxy; // The BankProxy is used by agent and auction

        try {
            // Relevant information: https://alvinalexander.com/blog/post/java/java-class-writes-reads-remote-socket
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(hostName), portNumber);
            Socket socket = new Socket();
            socket.connect(socketAddress);
            System.out.println("Connected successfully.");

            // Create a BankProxy object which uses the socket input/output streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(clientType); // Tell the bank which kind of client this is
            bankProxy = new BankProxy(out, in);
            return bankProxy;
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * The auction house service receives connections from agents only. This method is used by agents to connect to an
     * auction house.
     * @param hostName The host name of the auction house server
     * @param portNumber The port number of the auction house server
     * @return An auction house proxy that handles the interactions with the auction house server.
     * @throws IOException
     */
    public AuctionHouseProxy connectToAuctionHouseServer(String hostName, int portNumber)
            throws IOException {
        AuctionHouseProxy ahProxy;

        try {
            // Connect to the Auction House Server
            SocketAddress ahServerAddress = new InetSocketAddress(InetAddress.getByName(hostName), portNumber);
            Socket ahSocket = new Socket();
            ahSocket.connect(ahServerAddress);
            System.out.println("Connected successfully.");

            // Create an AuctionHouseProxy object which uses the socket input/output streams
            PrintWriter AhOut = new PrintWriter(ahSocket.getOutputStream(), true);
            BufferedReader AhIn = new BufferedReader(new InputStreamReader(ahSocket.getInputStream()));
            ahProxy = new AuctionHouseProxy(AhOut, AhIn);
            return ahProxy;
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
