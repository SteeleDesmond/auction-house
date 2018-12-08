package ah.auction;
//I think I might make this into a runnable, and it will check to see if time has run out.
public class Bid extends Thread implements Runnable{
    private String theBid;
    private Item item;
    private AuctionHouseService a;

    public Bid(Item item, AuctionHouseService parent){
        theBid = item.toString();
        this.item =item;
        a = parent;
    }

    //thread sleep hahahahaha
//    @Override
    public void run(){
        System.out.println("Bid created");
        try{
            sleep(30000);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Bid has awoken");
       if(a.checkWin(theBid, item)){
           //tell that they won
       }
    }

//    /**
//     * Creates a bid
//     * @param item the item the bid is on
//     * @param money the amount of money bid on the item
//     * @param name name of the bidder
//     */
//    public Bid(String){
//        this.item=item;
//        this.bidAmount=money;
//        this.bidder=name;
//    }
//
//    /**
//     * getter for the bid amount
//     * @return the bid amount
//     */
//    public int getBidAmount() {
//        return bidAmount;
//    }
//
//    /**
//     * getter for the item
//     * @return the item in question
//     */
//    public Item getItem() {
//        return item;
//    }
//
//    /**
//     * getter for the bidder's name
//     * @return bidder's name as a string
//     */
//    public String getBidder() {
//        return bidder;
//    }
//
//    /**
//     * @Override toString
//     * @return string representation of bid
//     */
//    public String toString(){
//        return ("Bidder: "+bidder+", $"+bidAmount+" for "+item);
//    }
}
