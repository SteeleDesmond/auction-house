package ah.shared;


import ah.auction.AuctionHouseService;
import ah.bank.BankService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Used by Servers to open a port and start its service. Uses a ServerSocket to allow clients to connect to the service.
 * Clients are automatically accepted upon request. The server is used for BankService and AuctionHouseService.
 */
public class NotificationServer {

    private String serverType; // Used to distinguish between Bank servers and AuctionHouseServer servers
    private int portNumber; // The port to open the service on
    private BankService bank;
    private AuctionHouseService auctionHouse;

    public NotificationServer(int portNumber, String serverType) throws IOException {
        this.serverType = serverType;
        this.portNumber = portNumber;
        startServer();
    }

    /**
     * Open the ServerSocket and start a BankService or an AuctionHouseService on the given port.
     * @throws IOException Generally thrown when the port is unavailable
     */
    public void startServer() throws IOException {
        ServerSocket  serverSocket = new ServerSocket(portNumber);

        // Create new Bank thread or AuctionHouseServer thread depending on the type of server
        switch(serverType.toLowerCase()) {
            case("bank"): {
                bank = new BankService();
                Thread t = new Thread(bank);
                t.start();
                break;
            }
            case("auction house"): {
                auctionHouse = new AuctionHouseService();
                Thread t = new Thread(auctionHouse);
                t.start();
                break;
            }
        }
        System.out.println("Server is running.");

        // Listen  for  new  clients  forever
        while(true) {
            Socket clientSocket = serverSocket.accept(); // This method blocks until a connection is made
            // If a client is trying to connect, accept and pass the socket request to the corresponding service
            if(clientSocket != null) {
                switch(serverType.toLowerCase()) {
                    case("bank"): {
                        bank.addNewClient(clientSocket);
                        break;
                    }
                    case("auction house"): {
                        auctionHouse.addNewClient(clientSocket);
                        break;
                    }
                }
            }
        }
    }
}
