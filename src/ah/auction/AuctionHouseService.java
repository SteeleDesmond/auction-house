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

    public String makeBid(String name, String item, int money)throws IOException{
        if(bank.blockFunds(bank.getAHKey(),name,money)){
            Item mcguffin = new Item(item);
            String result = ah.makeBid(mcguffin,money,name);
            switch(result){
                case ("NONE"):
                case("REJECT"):
                    return ("REJECT");
                case("START"):
                    break;
                default:
                    //other bidder
            }
        }

        return "Reject";
    }
    public boolean attemptToBid(String bidderKey, String itemName, int amount) {
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




































