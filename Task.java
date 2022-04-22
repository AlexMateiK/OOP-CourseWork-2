import java.util.HashSet;
/**
 * A single task to be completed as part of a mission.
 * It contains the conditiosn that need to be met and the time of the action,
 * the command/action itself that needs to be done, and consequences of different types.
 * This class is designed in a modullar way to allow for the creation of a variety of tasks 
 * from the same building blocks.
 *
 * @author Alexandru Matei
 * @version 30/11/2020
 */
public class Task
{
    private Command action;
    private HashSet<String> itemsToHave; // names of the items the Player needs to have in their inventory
    private String characterPresent; // name of character that needs to be in the same room as the player
    private String nameItemCharShouldHave;
    private String validRoomName; // room that the character needs to be in

    private HashSet<String> itemsToRemove; // names of items that should be removed from the Player's inventory should they complete the task
    private HashSet<Item> itemsToGive; //items that should be put in the Player's inventory should they complete the task
    private int cashReward; //amount of pounds to be put in the player's wallet should they complete the task
    private String endMessage;
    
    /**
     * Constructor for objects of class Task
     * @param command The command which must be written in by the player in order to do this task.
     */
    public Task(Command command)
    {
        action = command;
        itemsToHave = new HashSet<>();
        characterPresent = null;
        validRoomName = null;
        itemsToRemove = new HashSet<>();
        itemsToGive = new HashSet<>();
        cashReward=0;
        endMessage = null;
        nameItemCharShouldHave=null;
    }

    /**
     * Checks if the task's conditions are all met.
     * @param roomManager The roomManager object, needed for condition checks
     * @param player the Player object, needed for condition checks
     * @param command The last command issued by the player, checked for fit
     * @return true if all conditions are met by the current state of the game, false otherwise
     */
    public boolean checkConditionsMet(RoomManager roomManager, Player player, Command command){
       
        if (!checkCommandEquality(command))return false;
        
        if(!checkItems(player))return false;
        
        if(!checkCharacter(roomManager))return false;
        
        if(!checkValidRoom(roomManager))return false;
        
        if(!checkCharHasItem(roomManager))return false;
        return true;
    }
    
    /**
     * Applies all the effects of the completion of the task.
     * @param player Needed in case inventory changes take place
     * @param roomManager Needed in case an item must be added to the room.
     */
    public void applyConsequences(Player player, RoomManager roomManager){ // The room manager should be passed as well in order to add items to the room in case the player cannot carry them.
        for (String itemName: itemsToRemove){
            player.removeItemByName(itemName);
        }
        
        for (Item itemToGive: itemsToGive){
            if (!player.addItemToInventory(itemToGive)){
                System.out.println("You can't hold "+ itemToGive.getName()+", you drop it on the ground.");
                roomManager.getCurrentRoom().addItem(itemToGive);
            }
        }
        
        if (cashReward>0)
            player.addCash(cashReward);
            
        if (endMessage!=null)
            System.out.println(endMessage);
    }
    //  
    
    /*
     * Thoroughly checks if the command issued by the player fits the command in the task.
     */
    private boolean checkCommandEquality(Command command){
        if (!action.getCommandWord().equals(command.getCommandWord()))return false;
        // can never point to null, nothing from the tasks will be checked if the commandword isn't valid.
        
        if (action.getSecondWord()==null && command.getSecondWord()!=null)return false;
        if (action.getSecondWord()!=null && command.getSecondWord()==null)return false;
        
        if (action.getSecondWord()!=null && command.getSecondWord()!=null) // avoid null pointer exception, in case both commands are null
            if (!action.getSecondWord().equals(command.getSecondWord()))return false;

        if (action.getThirdWord()==null && command.getThirdWord()!=null)return false;
        if (action.getThirdWord()!=null && command.getThirdWord()==null)return false;
        if (action.getThirdWord()!=null && command.getThirdWord()!=null)
            if (!action.getThirdWord().equals(command.getThirdWord()))return false;
        
        return true;
    }
    
