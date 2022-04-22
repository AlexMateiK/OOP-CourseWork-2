
/**
 * Class 'Item' stores, processes and offers information about individual items.
 *
 * @author Alexandru Matei, K20054925
 * @version 1.0 29/11/2020
 */
public class Item
{
    
    private String name;
    private int weight; // weight in kilograms
    private boolean collectable; // true if item can be collected, false otherwise
    private String hint;
    
    /**
     * Initializes an Item type object according to the given attributes.
     * @param name Name of the item.
     * @param weight Weight of the item.
     * @param collectable True if the item can be picked up, false otherwise.
     */
    public Item(String name, int weight, boolean collectable)
    {
        this.name = name;
        this.weight = weight;
        this.collectable = collectable;
        hint = null;
    }
    
    public Item(String name, int weight, boolean collectable, String hint)
    {
        this.name = name;
        this.weight = weight;
        this.collectable = collectable;
        this.hint = hint;
    }

    /**
     * Returns name of object
     * @return String containing item's name.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns hint of object. 
     * After calling this method, the hint will disappear, as it is
     * is meant to appear online one time in the game.
     * @return String containing object hint
     */
    public String getHint(){
        String hintHolder;
        hintHolder = hint;
        hint = null;
        return hintHolder;
    }
    
    /**
     * Returns weight of item.
     * @return Weight of item.
     */
    public int getWeight(){
        return weight;
    }
    
    /**
     * Returns true if the item represented by the object on which the method is called can be collected.
     */
    public boolean canBeCollected(){
        return collectable;
    }

}
