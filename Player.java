import java.util.HashSet;
/**
 * Class meant to store information about the player character.
 *
 * @author Alexandru Matei
 * @version 29/11/2020
 */
public class Player
{
    
    private HashSet<Item> inventory;
    private int weightOfItems;
    private static final int weightLimit = 10;
    private int wallet;
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        inventory = new HashSet<>();
        weightOfItems = 0;
        wallet = 0;
    }

    /**
     * Adds an item to the player's inventory.
     * @param item - Item object to be added.
     * @return Returns true if item was succesfully added, false if item was too heavy.
     */
    public boolean addItemToInventory(Item item){
        if(item.getWeight()<=weightLimit-weightOfItems)
        {
            inventory.add(item);
            weightOfItems+=item.getWeight();
            return true;
        }
        else return false;    
    }
    
    /**
     * Adds specified amount to the total money the player has.
     * @param amount How many pounds you want to add.
     */
    public void addCash(int amount){
        wallet+=amount;
    }
    
    /**
     * Returns how much money the player has.
     * @return Amount of money as an int.
     */
    public int getCash(){
        return wallet;
    }
    
    /**
     * Removes the item with the name you provide from the player's inventory.
     * @return The item object corresponding the the name you gave, if such an item exists, null otherwise.
     */
    public Item removeItemByName(String itemName){
        for (Item item: inventory){
            if (item.getName().equals(itemName)){
                inventory.remove(item);
                weightOfItems-=item.getWeight();
                return item;
            }
        }
        return null;
    }
    
    /**
     * Returns a string which is a list of all the items the player has at the moment.
     * @return A list of the items in the player's inventory.
     */
    public String getStringWithItems(){
        if(inventory.size()==0)return "You have no items!";
        else {
            String itemString = "Inventory: ";
            for (Item item: inventory){
                itemString+= item.getName() + " ";
            }
            return itemString;
        }
    }
    
    /**
     * Checks if the player's inventory contains an item with the name you provide.
     * @param itemName Name of item you want to check.
     */
    public boolean checkItemByName(String itemName){
        for (Item item: inventory){
            if (item.getName().equals(itemName)){
                return true;
            }
        }
        return false;
    }
}
