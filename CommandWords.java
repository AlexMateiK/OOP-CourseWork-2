import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes, modified by Alexandru Matei, K20054925
 * @version 1/12/2020
 */

public class CommandWords
{
    
    private HashMap<String, String> validCommands;
    
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        fillInCommands();
    }
    
    /**
     * Initializes the valid commands list.
     */
    /*
    Each command is represented by an entry into the HashMap.
    
    Key = any word you wish to associate with a command, it will be used
    IN-GAME by the user in order to undertake in action.
    
    Value = name of command, always in english, used in the code
    to recognize command chosen by user and trigger it's effects.
    
    I have chosen this implementation in order to be able to add 
    any action/command to the game and have it be independent from
    a natural language, in the sense that only this map needs to be changed
    if a translation takes place.
    I prefer this over using an Enum type because it's easier to add commands on the fly.
    */
    private void fillInCommands(){
        validCommands.put("go","go");
        validCommands.put("help","help");
        validCommands.put("quit","quit");
        validCommands.put("collect","collect");
        validCommands.put("drop","drop");
        validCommands.put("back","back");
        validCommands.put("search","search");
        validCommands.put("inventory","inventory");
        validCommands.put("give","give");
        validCommands.put("wallet","wallet");
        validCommands.put("chat","chat");
        validCommands.put("take","take");
        validCommands.put("buy","buy");
        
    }

    
    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return (validCommands.containsValue(aString));
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAllCommands() 
    {
        Set<String> keySet = validCommands.keySet();
        for (String key : keySet){
            System.out.print(key + " ");
        }
        System.out.println();
    }

    
    /**
     * Returns the command a word is associated with.
     * 
     * @param aString A word from the user's input.
     * @return String containing a standardized english command associated with given word, or null if the word doesn't represent a command.
     */
    public String representsCommand(String aString){
        if (validCommands.get(aString)==null){
              
                return null;   
        }                          
        return validCommands.get(aString);
    }
}
