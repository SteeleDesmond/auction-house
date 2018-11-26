package ah.auction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class AuctionHouseService implements Runnable {

    private LinkedList<Item> itemList = new LinkedList<>();

    public AuctionHouseService() {

        // Register with the static bank

//        System.out.println("In auction house.");
//        Scanner commandLine = new Scanner(System.in);
//        System.out.println("Read in file__:");
//        String command = commandLine.nextLine();
//        readItemList(command);
//        System.out.println("Printing item list:");
//        printItemList();
    }

    @Override
    public void run() {

    }

    public void addNewClient(Socket s) {

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
}
