package ah.auction;

public class Item {
    private final int itemID;
    private final String itemName;
    private final int minimumBid;
    private int currentBid;
    private String bidder; //saves the name of the bidder, if any

    /**
     * creates an item, temporarily, to test for equality
     * @param str is the name of the item you are looking for
     */
    public Item( String str){
        itemID = -1;
        itemName = str;
        minimumBid = 0;
        currentBid = minimumBid;
        bidder = null; //if null, there is no bidder
    }

    /**
     * Creates an item
     * @param id takes the id that will be used for the item in question
     * @param str takes the name of the item to be created
     * @param minBid takes the minimum bid for an item
     */
    public Item(int id,String str, int minBid){
        itemID = id;
        itemName = str;
        minimumBid = minBid;
        currentBid = minimumBid-1;
        bidder = null; //if null, there is no bidder
    }

    /**
     * getter for the item's id
     * @return the itemID
     */
    public int getItemID(){
        return itemID;
    }

    /**
     * getter for the item's name
     * @return the item's name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * getter for the minimum bid for the item
     * @return the minimum bid
     */
    public int getMinimumBid() {
        return minimumBid;
    }

    /**
     * getter for the current bid for the item
     * @return the current bid
     */
    public int getCurrentBid() {
        return currentBid;
    }

    /**
     * setter for the current bid for the item
     * will only go through if the attempted bid is higher
     * @param currentBid that is being set
     * @param bidder is the bidder that currently has this item.
     */
    public void setCurrentBid(int currentBid, String bidder) {
        //System.out.println("\t reached setting the current bid");
        if(currentBid>this.currentBid){
            //System.out.println("In if");
            this.currentBid = currentBid;
            this.bidder = bidder;
        }
    }

    /**
     * getter for the current bidder
     * @return bidder
     */
    public String getBidder(){
        return bidder;
    }

    /**
     * @Override toString
     * ensures that items are represented properly
     * @return the string of the item id, its name, the minimun and current bid
     * does not reveal the secret id of current bidder
     */
    public String toString(){
        return getItemID()+" "+getItemName()+" "+
                getMinimumBid()+" "+getCurrentBid();
    }

    /**
     * @Override equals
     * checks to see if two items are equal, by which they have the same name,
     * or that the object is equal to this item
     * @param o the object in question
     * @return true if it is, false if it is not
     */
    public boolean equals(Object o){
        if(o instanceof Item){
            return itemName.equals(((Item) o).getItemName());
        }
        return false;
    }
}
