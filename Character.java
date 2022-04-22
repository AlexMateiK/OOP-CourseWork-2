import java.util.HashSet;
/**
 * The character class models a character in the game.
 * It contains information about it and methods to implement movement.
 *
 * @author Aleandru Matei
 * @version 30/11/2020
 */
public class Character
{
    
    private String name;
    private Room currentRoom;
    private String defaultLine;
    private HashSet<Item> inventory;
    
    /**
     * Constructor for objects of class Character
     */
    public Character(String name, Room startingRoom, String defaultLine)
    {
        this.name=name;
        this.currentRoom=startingRoom;
        this.defaultLine = defaultLine;
        inventory = new HashSet<>();
    }

    /**
     * Moves the character to a specified room.
     * @param room The room you want to move the character to.
     */
    public void changeRoom(Room room)
    {
        currentRoom.removeCharacter(this);
        currentRoom = room;
        currentRoom.addCharacter(this);
    }
    
    /**
     * Returns the default dialogue line of the character.
     * @return String with default line.
     */
    public String getDefaultLine(){
        return defaultLine;
    }
    
    /**
     * Adds a specified item to the character's inventory.
     * @param item Item you want to add.
     */
    public void addItemToInventory(Item item){
        inventory.add(item);  
    }
    
    /**
     * Removes a specified item from the character's inventory.
     * @param item Item you want to remove.
     */
    private void removeItemFromInventory(Item item){
        inventory.remove(item);  
    }
    
    /**
     * Checks if a character has an item in their inventory with the name equal to the String you provide.
     * @param itemName Name of the item you want to check.
     * @return true if the character has an item with the specified name, false otherwise
     */
    public boolean checkItemByName(String itemName){
        for (Item item: inventory){
            if (item.getName().equals(itemName))return true;
        }
        return false;
    }
    
    /**
     * Returns the item object with the name you specify, so you can add it someplace else.
     * @param itemName Name of the item you're looking for.
     * @return An item object with the specified name, or null if that doesn't exist.
     */
    public Item takeItemByName(String itemName){
        for (Item item: inventory){
            if (item.getName().equals(itemName)){
                removeItemFromInventory(item);
                return item;
            }
        }
        return null;
    }
    
    /**
     * Moves the character to a random neighboring room, if the player is not in the same room. 
     */
    public void moveToRandomNeighbor(){
        Room nextRoom = currentRoom.getRandomExit();
        changeRoom(nextRoom);
    }
    
    /**
     * @return Returns object reference of the room the character is in.
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }
    
    /**
     * @return Name of the character.
     */
    public String getName(){
        return name;
    }
}
