package ah.auction;

public class Item {
    private final String itemName;

    /**
     * Creates an item
     * @param str takes the name of the item to be created
     */
    public Item(String str){
        itemName = str;
    }

    /**
     * getter for the item's name
     * @return the item's name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @Override toString
     * ensures that items are represented properly
     * @return the string of the item name
     */
    public String toString(){
        return getItemName();
    }
}