    /*
     * Checks if the character that is specified as being supposed to be in the room ( its name should be in the characterPresent field if this condition is to be used)
     * has a specified item. Both of these checks are done by name.
     * This method fits the case when we want the player to give an item to a character in order to finish a task.
     * Since that item disappears from the player's inventory when they give it to a character, 
     * we need this check to ensure that the command actually had an effect (as in, the give command wasn't just written, but the player did have that item and did give it to the character)
     */
    private boolean checkCharHasItem(RoomManager roomManager){
        if (nameItemCharShouldHave==null)return true;
        if (characterPresent==null)return true;
        Room room = roomManager.getCurrentRoom();
        Character character = room.getCharacterByName(characterPresent);
        if (character.checkItemByName(nameItemCharShouldHave))return true;
        else {
            System.out.println("You need to give the "+nameItemCharShouldHave+" to "+characterPresent);
            return false;
        }
    }
    
    /*
     * Used to check if the player has all the items that he is supposed to have as a condition.
     */
    private boolean checkItems(Player player){
        if (!itemsToHave.isEmpty())
            for (String itemName: itemsToHave){
                if(!player.checkItemByName(itemName)){
                    System.out.println("You need the "+itemName+" !");
                    return false;
                }
            }
        return true;
    }
    
    /*
     * Checks if the character specified in the condition (characterPresent = the name of the character that should be present for task completion) is in the same room as the player.
     */
    private boolean checkCharacter(RoomManager roomManager){
        if (characterPresent==null)return true;
        else {
            Room currentRoom = roomManager.getCurrentRoom();
            Character character = currentRoom.getCharacterByName(characterPresent);
            if (character!=null)return true;
            else return false;
        }
    }
    
    /*
     * Checks if player is in the room they need to be in order to complete the task.
     */
    private boolean checkValidRoom(RoomManager roomManager){
        if (validRoomName == null)return true;
        else {
            Room room = roomManager.getRoomByName(validRoomName);
            Room currentRoom = roomManager.getCurrentRoom();
            //room.getShortDescription().equals(currentRoom.getShortDescription())
            if (room == currentRoom) // I know I am testing identity, at this point, if the player is in the room they need to be, the room objects should be identical.
                return true;
            else
                {
                System.out.println("You're not in the right place for this");
                return false;
            }
        }
    }
    
    /**
     * Specify by name the item a character should have after the player issues a command.
     * Usually useful for checking if a "give nameItem nameCharacter" command was valid/had an effect
     * @param itemName Item the character specified in the "character present" condition should have.
     */
    public void addItemPresentCharacterShouldHave(String itemName){
        nameItemCharShouldHave = itemName;
    }
    
    /**
     * Specify the name of an item the player needs to have for task completion.
     * @param nameOfItem
     */
    public void addItemToHave(String nameOfItem){
        itemsToHave.add(nameOfItem);
    }
    
    /**
     * Specify the name of a character that needs to be present for task completion.
     * @param characterName Name of the character you want to be present as a condition for completing the task.
     */
    public void addCharacterToBePresent(String characterName){
        characterPresent = characterName;
    }
    
    /**
     * Specify by name the room in which the player needs to be to complete this task.
     * @param room Name of the room the player needs to be in to complete task.
     */
    public void addRoomToBeIn(String room){
        validRoomName = room;
    }
    
    /**
     * Specify the name of an item that should be removed if this task is succesfully completed.
     * @param nameOfItem Name of the item to be removed on task completion.
     */
    public void addItemToRemove(String nameOfItem){
        itemsToRemove.add(nameOfItem);
    }
    
    /**
     * Provide an Item type object the player should receive if they succesfully complete this task.
     * @param item Item to give to the player on task completion.
     */
    public void addItemToGive(Item item){
        itemsToGive.add(item);
    }
    
    /**
     * Specify how much money the player should receive for completing this task.
     * @param cashReward a number representing how many pounds the player should receive.
     */
    public void setCashReward(int cashReward){
        this.cashReward=cashReward;
    }
    
    /**
     * Set a message to be printed out on succesful completion of the task.
     * Can be used both to describe the resulting situation to the player, or to simulate NPC reaction, etc.
     * @param aString String to be printed out on task completion.
     */
    public void setEndMessage(String aString){
        endMessage = aString;
    }
}
