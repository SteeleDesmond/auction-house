package ah.shared;


import ah.agent.AgentController;

import java.io.*;
import java.net.*;

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

        AgentController agentController;

        try
        {
            InetAddress addr = InetAddress.getByName(hostName);
            SocketAddress sockaddr = new InetSocketAddress(addr, portNumber);
            Socket sock = new Socket();

            // this method will block for the defined number of milliseconds
            int timeout = 2000;
            sock.connect(sockaddr, timeout);

            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            agentController = new AgentController(out, in);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }


//        try (Socket socket = new Socket(hostName, portNumber);
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
//        {
//            agentController = new AgentController(out, in);
//        }


    }
}
