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
        aHouse.readInNumber();
        System.out.println("Amount of items you wish to sell: "
                + aHouse.numberOfItems);

        aHouse.readItemList("resrcs/itemList.txt");
        System.out.println("Printing item list:");
        aHouse.printItemList();
    }

    /*
     * reads in the number of items an auction house wishes to sell
     *
     */
    private void readInNumber(){
        System.out.println("Input the number of items you wish to sell," +
                " or d for default");
        Scanner commandLine = new Scanner(System.in);
        String command = commandLine.nextLine();
        try{
            int getItems = Integer.parseInt(command);
            numberOfItems = getItems;
        }catch (NumberFormatException e){
            if(!command.equals("d")) {
                System.out.println("That is not a number");
            }
        }
        if(defaultItemNum == numberOfItems){
            System.out.println("using default");
        }
    }

    /*
     * reads in a list of items, and stores them in a temporary list
     * then takes numberOfItems from that list, randomly, and
     * stores those items inside the auction house's "inventory" , 'itemList'
     * @param fileName -file to be read in, along with relative path
     * @return true if successful, false if file isn't found
     */
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
