
package ah.auction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;


//need to clean this up a bit.
public class AuctionHouse {
    private String name;
    private String bankID; //keep for now
    private LinkedList<Item> inventory;
    private int defaultItemNum = 5;//make this a local variable
    private int numberOfItems = 5; //for sale, pick from temp list //keep
    private int maxMinBid = 1000; //this is the maximum that the minimum bid can be
    private Random rand = new Random();
    private boolean quit = false; //may not need

    private LinkedList<Bid> activeAuctions;  //may not need
    private LinkedList<Item> pendingAuctions; //items people want, but cannot //remove
    //bid for yet
    //probably need a way to keep track of bidders....
    private LinkedList bidders; //may not need, espically since i keep track in item the person who is doing it

    /**
     * creates an Auction house object
     * @param in requires a scanner for input.
     */
    public AuctionHouse(Scanner in){
        //initializing stuff
        inventory = new LinkedList<Item>();
        activeAuctions = new LinkedList<Bid>();

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

        //bank related things set after creation

        System.out.println("Auction House is now operational");
    }

    public static void main(String[] args){
        //System.out.println("In auction house.");
        System.out.println("Welcome.");
        Scanner commandLine = new Scanner(System.in);
        AuctionHouse ah = new AuctionHouse(commandLine);
        System.out.println(ah.getInventoryList());
        //ah.startAuction(new Item("excalibur"),400);
        System.out.println(ah.activeAuctions);

//        System.out.println("For commands, enter help");
//        while(!aHouse.quit){
//            command = commandLine.nextLine();
//            switch(command){
//                case("quit"):
//                    aHouse.quit = true;
//                    break;
//                case("help"):
//                    aHouse.printHelp();
//                    break;
//                case("print items"):
//                    System.out.println("Printing item list:");
//                    aHouse.printItemList();
//                    break;
//                case("bank"):
//                    System.out.println("Set up bank account here");
//                    //need to create the controller here, and the server
//                    //in order: server, bank, controller
//                    break;
//                case("server"):
//                    System.out.println("create server here");
//                    break;
//                case("Start auction"):
//                    System.out.println("start an acution for an item");
//                    break;
//                case("view interest"):
//                    System.out.println("view flagged items");
//
//                    break;
//                case("view auctions"):
//                    System.out.println("view active auctions");
//                    aHouse.printActiveAuctions();
//                    break;
//                case ("t"):
//                    System.out.println("create bid");
//                    Item item = new Item("hell");
//                    Bid bid = new Bid(item, 30,"bob");
//                    System.out.println(bid);
//                    break;
//                case("add item"):
//                    aHouse.addCustomItem();
//                    break;

//
//            }

            //probs should check notification here


//        }

        //aHouse.getServerInfoFromUser();



        commandLine.close();
    }

    /**
     * Gets the list of items the auction is selling, all of them
     * @return a string representation of that
     */
    public String getInventoryList(){ /**need to fix this**/
        String ret = name+"\n";
        for(Item i: inventory){
            ret = ret+i.toString();
            ret = ret+"\n";
        }
        return ret;
    }

    public void startAuction(Item item, int minMoney){ //def need to fix/remove this
        Item mcGuffin = findItemInInventory(item);
        if(mcGuffin==null){
            return;
        }
        Bid minBid = new Bid(mcGuffin,minMoney, null);
        activeAuctions.add(minBid);
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
        //assuming that thier account has already been checked, just update the item
        //return a value if success, then let the thing that called start the thread
        Item find = findItemInInventory(item);
        if(find == null){
            System.out.println(item.getItemName()+" is not in our inventory");
            return "NONE"; //item is out of stock
        }else if(find.getBidder() == null){
            if(tryMoney>find.getCurrentBid()){
                System.out.println("item is now being bid on");
                return "START"; //item is now being bid on
            }
            else{ //not an overtaking bid
                return "REJECT";
            }
        }else{
            if(tryMoney>find.getCurrentBid()){
                String theOutBid = find.getBidder();
                find.setCurrentBid(tryMoney,name);
                return theOutBid; //return the outbid for notification
            }else{
                return "REJECT"; //reject if the bid was not enough to overtake
            }
        }



//    probs going to remove all of this
//        if(activeAuctions.isEmpty()){
//            System.out.println("There are no active auctions");
//            return;
//        }else if(find == null){
//            System.out.println("That item is not in our inventory");
//        }else if(!find.equals(activeAuctions.peekFirst())){
//            System.out.println("We are not accepting bids for that item " +
//                    "at this time");
//        }else{
//            Bid attempt = new Bid(find, tryMoney, name);
//            Bid win = getLargerBid(attempt,activeAuctions.peekFirst());
//            System.out.println("Winner of the battle: "+win);
//        }

    }

