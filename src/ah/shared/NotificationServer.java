package ah.shared;


import ah.auction.AuctionHouseService;
import ah.bank.BankService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Used by Servers to open a port and start its service. Uses a ServerSocket to allow clients to connect to the service.
 * Clients are automatically accepted upon request. The server is used for BankService and AuctionHouseService.
 */
public class NotificationServer {

    private String serverType; // Used to distinguish between Bank servers and AuctionHouseServer servers
    private int portNumber; // The port to open the service on
    private BankService bank;
    private AuctionHouseService auctionHouse;
    private CommunicationService connector = new CommunicationService(); // Used to connect the AH Service to the Bank

    public NotificationServer(int portNumber, String serverType) throws IOException {
        this.serverType = serverType;
        this.portNumber = portNumber;
        startServer();
    }

    /**
     * Open the ServerSocket and start a BankService or an AuctionHouseService on the given port. This method starts a
     * server and serves as a thread for servers that listens for client connections. When a client connects it is
     * automatically accepted and the socket is given to the corresponding service to handle.
     * The method is public in case a server needs to reconnect.
     * @throws IOException Generally thrown when the port is unavailable
     */
    public void startServer() throws IOException {
        ServerSocket  serverSocket = new ServerSocket(portNumber);

        // Create new Bank thread or AuctionHouseServer thread depending on the type of server
        switch(serverType.toLowerCase()) {
            case("bank"): {
                bank = new BankService();
                //Thread t = new Thread(bank);
                //t.start();
                break;
            }
            // If this is an auction house server, connect to the bank service first
            case("auction house"): {

                // If it is an auction house server then connect to the bank server. The AHService uses a BankProxy.
                System.out.println("Please enter the host name of the bank server");
                Scanner commandLine = new Scanner(System.in);
                String hostName = commandLine.nextLine();
                System.out.println("Please enter the bank's port number:");
                int portNumber = commandLine.nextInt();
                commandLine.nextLine(); // This is to remove the new line character after the nextInt function
                BankProxy bankProxy = connector.connectToBankServer(hostName, portNumber,
                        "auction house", this.portNumber);

                auctionHouse = new AuctionHouseService(bankProxy);
                Thread t = new Thread(auctionHouse);
                t.start();
                break;
            }
        }
        System.out.println("Server is running.");

        // Listen  for  new  clients  forever
        while(true) {
            Socket clientSocket = serverSocket.accept(); // This method blocks until a connection is made
            // If a client is trying to connect, accept and pass the socket connection to the corresponding service
            if(clientSocket != null) {
               // System.out.println("Socket has connected");
                switch(serverType.toLowerCase()) {
                    case("bank"): {
                        bank.handleNewConnection(clientSocket);
                        break;
                    }
                    case("auction house"): {
                        //System.out.println("Auction house client is trying to connect");
                        auctionHouse.addNewClient(clientSocket);
                        break;
                    }
                }
            }
        }
    }
}
