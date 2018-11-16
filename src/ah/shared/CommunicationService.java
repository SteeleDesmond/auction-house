package ah.shared;


import java.io.*;
import java.net.Socket;

/**
 * Used by clients to connect to given port and hostname (server)
 */
public class CommunicationService {

    private String hostName;
    private int portNumber;

    public CommunicationService(String hostName, int portNumber) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
        connectToServer();
    }

    private void connectToServer() throws IOException {

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer = in.readLine();
            while (fromServer != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye.")) {
                    break;
                }
                String fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
                fromServer = in.readLine();
            }
        }
    }
}
