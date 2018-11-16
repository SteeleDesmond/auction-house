package ah.auction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;
import ah.shared.Item;


public class AuctionHouse {
    private LinkedList<Item> itemList;

    public AuctionHouse(){
        itemList = new LinkedList<Item>();
    }

    public static void main(String[] args){
        System.out.println("In auction house.");
        AuctionHouse aHouse = new AuctionHouse();
        Scanner commandLine = new Scanner(System.in);
        System.out.println("Read in file__:");
        String command = commandLine.nextLine();
        aHouse.readItemList(command);
        System.out.println("Printing item list:");
        aHouse.printItemList();
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

    //read in file
    //use scanner

   // Scanner input = new Scanner(System.in);
    //String command;
    //command = input.nextLine();
    //command = command.toLowerCase();

}
