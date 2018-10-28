
/**
 * Class Item - an item in an adventure game.
 *
 * This class is part of the "Corp Adventure" application. 
 * "Corp Adventure" is a very simple, text based adventure game.  
 *
 * "Item" represents one item in the scenery of the game.  It is 
 * adding items to each room. These items contain an unique description  
 * and weight. 
 * 
 * @author  Nicolas M.
 * @version 1.
 */
public class Item 
{
    private String itemDescription;
    private int itemWeight;
        
    /**
     * Cronstuctor for objects of "Item".
     * Book and online references.
     */
    public Item(String itemDescription, int itemWeight) 
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }
    
    /**
     * The item description in a room.
     * @return Item description with weight.
     * (the one that was defined in the constructor).
     */
    public String getItemDescription()
    {
        String itemString = "This room contains: ";
        itemString = itemString + this.itemDescription +
                        "\nItem Weight: " + this.itemWeight;
        return itemString;
    }
    
}
    