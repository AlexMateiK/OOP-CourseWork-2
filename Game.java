/**
 *  This class is the main class of the "Hard work pays off" application.
 *  A simple game in which you have to do tasks to collect 30 pounds.
 *  The game is highly interactive and in order to win the player must chat with characters, help them, use items, collect items, etc.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes, modified by Alexandru Matei, k20054925
 * @version 1/12/2020
 */

public class Game 
{
    private Parser parser;
    private RoomManager roomManager;
    private Player player;
    private CharacterManager characterManager;
    private MissionManager missionManager;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player();
        MissionCommandWords missionCommandWords = new MissionCommandWords();
        // The command words specific to missions are handled in a different class. Reasons detailed in it.
        roomManager = new RoomManager();
        roomManager.createRooms();
        roomManager.addItemsToRooms();
        characterManager = new CharacterManager(roomManager);
        missionManager = new MissionManager(missionCommandWords);
        parser = new Parser();
        parser.setMissionCommandWords(missionCommandWords);
    }

    
    
    

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            characterManager.moveCharacters();
            if (! finished){
                if(checkIfWon()) 
                    printWinMessage();
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to 'Hard work pays off' !");
        System.out.println();
        System.out.println("A game meant to teach resourcefulness!");
        System.out.println("You are Alex, a 12 year old that just saw an advert for the newest watercannon toy.");
        System.out.println("It's summer, so you absolutely NEED it! But you're out of pocket money! See how you can get the funds to buy it");
        System.out.println();
        System.out.println("Hint: chat with characters, help them out, sell some lemonade..");
        System.out.println("Type 'help' if you need help. I recommend you try out a few commands to see what they do");
        System.out.println();
        System.out.println(roomManager.getCurrentRoom().getLongDescription());
    }

    /*
     * If the player has managed to collect 30 pounds, this returns true, otherwise falls. The player needs 30 pounds to win the game.
     */
    private boolean checkIfWon(){
        if (player.getCash()>=30)return true;
        else return false;
    }
    
    /*
     * Prints a helpful message to the player. This only happens if the player has met the win condition.
     */
    private void printWinMessage(){
        System.out.println("Congratulations! You've got enough cash to buy the watercannon!");
        System.out.println("Whenever you feel like finishing the game, just type 'buy watercannon'");
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        
        
        
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        boolean win = checkWinCommand(command);
        if (win==true)return true;
        
        
        String commandWord = command.getCommandWord();
        if (!commandWord.equals("give") &&!commandWord.equals("take") && command.hasThirdWord()){
            System.out.println("Could you keep it a bit more simple?");
        }
        else if (commandWord.equals("give")){
            giveItem(command);
        }
        else if (commandWord.equals("take")){
            takeItem(command);
        }
        else if (commandWord.equals("chat")){
            chat(command);
        }
        else if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("search")){
            System.out.println(roomManager.getCurrentRoom().getItemListString());
            System.out.println("There are some people here:" + roomManager.getCurrentRoom().getCharacterList());
        }
        else if (commandWord.equals("collect")){
            collectItem(command);
        }
        else if (commandWord.equals("inventory")){
            printInventory();
        }
        else if (commandWord.equals("drop")){
            dropItem(command);
        }
        else if (commandWord.equals("wallet")){
            System.out.println("You have "+player.getCash()+" pounds.");
        }
        else if (commandWord.equals("back")){
            if(roomManager.goBack()){
                System.out.println("You go back to the previous room.");
                System.out.println(roomManager.getCurrentRoom().getLongDescription());
            }
            else System.out.println("You don't seem to remember which room you were in previously.");
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        
        // instructs the mission manager to check the current command against all the tasks the player is at right now
        missionManager.tryCurrentTasks(roomManager, player, command);
        
        
        return wantToQuit;
    }

    // implementations of user commands:

    /*
     * Handles the situation when the player inputs the command to win the game.
     * It checks the win condition, and if it is fulfilled, it will return true, which will make the processCommand method return true and end the game.
     */
    private boolean checkWinCommand(Command command){
        if (command.getCommandWord()!=null && command.getSecondWord()!=null){
        if(command.getCommandWord().equals("buy") && command.getSecondWord().equals("watercannon"))
        {
            if(player.getCash()>=30)
                {
                    System.out.println("You buy the watercannon, and win the game.");
                    return true;
                }
            else {
                    System.out.println("You need more cash!");
                    return false;
                 }
        }
        else return false;
        }
        else {
            if (command.getCommandWord()!=null)
                if (command.getCommandWord().equals("buy"))
                    System.out.println("Buy what?");
            return false;}
    }
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println();
        System.out.println("Task-specific command words you might need to use are:");
        parser.showMissionCommands();
        System.out.println();
        System.out.println("Only the 'give' and 'take' commands use 3 words, in the following format: command item name");
        System.out.println("For tasks, try the simplest way to express an action!");
    }

    /*
     * Handles the chat command, protects against null pointer exceptions, prints out the defaultLine of the named character.
     */
    private void chat(Command command){
       if (command.hasSecondWord()){ 
           Character character = roomManager.getCurrentRoom().getCharacterByName(command.getSecondWord()); // theRoomManager returns the current room the player is in,
           // a method is called on the room to get the character object with the specified string (the name the player inputs) in it's name field
           // if the getCharacterByName method returns null, then either the spelling was wrong, the character doesn't exit, or it's not in the same room.
           if (character==null) System.out.println("There's no one called "+ command.getSecondWord() + " around.");
                    else 
                        System.out.println(character.getName()+": "+character.getDefaultLine());
        }
        else System.out.println("Chat with whom?");
    }
    
    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = roomManager.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomManager.setCurrentRoom(nextRoom);
            System.out.println(roomManager.getCurrentRoom().getLongDescription());
        }
    }
    
    /*
     * Takes the input words, tries to find the corresponding objects,
     * "gives" an item by removing it from the player's inventory and adding it to the character's
     */
    private void giveItem(Command command){
        Character character = null;
        Item item = null;
        String itemName = command.getSecondWord();
        if(itemName==null)
            System.out.println("Give what?");
        else {
            if(!player.checkItemByName(itemName)) // checkItemByName returns true if an item in thee player's inventory has the passed String as it's name
                System.out.println("You have no such item!");
        }
        
        String characterName = command.getThirdWord();
        character = nearbyCharacterByName(characterName); // Method that takes a name and returns the character in the same room that has that name, or null if it doesn't exist
            
        if (character!=null && player.checkItemByName(itemName))
            {
            item = player.removeItemByName(itemName);
            character.addItemToInventory(item);
            System.out.println("You give the "+item.getName()+" to "+character.getName());
            }
    }
    
    /*
     * Similar to the give method.
     * Inputs are interpreted, checked and "translated" to actual objects,
     * the inventories are manipulated through method calls and the item is moved from one to another.
     */
    private void takeItem(Command command){
        Character character = null;
        Item item = null;
        String itemName = command.getSecondWord();
        String characterName = command.getThirdWord();
        if (itemName==null)System.out.println("Take what?");
        
        character = nearbyCharacterByName(characterName);
        if (character!=null){ 
              if(!character.checkItemByName(itemName))
                 System.out.println("They have no such item!");
                 else {
                        item = character.takeItemByName(itemName);
                        if (player.addItemToInventory(item)) //if adding the item to the player's inventory was succesful, the condition will be met
                            System.out.println("You take the "+itemName+" from "+character.getName() );
                        else {  //otherwise we put the item in the room
                                roomManager.getCurrentRoom().addItem(item);
                                System.out.println("You couldn't take the "+itemName+", you leave it on the ground");
                            }
                      }
        
                    
                }
            
    }
    
    /*
     * Is used in the give and take methods to check whether the 
     * specified character name belongs to a character in the same room.
     * Returns the corresponding character object or null, if the character doesn't exist or isn't there.
     */
    private Character nearbyCharacterByName(String characterName){
        Character character = null;
        if (characterName == null)
            System.out.println("Who are you talking about?");
        else {
            character = characterManager.getCharacterByName(characterName);
            if (character==null)
                System.out.println(characterName +" isn't someone you know.");
            else {
                Room currentRoom = roomManager.getCurrentRoom();
                character = currentRoom.getCharacterByName(characterName);
                if (character==null)
                    System.out.println(characterName +" isn't around.");
            }
            }
        return character;    
    }
    
    /*
     * Calls a method on the Player object that returns a String containing a list of the items the player has and prints that String.
     */
    private void printInventory(){
        System.out.println(player.getStringWithItems());
    }
    
    /*
     * Adds an item to the player's inventory, removes it from the room. Checks validity of the command.
     */
    private void collectItem(Command command){
        Item item = roomManager.getCurrentRoom().getItemByName(command.getSecondWord()); // Takes the second word from the command and looks for an item in the room with that name. The item local variable will contain said item if it exists.
        if (item!=null){
            if (item.canBeCollected()){ 
                if (player.addItemToInventory(item)){ // the addItemToInventory method returns false if an item is too heavy to be added into the inventory
                        roomManager.getCurrentRoom().removeItem(item);  // if it returns true, meaning the item fits, the item is added to the inventory, removed from the room, and information about it is printed out
                        System.out.println("You pick up the " + item.getName());
                        String hint = item.getHint();
                        if (hint!=null)System.out.println(hint); // the hints in the Item class become null after being returned once, so that the game is not full of item hints
                    }
                    else System.out.println("You can't collect this item, you're carrying too much!");
                }
            else System.out.println("You probably don't wanna pick that up...");
        }
        else System.out.println("There is no such item in this room.");
    }
    
    /*
     * 
     */
    private void dropItem(Command command){
        String itemName = command.getSecondWord();
        if (itemName==null)System.out.println("Drop what?");
            else {
                Item item = player.removeItemByName(itemName); // This method removes the item (named itemName) from the player's inventory and returns the reference to it, so that the item can easily be stored someplace else
                if (item!=null){
                    roomManager.getCurrentRoom().addItem(item); // adds dropped item to room
                    System.out.println("You drop the "+ item.getName());
                }
                else System.out.println("You have no such item");
            }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
