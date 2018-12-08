package ah.auction;

import ah.bank.BankService;
import ah.shared.enums.AuctionHouseMessages;
import ah.shared.enums.BankMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public class Bidder implements Runnable {
    private String name;
    boolean loggedIn = true;
    private PrintWriter out;
    private BufferedReader in;
    private AuctionHouseService house;
    private String biddingKey;
    private ArrayList<String> itemsWon = new ArrayList<>();

    public Bidder(String callMe, PrintWriter out, BufferedReader in, AuctionHouseService house) throws IOException {
        this.name = callMe;
        this.in=in;
        this.out = out;
        this.house=house;
        biddingKey = in.readLine();
        System.out.println("New bidder added! Bidding key = " + biddingKey);
    }

    @Override
    public void run() {
        String input;
        while(loggedIn){
            //System.out.println("Loop");
            try{
                input = in.readLine();
                if(input!= null){
                    System.out.println("Request received from account " + name+ ": " + input);
                    AuctionHouseMessages msg = AuctionHouseMessages.valueOf(input);
                    switch(msg){
                        case GETAUCTIONS:
                            sendSuccess();
                            LinkedList<String> inventory = house.getInventory();
                            out.println(inventory.size());
                            for(String item: inventory){
                                out.println(item);
                            }
                            break;
                        case BID: {
                            String itemName = in.readLine(); // Get the item to bid on
                            int amount = Integer.valueOf(in.readLine());
                            if(house.attemptToBid(biddingKey, itemName, amount)) {
                                out.println(AuctionHouseMessages.SUCCESS);
                            }
                            else {
                                out.println(AuctionHouseMessages.FAILURE);
                            }
                            break;
                        }
                        case WON: {
                            out.println(AuctionHouseMessages.SUCCESS);
                            out.println(itemsWon.size());
                            for(String s : itemsWon) {
                                out.println(s);
                            }
                            break;
                        }
//                        case BID:
//                            input = in.readLine();
//                            int money = Integer.parseInt(in.readLine());
//                            house.makeBid(name,input,money);
//                            break;
                    }


                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void sendSuccess() {
        out.println(BankMessages.SUCCESS);
    }

    public ArrayList<String> getItemsWon() {
        return itemsWon;
    }

    public void sendWinNotification(Item item){
        out.println(AuctionHouseMessages.WON);
        addWonItem(item.getItemName());
    }
    public void sendPassNotification(){
        out.println(AuctionHouseMessages.PASS);
    }

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

    public boolean matches(String name){
        return this.name.equals(name);
    }
}
