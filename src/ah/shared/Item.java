package ah.shared;

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
