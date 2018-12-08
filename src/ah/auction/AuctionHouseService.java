package ah.auction;

import ah.shared.BankProxy;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuctionHouseService implements Runnable {
    private ExecutorService pool = Executors.newCachedThreadPool();
    private LinkedList<Bidder> bidders = new LinkedList<>();
    private LinkedList<Bid> bids= new LinkedList<>();
    private AuctionHouse ah;
    private BankProxy bank;
    private Scanner commandLine;

    public AuctionHouseService(BankProxy bank) {
        this.bank = bank;
        commandLine = new Scanner(System.in);
        ah = new AuctionHouse(commandLine);

    }

    @Override
    public void run() {

    }

    public void addNewClient(Socket s) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String name = "temp";
        Bidder newbidder =  new Bidder(name,out,in,this);
        bidders.add(newbidder);
        pool.execute(newbidder);
    }

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

    private Bidder findBidder(String bidderkey) {
        for(Bidder b: bidders) {
            if (b.matches(bidderkey)) {
                return b;
            }
        }
        return null;
    }
    public void createBidThread(Item item, Bidder bidder){
        Bid bid = new Bid(item,this,bidder);
        bids.add(bid);
        bid.start();
    }

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




































