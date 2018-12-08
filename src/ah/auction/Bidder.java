package ah.auction;

import ah.bank.BankService;
import ah.shared.enums.AuctionHouseMessages;
import ah.shared.enums.BankMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Bidder functions as a communication thread
 * on the auction house side between the agent and auction house
 */
public class Bidder implements Runnable {
    private String name;
    boolean loggedIn = true;
    private PrintWriter out;
    private BufferedReader in;
    private AuctionHouseService house;
    private String biddingKey;
    private ArrayList<String> itemsWon = new ArrayList<>();

    /**
     * constructor for the Bidder
     * @param callMe this supposed to be the agents name. it is not
     * @param out this is the output stream
     * @param in this is the input stream
     * @param house this is a refererance to the parent class,
     *              that class deals with the "real" auction house
     * @throws IOException if it occurs
     */
    public Bidder(String callMe,
                  PrintWriter out, BufferedReader in, AuctionHouseService house)
            throws IOException {
        this.name = callMe;
        this.in=in;
        this.out = out;
        this.house=house;
        biddingKey = in.readLine();
        System.out.println("New bidder added! Bidding key = " + biddingKey);
    }

    /**
     * This is the run method
     * while the bidder is 'logged on'
     * it will be listened to
     */
    @Override
    public void run() {
        String input;
        while(loggedIn){
            //System.out.println("Loop");
            try {
                input = in.readLine();
                if (input != null) {
                    System.out.println("Request received from account " + name + ": " + input);
                    AuctionHouseMessages msg = AuctionHouseMessages.valueOf(input);
                    switch (msg) {
                        case GETAUCTIONS:
                            sendSuccess();
                            LinkedList<String> inventory = house.getInventory();
                            out.println(inventory.size());
                            for (String item : inventory) {
                                out.println(item);
                            }
                            break;
                        case BID: {
                            String itemName = in.readLine(); // Get the item to bid on
                            int amount = Integer.valueOf(in.readLine());
                            if (house.attemptToBid(biddingKey, itemName, amount)) {
                                out.println(AuctionHouseMessages.SUCCESS);
                            } else {
                                out.println(AuctionHouseMessages.FAILURE);
                            }
                            break;
                        }
                        case WON: {
                            out.println(AuctionHouseMessages.SUCCESS);
                            out.println(itemsWon.size());
                            for (String s : itemsWon) {
                                out.println(s);
                            }
                            break;
                        }

                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /*
     * method is meant to be a way to notify bidder of success
     */
    private void sendSuccess() {
        out.println(BankMessages.SUCCESS);
    }

    /**
     * getter for the items a bidder has won
     * @return a list of items
     */
    public ArrayList<String> getItemsWon() {
        return itemsWon;
    }

    /**
     * Notify the bidder that they have won the item
     * add it to their winnings
     * @param item in question
     */
    public void sendWinNotification(Item item){
        out.println(AuctionHouseMessages.WON);
        addWonItem(item.getItemName());
    }

    /**
     * Notify the bidder that their bid has been passed
     * need to find a way to unblock their fundss...
     */
    public void sendPassNotification(){
        out.println(AuctionHouseMessages.PASS);
    }

    /**
     * Notify a bidder that their bid has been rejected
     */
    public void sendRejectNotification(){
        out.println(AuctionHouseMessages.REJECT);
    }
    /**
     * Used by the parent class (AuctionHouseService) to add items to the bidder's list of won items
     * @param item The item name of the item won
     */
    public void addWonItem(String item) {
        itemsWon.add(item);
    }

    /**
     * A method to check to see if you are the bidder being searched for
     * @param name string of the key that wants to be matched
     * @return true if this bidders 'name' matches, false if it does not
     */
    public boolean matches(String name){
        return this.name.equals(name);
    }
}
