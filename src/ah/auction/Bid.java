package ah.auction;

/**
 * Bid class is meant to keep track of the count down for the auctions
 */
public class Bid extends Thread implements Runnable{
    private String theBid;
    private Bidder bidder;
    private Item item;
    private AuctionHouseService a;

    /**
     * creates a bid object
     * @param item item being bid on
     * @param parent reference to parent for notification purposes
     * @param bidder referance to the bidder who makes the bid
     */
    public Bid(Item item, AuctionHouseService parent, Bidder bidder){
        System.out.println("bid created");
        this.bidder=bidder;
        theBid = item.toString();
        this.item =item;
        a = parent;
    }

    /**
     * Run is meant to be the count down
     * thread sleeps for thirty seconds,
     * then it checks to see if the will is valid
     * then, if the win is there, it notifies the bidder
     */
    @Override
    public void run(){
        System.out.println("Bid run");
        try{
            sleep(30000);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Bid has awoken");
       if(a.checkWin(theBid, item)){
           bidder.sendWinNotification(item);
           //tell that they won
       }
    }
}
