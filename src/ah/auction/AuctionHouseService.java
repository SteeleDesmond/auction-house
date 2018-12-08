package ah.auction;

import ah.shared.BankProxy;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AuctionHouseService is the meat of the program
 * it runs the auction house it makes
 */
public class AuctionHouseService implements Runnable {
    private ExecutorService pool = Executors.newCachedThreadPool();
    private LinkedList<Bidder> bidders = new LinkedList<>();
    private LinkedList<Bid> bids= new LinkedList<>();
    private AuctionHouse ah;
    private BankProxy bank;
    private Scanner commandLine;

    /**
     * Constructor for the service, sets everything up
     * @param bank takes a bank proxy
     */
    public AuctionHouseService(BankProxy bank) {
        this.bank = bank;
        commandLine = new Scanner(System.in);
        ah = new AuctionHouse(commandLine);

    }

    /**
     * run, it exists for when we want to add command line gui after
     * the creation of the whole auction house
     */
    @Override
    public void run() {

    }

    /**
     * Adds new clients for the auction house
     * will start a new bidder thread for communication,
     * then go back to listening for more connections
     * @param s a socket
     * @throws IOException if it occurs
     */
    public void addNewClient(Socket s) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String name = "temp";
        Bidder newbidder =  new Bidder(name,out,in,this);
        bidders.add(newbidder);
        pool.execute(newbidder);
    }

    /**
     * getter for the inventory
     * @return the inventory in linked list form
     */
    public LinkedList<String> getInventory(){
        return ah.getInventoryList();
    }

    /**
     * Tell the bank to put a hold on a given amount of funds.
     * @param agentBiddingKey The agent's bidding key given to the AH
     * @param amount The amount to hold
     */
    public boolean blockFunds(String agentBiddingKey, int amount) throws IOException {
        return bank.blockFunds(bank.getAHKey(), agentBiddingKey, amount);
    }


    /**
     * Tell the bank to remove a hold on a given amount of funds of a given agent account
     * @param agentBiddingKey The agent's bidding key given to the AH
     * @param amount The amount to hold
     */
    public boolean unblockFunds(String agentBiddingKey, int amount)  throws IOException {
        return bank.unblockFunds(bank.getAHKey(), agentBiddingKey, amount);
    }

    /*
     * finds the bidder based on the key you have
     * @param bidderkey that you have
     * @return the bidder, if one is found
     */
    private Bidder findBidder(String bidderkey) {
        for(Bidder b: bidders) {
            if (b.matches(bidderkey)) {
                return b;
            }
        }
        return null;
    }

    /*
     * creates for a bid thread
     * @param item you want to bid on
     * @param bidder that is bidding
     */
    private void createBidThread(Item item, Bidder bidder){
        Bid bid = new Bid(item,this,bidder);
        bids.add(bid);
        bid.start();
    }

    /**
     * attempts to make a bid,
     * it finds the bidder, checks their account, and, if they have the money
     * will then send their bid attempt along
     * they will then receive notification based on success
     * @param bidderKey the string rep of the bidder you are
     * @param itemName name of the item you want
     * @param amount of money you want to try spending on it
     * @return false if bid fails, true if it does not
     * @throws IOException
     */
    public boolean attemptToBid(String bidderKey, String itemName, int amount)
    throws IOException{
        System.out.println("Attempting to bid");
        if(bank.blockFunds(bank.getAHKey(),bidderKey,amount)){
            Item mcguffin = new Item(itemName);
            String result = ah.makeBid(mcguffin,amount,bidderKey);
            Bidder newBidder = findBidder(bidderKey);
            if(newBidder==null){
                return false;
            }
            switch(result){
                case ("NONE"):
                case("REJECT"):
                    newBidder.sendRejectNotification();
                    break;
                case("START"):
                    createBidThread(mcguffin,newBidder);
                    break;
                default:
                    Bidder oldBidder = findBidder(result);
                    if(oldBidder==null){
                        return false;
                    }
                    oldBidder.sendPassNotification();
                    createBidThread(mcguffin,newBidder);
                    //other bidder
            }

        }
        return true;
    }

    /**
     * checks to see if you have a winning bid
     * @param winCard string representation of the bid
     * @param item that you had bid on
     * @return true if the current bid information matches, false if it does not
     */
    public boolean checkWin(String winCard, Item item){
        LinkedList<Item> inventory = ah.getInventory();
        if(inventory.contains(item)){
            Item compare = inventory.remove(inventory.indexOf(item));
            if(compare.getBidder().equals(item.getBidder())){
                if(compare.getCurrentBid()==item.getCurrentBid()){
                    return true;
                }
            }
            inventory.add(compare);
        }
        return false;
    }
}