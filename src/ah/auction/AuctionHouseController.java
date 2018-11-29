package ah.auction;

import ah.shared.AuctionHouseProxy;

import java.util.Scanner;

public class AuctionHouseController implements Runnable {

    private AuctionHouseProxy auctionHouse;
    private Scanner commandLine = new Scanner(System.in);
    private Boolean running = true;

    public AuctionHouseController(AuctionHouseProxy auctionHouse) {
        this.auctionHouse = auctionHouse;
    }

    @Override
    public void run() {
        while(running) {
            printCommands();
            switch(commandLine.nextLine()) {
                case ("ahMsg"): {
                    System.out.println("Type a message to send to the Auction House service");
                    auctionHouse.sendMsg(commandLine.nextLine());
                    break;
                }
            }
        }
    }

    private void printCommands() {
        System.out.println("Auction House Client Console Options:");
        System.out.println("ahMsg -- Send a message to the AH Server");
        // list auction
        // start bidding
        // etc.
    }

    public static class Bid {
        private String bidder;
        private int bidAmount;
        private Item item;

        /**
         * Creates a bid
         * @param item the item the bid is on
         * @param money the amount of money bid on the item
         * @param
         */
        public Bid(Item item, int money, String name){
            this.item=item;
            this.bidAmount=money;
            this.bidder=name;
        }

        /**
         * getter for the bid amount
         * @return the bid amount
         */
        public int getBidAmount() {
            return bidAmount;
        }

        /**
         * getter for the item
         * @return the item in question
         */
        public Item getItem() {
            return item;
        }

        /**
         * getter for the bidder's name
         * @return bidder's name as a string
         */
        public String getBidder() {
            return bidder;
        }

        /**
         * @Override toString
         * @return string representation of bid
         */
        public String toString(){
            return ("Bidder: "+bidder+", $"+bidAmount+" for "+item);
        }
    }
}
