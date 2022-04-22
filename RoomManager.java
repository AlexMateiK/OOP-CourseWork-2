import java.util.HashMap;


/**
 * Class used to create all the rooms at the beginning of the game,
 * add items to them, and store them.
 *
 * @author Alexandru Matei
 * @version 29/11/2020
 */
public class RoomManager
{
    
    private HashMap<String, Room> rooms;
    // HashMap associating room names with respective room objects.
    // for added flexibility and greater readability in the code
    private Room playerCurrentRoom;
    private Room lastRoom;

    
    public RoomManager()
    {
        rooms = new HashMap<>();
    }

    /**
     * Creates the rooms/locations for the game and stores them.
     * Each room is associated to a name, which will be later used to get the reference to that room.
     * This is where the whole "game map" is created and laid out.
     */
    public void createRooms(){

        // create the rooms
        rooms.put("bedroom",new Room("in your bedroom", this));
        rooms.put("hall", new Room("in the hall", this));
        rooms.put("livingroom", new Room("in the living room", this));
        rooms.put("backyard", new Room("in the backyard. You see a bunch of toys laying around, some gardon tools and a nice lemon tree. Maybe you can pick some lemons.", this));
        rooms.put("upstairs", new Room("in the upstairs hall", this));
        rooms.put("masterbedroom", new Room("in the master bedroom",this));
        rooms.put("cellar", new Room("in the cellar",this));
        rooms.put("kitchen", new Room("in the kitchen",this));
        rooms.put("sistersbedroom", new Room ("in your sister's bedroom. Daria spends a lot of time here studying.",this));
        rooms.put("frontyard", new Room("in the front yard. The neighbourhood is lively. People riding bikes, children playing, this view holds potential for your enterprising mindset.",this));
        rooms.put("attic", new Room("in the attic",this));
        
        // initialise room exits
        rooms.get("bedroom").setExit("east","hall");
        
        Room hall = rooms.get("hall");
        hall.setExit("west","bedroom");
        hall.setExit("up","upstairs");
        hall.setExit("north","backyard");
        hall.setExit("south", "frontyard");
        hall.setExit("east","livingroom");
        
        Room upstairs = rooms.get("upstairs");
        upstairs.setExit("down","hall");
        upstairs.setExit("west","sistersbedroom");
        upstairs.setExit("east","masterbedroom");
        upstairs.setExit("up","attic");
        
        rooms.get("attic").setExit("down","upstairs");
        
        rooms.get("sistersbedroom").setExit("east","upstairs");
        rooms.get("masterbedroom").setExit("west","upstairs");
        
        Room livingroom = rooms.get("livingroom");
        livingroom.setExit("west","hall");
        livingroom.setExit("north","kitchen");
        
        rooms.get("kitchen").setExit("south","livingroom");
        
        rooms.get("frontyard").setExit("north", "hall");
        
        Room backyard = rooms.get("backyard");
        backyard.setExit("south", "hall");
        backyard.setExit("down","cellar");
        
        rooms.get("cellar").setExit("up","backyard");
        
       playerCurrentRoom = getRoomByName("bedroom");
    }
    
    /**
     * Creates and adds all the items in the game to their respective rooms.
     */
    public void addItemsToRooms(){
    rooms.get("cellar").addItem(new Item("rag",1,true));
    rooms.get("cellar").addItem(new Item("cleaning_spray",2,true));
    rooms.get("kitchen").addItem(new Item("sugar",1,true,"Hmm, maybe I can make something with this"));
    rooms.get("kitchen").addItem(new Item("cups",1,true));
    rooms.get("bedroom").addItem(new Item("pen",1,true));
    rooms.get("sistersbedroom").addItem(new Item("notebook",1,true,"Might prove useful" ));
    rooms.get("sistersbedroom").addItem(new Item("textbook",1,true,"Time for homework!"));
    rooms.get("hall").addItem(new Item("glue",1,true));
    rooms.get("backyard").addItem(new Item("toy_wings",1,true,"You can add these to Joseph's toy"));
    rooms.get("backyard").addItem(new Item("ball",1,true));
    rooms.get("backyard").addItem(new Item("toy_train",1,true));
    rooms.get("backyard").addItem(new Item("hose",1,false));
    rooms.get("backyard").addItem(new Item("rake",1,false));
    rooms.get("livingroom").addItem(new Item("TV",10,false));
    rooms.get("attic").addItem(new Item("ring",1,true));
    }
    
    /**
     * Get the reference to a room object by providing its associated name.
     * @param name Implementation-level name of the room ("livingroom", etc.)
     * @return Room object associated with the provided name.
     */
    public Room getRoomByName(String name){
    return rooms.get(name);
    }
    
    /**
     * Get the reference to the room the player is in.
     * @return Room object where the player is.
     */
    public Room getCurrentRoom(){
        return playerCurrentRoom;
    }
    
    /**
     * Change the room the player is in to a specified room.
     * @param nextRoom A room object - the room you want to move the player to.
     */
    public void setCurrentRoom(Room nextRoom){
        lastRoom=playerCurrentRoom;
        playerCurrentRoom = nextRoom;
    }
    
    /**
     * Takes the player back to the last room they were in.
     * @return true if the player has been in any room beforehand, false if the game just started.
     */
    public boolean goBack(){
    if (lastRoom!=null){
        setCurrentRoom(lastRoom);
        return true;
    }
    else return false;
    }
    
    
}
