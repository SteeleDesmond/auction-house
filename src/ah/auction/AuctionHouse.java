package ah.auction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import ah.shared.Item;


public class AuctionHouse {
    private LinkedList<Item> itemList;
    private int defaultItemNum = 5;
    private int numberOfItems = 5; //for sale, pick from temp list
    private Random rand = new Random(); //temp seed

    public AuctionHouse(){
        itemList = new LinkedList<Item>();
    }

    public static void main(String[] args){
        //System.out.println("In auction house.");
        AuctionHouse aHouse = new AuctionHouse();
        System.out.println("Input the number of items you wish to sell," +
                " or d for default");
        //will stop if num exceeds number if items in list read in
        Scanner commandLine = new Scanner(System.in);
        String command = commandLine.nextLine();
        try{
            int getItems = Integer.parseInt(command);
            aHouse.numberOfItems = getItems;
        }catch (NumberFormatException e){
            if(!command.equals("d")) {
                System.out.println("That is not a number");
            }
        }
        if(aHouse.defaultItemNum == aHouse.numberOfItems){
            System.out.println("using default");
        }
        System.out.println("Items you wish to sell: "+aHouse.numberOfItems);


        aHouse.readItemList("resrcs/itemList.txt");
        System.out.println("Printing item list:");
        aHouse.printItemList();
    }


    private boolean readItemList(String fileName){
        LinkedList<Item> items = new LinkedList<>();
        try{
            Scanner readin = new Scanner(new FileReader(fileName));
            //Note adds ALL things in text file to auction house  itemlist
            while(readin.hasNextLine()){
                String input = readin.nextLine();
                Item item = new Item(input);
                items.add(item);
            }
            readin.close();
        }catch (FileNotFoundException ex){
            System.out.println("file not found");
            return false;
        }
        //need to randomly pick a certain number of items
        while(numberOfItems>0 && !items.isEmpty()){
            int next = rand.nextInt(items.size());
            itemList.add(items.remove(next));
            numberOfItems--;
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
