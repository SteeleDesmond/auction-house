package ah.auction;

import ah.shared.BankProxy;
import ah.shared.CommunicationService;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class AuctionHouseService implements Runnable {

    private BankProxy bank;
    //private LinkedList<Item> itemList = new LinkedList<>();

    public AuctionHouseService(BankProxy bank, String name) {
        this.bank = bank;
        // Register with the static bank
    }

    @Override
    public void run() {
        Scanner commandLine = new Scanner(System.in);
        AuctionHouse ah = new AuctionHouse(commandLine);

        commandLine.close();
    }

    public void addNewClient(Socket s) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        while(true) {
            if(in.readLine() != null)
                System.out.println("AHService received message: " + in.readLine());
        }
    }



}
