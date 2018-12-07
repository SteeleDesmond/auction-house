package ah.auction;

import ah.shared.BankProxy;
import ah.shared.CommunicationService;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuctionHouseService implements Runnable {
    private ExecutorService pool = Executors.newCachedThreadPool();
    private LinkedList<Bidder> bidders = new LinkedList();
    private AuctionHouse ah;
    private BankProxy bank;
    private Scanner commandLine;
    //private LinkedList<Item> itemList = new LinkedList<>();

    public AuctionHouseService(BankProxy bank) {
        this.bank = bank;
        // Register with the static bank
        commandLine = new Scanner(System.in);
        ah = new AuctionHouse(commandLine);

    }

    @Override
    public void run() {



        //commandLine.close();
    }

    public void addNewClient(Socket s) throws IOException {
        System.out.println("entering addnewclient");
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println("past in and out streams");

        /* only agents are connecting to the auction house
         */
//        String type;
//        if((type = in.readLine()) != null) {
//            System.out.println("BankService received connection type: " + type);
//        }
//        else {
//            // There should be a better way to handle the connection in case the message isn't sent
//            System.out.println("Connection message not received");
//            return;
//        }

        String name = "temp";//in.readLine();
        //create a bidder
        Bidder newbidder =  new Bidder(name,out,in,this);
        bidders.add(newbidder);
        pool.execute(newbidder);

//        while(true) {
//            if(in.readLine() != null)
//                System.out.println("AHService received message: " + in.readLine());
//        }
    }

    public String getInventory(){
        return ah.getInventoryList();
    }



}




































