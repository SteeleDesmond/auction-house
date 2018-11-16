package ah.shared;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Used by Servers to setup port and hostname
 */
public class NotificationServer {

    private String hostName;
    private int portNumber;

    public NotificationServer(String hostName, int portNumber) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
        startServer();
    }

    public void startServer() throws IOException {
        ServerSocket  serverSocket = new ServerSocket(portNumber);
        System.out.println("Server successfully started");
        //  Listen  for  new  clients  forever
        while(true) {
            //  Create  new  thread  to  handle  each  client
            Socket clientSocket = serverSocket.accept();
            //KnockKnock kk = new KnockKnock(clientSocket);
            //Thread t = new Thread(kk);
            //t.start();
        }
    }
}
