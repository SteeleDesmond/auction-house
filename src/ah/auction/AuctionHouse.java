
package ah.auction;

import ah.shared.NotificationServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;



public class AuctionHouse {
    private String name;
    private String bankID; //?
    private LinkedList<Item> itemList;
    private int defaultItemNum = 5;
    private int numberOfItems = 5; //for sale, pick from temp list
    private Random rand = new Random();
    private boolean quit = false;

    private LinkedList<AuctionHouseController.Bid> activeAuctions;  //should only be one
    private LinkedList<Item> pendingAuctions; //items people want, but cannot
    //bid for yet
    //probably need a way to keep track of bidders....
    private LinkedList bidders;

    public AuctionHouse(String name){
        this.name = name;
        itemList = new LinkedList<Item>();
    }

    public AuctionHouse(String name, int numberOfItems, int bankPortNum, int portNum){

        this.name = name;
        this.numberOfItems = numberOfItems;
    }

    public static void main(String[] args){
        //System.out.println("In auction house.");

        if (args.length > 0){

             String name = args[0];
             int numItems = Integer.parseInt(args[1]);
             int bankPortNum = Integer.parseInt(args[2]);
             int portNum = Integer.parseInt(args[3]);

             AuctionHouse auctionHouse = new AuctionHouse(name,numItems,bankPortNum,portNum);

            NotificationServer server = new NotificationServer();

        } else {

            System.out.println("Welcome.");
            Scanner commandLine = new Scanner(System.in);

            System.out.println("What do you want to name your Auction House?");
            String command = commandLine.nextLine();
            AuctionHouse aHouse = new AuctionHouse(command);
            System.out.println("Welcome " + aHouse.name + " Auction House");

            System.out.println("To Start, how many items do you want to sell");
            aHouse.readInNumber(commandLine);
            System.out.println("Amount of items you wish to sell: "
                    + aHouse.numberOfItems);
            System.out.println("Importing items...");
            aHouse.readItemList("resrcs/itemList.txt");
            System.out.println("Complete.");
            System.out.println("Auction House operational");
            System.out.println("For commands, enter help");
            while (!aHouse.quit) {
                command = commandLine.nextLine();
                switch (command) {
                    case ("quit"):
                        aHouse.quit = true;
                        break;
                    case ("help"):
                        aHouse.printHelp();
                        break;
                    case ("print items"):
                        System.out.println("Printing item list:");
                        aHouse.printItemList();
                        break;
                    case ("bank"):
                        System.out.println("Set up bank account here");
                        //need to create the controller here, and the server
                        //in order: server, bank, controller
                        break;
                    case ("server"):
                        System.out.println("create server here");
                        break;
                    case ("Start auction"):
                        System.out.println("start an acution for an item");
                        break;
                    case ("view interest"):
                        System.out.println("view flagged items");

                        break;
                    case ("view auctions"):
                        System.out.println("view active auctions");
                        aHouse.printActiveAuctions();
                        break;
                    case ("t"):

                        break;
//                case("add item"):
//                    aHouse.addCustomItem();
//                    break;


                }

                //probs should check notification here


            }

            //aHouse.getServerInfoFromUser();
             commandLine.close();
        }



    }

    public void makeBid(){

    }

    public String getName() {
        return name;
    }

    public LinkedList<Item> getItemList() {
        return itemList;
    }

    private void printActiveAuctions(){
        System.out.println("Active Auctions");
        for(AuctionHouseController.Bid bid: activeAuctions){
            System.out.println(bid);
        }
    }

    /*
     * bid a and bid b must be of the same item
     * @param a one bid
     * @param b another bid
     */
    private AuctionHouseController.Bid getLargerBid(AuctionHouseController.Bid a, AuctionHouseController.Bid b) {
        if (a.getBidAmount() == b.getBidAmount()) {
            return null;
        }
        else if(a.getBidAmount() > b.getBidAmount()){
            return a;
        }else{
            return b;
        }
    }

    private void printHelp(){
        System.out.println("How to use your auction house:");
        System.out.println("1. set up a bank account");
        System.out.println("use 'bank' command to do this.");
        System.out.println("2. sell your items.");
        System.out.println("wait for clients to do this");
        System.out.println("3. clean exit, type 'quit'.");
    }

    /*
     * reads in the number of items an auction house wishes to sell
     *
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