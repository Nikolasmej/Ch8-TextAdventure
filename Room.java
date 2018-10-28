import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Corp" application. 
 * "World of Corp" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @Nicolas M
 * @author  Michael Kölling and David J. Barnes
 * @version 1
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;         // stores exits of this room.
    private Item itemroom;                      //stores item in room.   
    private Item item;                          //stores item.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, String
                 itemDescription, int itemWeight) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        itemroom = new Item (itemDescription, itemWeight);  //create item for room.
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the room.
     *     Exits and items exist in the room
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        item= this.itemroom;
        return "You are " + description + ".\n" +
                getExitString() + ".\n \n" +
                item.getItemDescription();
        
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

