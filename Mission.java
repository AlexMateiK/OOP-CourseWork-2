import java.util.ArrayList;
/**
 * A mission to be done by the player.
 * A mission is composed of tasks.
 * This class is more of an organisatorial one, it stores the tasks keeps track of the current task.
 *
 * @author Alexandru Matei
 * @version 1/12/2020
 */
public class Mission
{
    private ArrayList<Task> tasks;
    int currentTask;
    
    public Mission()
    {
        tasks = new ArrayList<>();
        currentTask=0;
    }

    /**
     * Adds a provided task to the mission.
     * Care must be taken to add the tasks in order, as each Mission object goes through the tasks one by one, in order, from the
     * first one that was added to the last.
     * @param task Task to be added. 
     */
    public void addTask(Task task){
        tasks.add(task);
    }
    
    /**
     * Attempts to complete current task.
     * @param roomManager needed for the task conditions checks
     * @param player needed for the condition checks
     * @param command Most recent command issued by the player.
     */
    public boolean doCurrentTask(RoomManager roomManager, Player player, Command command){
        if (checkCurrentTask(roomManager, player, command)){
            applyConsequences(player, roomManager);
            currentTask++;
            return true;
        }
        else return false;
    }
    
    /*
     * If the currentTask counter is still at a valid position in the task list,
     * the checkConditionsMet method will be called on the corresponding task
     * in order to determine if that that has just been done by the player.
     */
    private boolean checkCurrentTask(RoomManager roomManager, Player player, Command command){
        if (currentTask<tasks.size()){
            Task task = tasks.get(currentTask);
            return task.checkConditionsMet(roomManager, player, command);
        }
        else return false;
    }
    
    /*
     * If the checkCurrentTask method returned true, meaning the command fits and the conditions were met, 
     * this method will be used to make the task object apply it's consequences to the game.
     */
    public void applyConsequences(Player player, RoomManager roomManager){
        tasks.get(currentTask).applyConsequences(player, roomManager);
    }
}
