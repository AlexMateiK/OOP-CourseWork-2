import java.util.ArrayList;
/**
 * A simple class meant to store a collection of type Character,
 * create the Character objects needed to run the game
 * and to manage the movement of the characters.
 *
 * @author Alexandru Matei
 * @version 30/11/2020
 */
public class CharacterManager
{
    
    private ArrayList<Character> characters;
    private RoomManager roomManager;
    
    /**
     * Constructor for objects of class CharacterManager
     */
    public CharacterManager(RoomManager roomManager)
    {
        characters = new ArrayList<>();
        this.roomManager = roomManager;
        createCharacters();
    }

    /**
     * Creates the Character type objects used in the game and stores them.
     * The characters get their attributes at this point, since those are defined
     * through parameters passed to the constructor.
     */
    public void createCharacters()
    {
        Room livingroom = roomManager.getRoomByName("livingroom");
        characters.add(new Character("Dad",livingroom,"Hey, Alex."));
        characters.add(new Character("Joseph",livingroom,"Oh, hey!"));
        
        Room sistersbedroom = roomManager.getRoomByName("sistersbedroom");
        characters.add(new Character("Daria",sistersbedroom,"Ugh, I have so much work to do."));
       }
       
    /**
     * Randomly moves all characters that are not in the same room as the player.
     */   
    public void moveCharacters(){
        for (Character character: characters){
            if (roomManager.getCurrentRoom()!=character.getCurrentRoom())
            {
                character.moveToRandomNeighbor();
            }
        }
    }
    
    /**
     * Get the reference to a Character object by specifying the name of that character.
     * @param name Name of the character.
     * @return Character object with the given name, or null if there isn't such a character.
     */
    public Character getCharacterByName(String name){
        for (Character character: characters){
            if (name.toLowerCase().equals(character.getName().toLowerCase()))return character;
        }
    return null;
    }
}
