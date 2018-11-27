package ah.shared;

public class Bid {
    private int bidAmount;
    private Item item;

    /**
     * Creates a bid
     * @param item the item the bid is on
     * @param money the amount of money bid on the item
     */
    public Bid(Item item, int money){
        this.item=item;
        this.bidAmount=money;
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
}
