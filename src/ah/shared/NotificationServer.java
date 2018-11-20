package ah.shared;


import ah.auction.AuctionHouseController;
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

    public NotificationServer(int portNumber, String serverType) throws IOException {
        this.serverType = serverType;
        //this.hostName = hostName;
        this.portNumber = portNumber;
        startServer();
    }

    public void startServer() throws IOException {
        BankService bank = new BankService();
        AuctionHouseController auctionHouse = new AuctionHouseController();
        ServerSocket  serverSocket = new ServerSocket(portNumber);

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
                        bank.addNewAgent(clientSocket);
                        break;
                    }
                    case("auction house"): {
                        auctionHouse.addNewAgent(clientSocket);
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
