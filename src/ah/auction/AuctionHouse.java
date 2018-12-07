
package ah.auction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;


//need to clean this up a bit.
public class AuctionHouse {
    private String name;
    private String bankID; //keep for now //delete this
    private LinkedList<Item> inventory;
    private int defaultItemNum = 5;//easy access
    private int numberOfItems = 5; //...don't need to change this
    private int maxMinBid = 1000; //easy access
    private Random rand = new Random(); //for all the random needs

    /**
     * creates an Auction house object
     * @param in requires a scanner for input.
     */
    public AuctionHouse(Scanner in){
        //initializing stuff
        inventory = new LinkedList<Item>();

        //auction house creation
        System.out.println("Welcome.");
        System.out.println("Before you can use your auction house," +
                "we need some information");
        System.out.println();
        System.out.println("What do you want to name your Auction House?");
        this.name = in.nextLine();
        System.out.println("Welcome "+this.name+" Auction House");

        System.out.println("How many items do you want to sell?");
        readInNumber(in);
        System.out.println("Amount of items to be sold: "+numberOfItems);

        System.out.println("Importing items...");
        readItemList("resrcs/itemList.txt");
        System.out.println("Complete.");

        System.out.println("Auction House is now operational");

        //bank related things set after creation, or before...just not here
    }

    /**
     * temporary main, used for testing right now.
     * @param args not used
     */
    public static void main(String[] args){
        Scanner commandLine = new Scanner(System.in);
        AuctionHouse ah = new AuctionHouse(commandLine);
        System.out.println(ah.getInventoryList());
        Item item = ah.inventory.getFirst();
        Item item2 = ah.inventory.getLast();
        ah.makeBid(item,30,"bob");
        ah.makeBid(item2,1001,"susie");
        ah.makeBid(item,1001,"mike");
        ah.makeBid(item2,1002,"mary");
        ah.makeBid(item,1001,"darrel");
        System.out.println(ah.getInventoryList());

        commandLine.close();
    }

    /**
     * Gets the list of items the auction is selling, all of them
     * @return a string representation of that information
     */
    public String getInventoryList(){
        String ret="";//name+"\n";
        for(Item i: inventory){
            ret = ret+i.toString();
            ret = ret+"\n";
        }
        return ret;
    }

    /**
     * This function attempts bids on items. It finds the item, ensuring it
     * is actually there, and checks to see if the amount of money is
     * larger than the amount currently in there
     * Assumes funds are in the account, blocked and ready to go
     * does not do the thread stuff, just saves value
     * @param item the item you want the bid to be made on, used to find it
     * @param tryMoney the amount of money you want to bid
     * @param name the name of the bidder
     * @return "NONE" if the item is not there, "REJECT" if the amount you want
     * to bid is not enough, "START" if the bid war on that item has just
     * started, or, if the bid is successful, the name of the previous bidder
     */
    public String makeBid(Item item, int tryMoney, String name){
        Item find = findItemInInventory(item);
//        System.out.println("bidder: "+name+" tryMoney: "
//                +tryMoney+" bidding on: " + find);
        if(find == null){
            //System.out.println(item.getItemName()+" is not in our inventory");
            //item is out of stock or not in the inventory in the first place
            return "NONE";
        }else if(tryMoney>find.getCurrentBid()){ //bid is larger
            if(find.getBidder() == null){ //no one has bid
                //System.out.println("item is now being bid on");
                //set the bid in place
                find.setCurrentBid(tryMoney,name);
                //let the caller know that bidding has started on item
                return "START";
            }else{ //auction is already taking place on the item
                //System.out.println("overtaking bid");
                //save the previous bidder
                String theOutBid = find.getBidder();
                //set the new bidder
                find.setCurrentBid(tryMoney,name);
                //System.out.println(theOutBid+" has been passed");
                //return old bidder, so caller knows who to notify
                return theOutBid;
            }
        }else{
            //System.out.println("Reject bid");
            //basically: bidder did not offer more money on this attempt
            return "REJECT";
        }
    }

    /**
     * getter for the auction house name
     * @return the auction house name
     */
    public String getName() {
        return name;
    }

    public void setBankID(){
        //just set this, later, after bank account gets created, if needed...probs not
    }

    /*
     * looks for the specified item in the ah inventory
     * @param item the thing you are looking for
     * @return item if found, null if not
     */
    private Item findItemInInventory(Item item){
        for(Item i: inventory){
            if(item.equals(i)){
                return i;
            }
        }
        return null;
    }

    /*
     * reads in the number of items an auction house wishes to sell
     * @param scanner, to read in the number of items
     */
    private void readInNumber(Scanner commandLine){
        System.out.println("Input the number of items you wish to sell," +
                " or d for default");
        String command = commandLine.nextLine();
        try{
            int getItems = Integer.parseInt(command);
            if(getItems< 0){
                System.out.println("That is a negative number");
            }else if(getItems==0){
                System.out.println("...That is zero.");
            }else{
                numberOfItems = getItems;
            }
        }catch (NumberFormatException e){
            if(!command.equals("d")) {
                System.out.println("That is not a number");
            }
        }
        if(defaultItemNum == numberOfItems){
            System.out.println("Using default");
        }
    }


    /* @param fileName -file to be read in, along with relative path
     * will randomly generate a price, so people with large amounts of inventory
     * don't have to do all of it by hand...also for fun.
     * @return true if successful, false if file isn't found
     */
    private boolean readItemList(String fileName){ //leaving for encapsulation
        LinkedList<Item> items = new LinkedList<>(); //a temporary list
        int idNum =1;
        try{
            Scanner readin = new Scanner(new FileReader(fileName));
            //adds all items in read in to a temporary list
            while(readin.hasNextLine()){
                String input = readin.nextLine();
                //randomizing the prices, because why not?
                int price = rand.nextInt(maxMinBid);
                if(price == 0){
                    price++; //add one just so the price isn't zero, ever
                }
                //IDs end up consistent across all auction houses
                Item item = new Item(idNum,input,price); //set max bid at top.
                items.add(item);
                idNum++;
            }
            readin.close();
        }catch (FileNotFoundException ex){
            System.out.println("file not found");
            return false;
        }
        //pick a specified number of items, but the items are picked randomly
        while(numberOfItems>0 && !items.isEmpty()){
            int next = rand.nextInt(items.size());
            inventory.add(items.remove(next));
            numberOfItems--;
        }
        return true;
    }
}