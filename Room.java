import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes, modified by Alexandru Matei, K20054925
 * @version 2016.02.29 modified 29/11/2020
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashSet<Item> items;
    private HashSet<Character> characterList;
    private RoomManager manager;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, RoomManager manager) 
    {
        this.description = description;
        this.manager = manager;
        items = new HashSet<>();
        exits = new HashMap<>();
        characterList = new HashSet<>();
    }

    /**
     * Adds an item to the room.
     * @param item object to be added
     */
    public void addItem(Item item){
        items.add(item);
    }
    
    
    public Character getCharacterByName(String name){
        for (Character character: characterList){
            if (name.toLowerCase().equals(character.getName().toLowerCase()))
                return character;
        }
        return null;
    }
    
    /**
     * Adds a specified character to the room.
     * @param The character you are trying to add.
     */
    public void addCharacter(Character character){
    characterList.add(character);
    }
    
    /**
     * Removes a specified character from the room.
     * @param The character you want to remove.
     */
    public void removeCharacter(Character character){
        characterList.remove(character);
    }
    
    /**
     * Get a string with the characters that are in the room.
     * @return String with characters in the room.
     */
    public String getCharacterList(){
        String characterListString="";
        if (characterList.size()>0){
            for (Character character: characterList){
                characterListString+= character.getName()+" ";
            }
        }
        return characterListString;
    
    }
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, String neighbor) 
    {
        exits.put(direction, manager.getRoomByName(neighbor));
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
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
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
    
    public String getItemListString(){
        String returnString = "Items: ";
        for (Item item: items){
            returnString += item.getName() + " ";
        }
        return returnString;
    }
    
    public Item getItemByName(String name){
        for (Item item: items){
        if (item.getName().equals(name))return item;
        }
        return null;
    }
    
    public void removeItem(Item item){
        items.remove(item);
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
    
    /**
     * Picks a random exit, returns the Room associated with it.
     * @return Room object representing one of the neighboring rooms.
     */
    public Room getRandomExit(){
        Random r = new Random();
        int countdown = r.nextInt(exits.size());
        
        Set keys = exits.keySet();
        Iterator iterator = keys.iterator();
        while (countdown>0){
            iterator.next();
            countdown--;
        }
        Room randomNeighbor = exits.get(iterator.next());
        return randomNeighbor;
        
    }
}

