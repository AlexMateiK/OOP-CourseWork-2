import java.util.HashSet;
/**
 * This class creates an object which stores, in a flexible manner, and allows acces to
 * all the commands in the game that are only used to progress tasks,
 * but not as actual functional interactive features.
 *
 * Mission commands don't "trigger" anything by themselves,  
 * they more or less take the form of a requirement/condition, and can be changed in the MissionCreator type object, but there isn't a functional reason to
 * add natural language independence, since they are tied to the story rather than the actual game mechanics (i.e drop should always make you drop an item,
 * but pick is specific to the pick lemons command specific to a certain task)
 * 
 * @author Alexandru Matei
 * @version 1/12/2020
 */
public class MissionCommandWords
{
   
    private HashSet<String> missionCommands;

    /**
     * Constructor for objects of class MissionCommandWords
     */
    public MissionCommandWords()
    {
        missionCommands = new HashSet<>();
    }

    /**
     * Adds a specified word to the set of task specific commands/mission commands.
     * @param word a String containing a task specific command word.
     */
    public void addMissionCommand(String word){
        missionCommands.add(word);
    }
    
    /**
     * Checks if a given word is part of its list of task-related commands.
     * @param aString A string you wish to check.
     * @return true - if the given String is a task-specific command word, false otherwise.
     */
    public boolean isMissionCommand(String aString){
        return (missionCommands.contains(aString));
    }
    
    /**
     * Prints out all the command words created for the missions.
     */
    public void showAllMissionSpecificCommands(){
        for (String word: missionCommands){
            System.out.print(word + " ");
        }
        System.out.println();
    }
    
}
