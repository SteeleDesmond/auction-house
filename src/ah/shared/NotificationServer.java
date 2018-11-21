package ah.shared;


import ah.auction.AuctionHouseService;
import ah.bank.BankService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Used by Servers to setup port and hostname
 */
public class NotificationServer {

    private String serverType;
    //private String hostName;
    private int portNumber;
    private BankService bank;
    private AuctionHouseService auctionHouse;

    public NotificationServer(int portNumber, String serverType) throws IOException {
        this.serverType = serverType;
        //this.hostName = hostName;
        this.portNumber = portNumber;
        switch(serverType.toLowerCase()) {
            case("bank"): {
                bank = new BankService();
                break;
            }
            case("auction house"): {
                auctionHouse = new AuctionHouseService();
                break;
            }
        }
        startServer();
    }

    public void startServer() throws IOException {
        ServerSocket  serverSocket = new ServerSocket(portNumber);

        // Create new Bank thread or AuctionHouse thread depending on the type of server
        switch(serverType.toLowerCase()) {
            case("bank"): {
                Thread t = new Thread(bank);
                t.start();
                break;
            }
            case("auction house"): {
                Thread t = new Thread(auctionHouse);
                t.start();
                break;
            }
        }
        System.out.println("Server successfully started");

        //  Listen  for  new  clients  forever
        while(true) {
            //  Create  new  thread  to  handle  each  client
            Socket clientSocket = serverSocket.accept();
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
            //KnockKnock kk = new KnockKnock(clientSocket);
            //Thread t = new Thread(kk);
            //t.start();
        }
    }
}
