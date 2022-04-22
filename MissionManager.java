import java.util.HashSet;
/**
 * An organisatorial class, meant to manage Mission type objects.
 *
 * @author Alexandru Matei
 * @version 1/12/2020
 */
public class MissionManager
{
    
    private HashSet<Mission> missions;

    /**
     * Constructor for objects of type MissionManager.
     * @param missionCommandWords Object of type MissionCommandWords which the MissionCreator class needs in order to add task-related commands. 
     */
    public MissionManager(MissionCommandWords missionCommandWords)
    {
        missions = new HashSet<>();
        MissionCreator missionCreator = new MissionCreator(this, missionCommandWords);
    }

    /**
     * Adds a mission to the the mission manager's set of missions.
     * @param mission A mission you wish to add to the game.
     */
    public void addMission (Mission mission){
        missions.add(mission);
    }
    
    /**
     * Goes through all stored missions, makes each of them check if the task the player got to has just been satisfied.
     * @param roomManager the room manager, vital for checking the task conditions
     * @param player the player object, vital for checking the task conditions
     * @param command the player's most recent command 
     */
    public void tryCurrentTasks(RoomManager roomManager, Player player, Command command){
        for (Mission mission: missions){
            //System.out.println("sunt aici");
            mission.doCurrentTask(roomManager, player, command);
        }
    }
}