    public void setBankID(){
        //just set this, later, after bank account gets created, if needed...probs not
    }

    /*
     * looks for the specified item in the ah inventory
     * @param item the thing you are looking for
     * @return item if found, null if not
     */
    private Item findItemInInventory(Item item){ //this is fine...if id compare is better, fix in item code
        for(Item i: inventory){
            if(item.equals(i)){
                return i;
            }
        }
        return null;
    }

    private void printActiveAuctions(){ //may not need
        System.out.println("Active Auctions");
        for(Bid bid: activeAuctions){
            System.out.println(bid);
        }
    }

    /*
     * bid a and bid b are assumed be of the same item
     * @param a one bid
     * @param b another bid
     */
    private Bid getLargerBid(Bid a, Bid b) { //good to go
        if (a.getBidAmount() == b.getBidAmount()) {
            return null;
        }
        else if(a.getBidAmount() > b.getBidAmount()){
            return a;
        }else{
            return b;
        }
    }

    private void printHelp(){ //acutally,might not need this at all, at least, not here
        System.out.println("How to use your auction house:");
        System.out.println("1. set up a bank account");
        System.out.println("use 'bank' command to do this.");
        System.out.println("2. sell your items.");
        System.out.println("wait for clients to do this");
        System.out.println("3. clean exit, type 'quit'.");
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
            numberOfItems = getItems;
        }catch (NumberFormatException e){
            if(!command.equals("d")) {
                System.out.println("That is not a number");
            }
        }
        if(defaultItemNum == numberOfItems){
            System.out.println("using default");
        }
        //commandLine.close();
    }


    /* @param fileName -file to be read in, along with relative path
     * will randomly generate a price, so people with large amounts of inventory
     * don't have to do all of it by hand...also for fun.
     * @return true if successful, false if file isn't found
     */
    private boolean readItemList(String fileName){
        LinkedList<Item> items = new LinkedList<>();
        int idnum =1;
        try{
            Scanner readin = new Scanner(new FileReader(fileName));
            //Note adds ALL things in text file to auction house  itemlist
            while(readin.hasNextLine()){
                String input = readin.nextLine();
                int price = rand.nextInt(maxMinBid);
                if(price == 0){
                    price++; //add one just so the price isn't zero, ever
                }
                Item item = new Item(idnum,input,price); //set max bid at top.
                items.add(item);
                idnum++;
            }
            readin.close();
        }catch (FileNotFoundException ex){
            System.out.println("file not found");
            return false;
        }
        //need to randomly pick a certain number of items
        //I think I will keep the very random id numbers...i feel like it fits
        while(numberOfItems>0 && !items.isEmpty()){
            int next = rand.nextInt(items.size());
            inventory.add(items.remove(next));
            numberOfItems--;
        }

        return true;
    }

    private void printItemList(){
        System.out.println("Items Available: ");
        if(!inventory.isEmpty()) {
            for (Item i : inventory) {
                System.out.println(i);
            }
        }else{
            System.out.println("nothing");
        }
    }

    /*
    if you close the input stream in one place, it closes everywhere
    for now, im using a static scanner
    if it becomes an issue, then I can go back to makeing new ones
    but I can't close any until the input stream is finished in
    everything(therefore, at the end of main
    I've kept hte read in in read Item list, because it isn;t from keyboard
    sout -systemoutprintln
     */
}