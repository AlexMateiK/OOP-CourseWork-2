import java.util.Scanner;

/**
 * 
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a three word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * Later addition: The parser now has also uses an object of type MissionCommandWords
 * which contains commands added as part of tasks, which have no other functionality than
 * to be recognized in order to progress the game, but still need to be recognized 
 * in the same way.
 * 
 * @author  Michael Kölling and David J. Barnes, modified by Alexandru Matei k20054925
 * @version 1/12/2020
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private MissionCommandWords missionCommands;
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Assigns a MissionCommandWords object to the parser, which stores and manages task-specific command words much in the same way as the CommandWords class does with standard commands.
     */
    public void setMissionCommandWords(MissionCommandWords missionCommands){
    this.missionCommands = missionCommands; 
    }
    
    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;
        
        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine().toLowerCase();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if (!missionCommands.isMissionCommand(word1))
                word1 = commands.representsCommand(word1);      // get internal command word associated with first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                
            }
            if (tokenizer.hasNext()){
                word3 = tokenizer.next(); // get the third word
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1) || missionCommands.isMissionCommand(word1)) { // the command word is also checked against the mission command list
            return new Command(word1, word2, word3);
        }
        else {
            return new Command(null, word2, word3); 
        }
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        commands.showAllCommands();
    }
    
    /**
     * Print out a list of valid task specific command words.
     */
    public void showMissionCommands()
    {
        missionCommands.showAllMissionSpecificCommands();
    }
}
