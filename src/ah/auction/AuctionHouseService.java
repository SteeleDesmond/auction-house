package ah.auction;

import ah.shared.BankProxy;
import ah.shared.CommunicationService;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class AuctionHouseService implements Runnable {

    private BankProxy bank;
    private LinkedList<Item> itemList = new LinkedList<>();

    public AuctionHouseService(BankProxy bank) {
        this.bank = bank;
        // Register with the static bank
    }

    @Override
    public void run() {
        //        System.out.println("In auction house.");
        //        Scanner commandLine = new Scanner(System.in);
        //        System.out.println("Read in file__:");
        //        String command = commandLine.nextLine();
        //        readItemList(command);
        //        System.out.println("Printing item list:");
        //        printItemList();
    }

    public void addNewClient(Socket s) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        while(true) {
            if(in.readLine() != null)
                System.out.println("AHService received message: " + in.readLine());
        }
    }


    private boolean readItemList(String fileName){
        try{
            Scanner readin = new Scanner(new FileReader(fileName));
            //Note adds ALL things in text file to auction house  itemlist
            while(readin.hasNextLine()){
                String input = readin.nextLine();
                Item item = new Item(input);
                itemList.add(item);
            }
            readin.close();
        }catch (FileNotFoundException ex){
            System.out.println("file not found");
            return false;
        }

        return true;
    }

    private void printItemList(){
        System.out.println("Items Available: ");
        if(!itemList.isEmpty()) {
            for (Item i : itemList) {
                System.out.println(i);
            }
        }else{
            System.out.println("nothing");
        }
    }

    /**
     * Post an auction for bidding. Used by the AuctionHouseClient only
     */
    protected void postAuction() {

    }
}
