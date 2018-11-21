package ah.agent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class AgentController {

    AuctionHouseProxy ahProxy;
    ActiveBids activeBids;
    UserCommands userCommands;
    UserDisplay userDisplay;
    UserAccount userAccount;
    BankProxy bankProxy;

    private PrintWriter out;
    private BufferedReader in;

    public AgentController(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;

        out.println("Sending information from Agent");
        while(true) {
            System.out.println("Type a message to send to the bank");
            Scanner commandLine = new Scanner(System.in);
            out.println(commandLine.nextLine());
        }
//        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//        String fromServer = in.readLine();
//        while (fromServer != null) {
//            System.out.println("Server: " + fromServer);
//            if (fromServer.equals("Bye.")) {
//                break;
//            }
//            String fromUser = stdIn.readLine();
//            if (fromUser != null) {
//                System.out.println("Client: " + fromUser);
//                out.println(fromUser);
//            }
//            fromServer = in.readLine();
//        }
    }


    public void start() {

    }
}
