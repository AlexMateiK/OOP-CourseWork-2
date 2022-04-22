
/**
 * This class is used to create all the missions in the game.
 *
 * @author Alexandru Matei
 * @version 1/12/2020
 */
public class MissionCreator
{
    
    private MissionCommandWords missionCommands;
    
    /**
     * Constructor for MissionCreator type objects
     * @param missionManager It needs a missionManager to put the missions it creates into.
     * @param missionCommandWords It needs a MissionCommandWords type objects to add new task-specific commands as the need arises.
     */
    public MissionCreator(MissionManager missionManager, MissionCommandWords missionCommandWords)
    {
        missionCommands = missionCommandWords;
        createMissions(missionManager);
    }
    
    /*
     * Creates mission objects,
     * calls the internal methods which construct
     * these missions, adds the missions to the missionManager.
     */
    private void createMissions(MissionManager missionManager){
        Mission mission1 = new Mission();
        createTasksMission1(mission1);
        missionManager.addMission(mission1);
        
        Mission mission2 = new Mission();
        createTasksMission2(mission2);
        missionManager.addMission(mission2);
        
        Mission mission3 = new Mission();
        createTasksMission3(mission3);
        missionManager.addMission(mission3);
        
        Mission mission4 = new Mission();
        createTasksMission4(mission4);
        missionManager.addMission(mission4);
    } 
    
    /*
     * The descriptive method names make the manipulation
     * of the task objects both intuitive to do and easy to read.
     */
    
    private void createTasksMission1(Mission mission1){
        Command command1 = new Command("pick","lemons",null);
        Task task1 = new Task(command1);
        task1.addRoomToBeIn("backyard");
        missionCommands.addMissionCommand("pick");
        
        Item lemons = new Item("lemons",5,true);
        task1.addItemToGive(lemons);
        
        task1.setEndMessage("Great! Lemons.. maybe you could make lemonade out of this. Make sure you add some sugar!");
        
        mission1.addTask(task1);
        
       
        Command command3 = new Command("make","lemonade",null);
        missionCommands.addMissionCommand("make");
        Task task3 = new Task(command3);
        
        task3.addRoomToBeIn("kitchen");
        task3.addItemToHave("sugar");
        task3.addItemToHave("lemons");
        
        task3.addItemToRemove("sugar");
        task3.addItemToRemove("lemons");
        
        Item lemonade = new Item("lemonade",5,true);
        
        task3.addItemToGive(lemonade);
        task3.setEndMessage("You could get some money by selling the lemonade, go to your front yard and find someone to sell it to! Take some cups, too");
        
        mission1.addTask(task3);
        
        Command command5 = new Command("sell","lemonade",null);
        missionCommands.addMissionCommand("sell");
        Task task5 = new Task(command5);
        
        task5.addRoomToBeIn("frontyard");
        task5.addItemToHave("cups");
        task5.addItemToHave("lemonade");
        
        task5.addItemToRemove("cups");
        task5.addItemToRemove("lemonade");
        
        task5.setEndMessage("You sell lemonade to any kid that happens to walk by your house. The hot summer sun makes it such that no one refuses your offer. You make 10 pounds.");
        task5.setCashReward(10);
        
        mission1.addTask(task5);
        
    }
    
    private void createTasksMission2(Mission mission2){
    
    Command command1 = new Command("chat","dad",null);
    Task task1 = new Task(command1);
    
    task1.addCharacterToBePresent("Dad");
    
    task1.setEndMessage("Dad: Wanna help me with something? If you clean the windows in the living room, I'll give you some pocket money.");
    mission2.addTask(task1);
    
    Command command2 = new Command("clean","windows",null);
    missionCommands.addMissionCommand("clean");
    Task task2 = new Task(command2);
    
    task2.addItemToHave("rag");
    task2.addItemToHave("cleaning_spray");
    task2.addRoomToBeIn("livingroom");
    
    task2.setEndMessage("Good job! You should go chat with your Dad and get your reward!");
    mission2.addTask(task2);
    
    Command command3 = new Command("chat","dad",null);
    Task task3 = new Task(command3);
    
    task3.addCharacterToBePresent("Dad");
    task3.setEndMessage("Dad: Thank you! Those windows haven't been this clean in years! Here's your reward. You know what? If you find that ring I've been searching for the past month, I might add a little extra.");
    task3.setCashReward(5);
    mission2.addTask(task3);
    
    Command command4 = new Command("give","ring","dad");
    Task task4 = new Task(command4);
    task4.addCharacterToBePresent("Dad");
    task4.addItemPresentCharacterShouldHave("ring");
    task4.setCashReward(5);
    task4.setEndMessage("Dad: Wow! I can't believe you've found it! Here, you deserve this!");
    
    mission2.addTask(task4);
    
    }
    
    private void createTasksMission3(Mission mission3){
    Command command1 = new Command("chat","daria",null);
    Task task1 = new Task(command1);
    
    task1.addCharacterToBePresent("Daria");
    
    task1.setEndMessage("Daria: About that.. I don't think I've got enough time to finish everything. If you do part of my homework I'll help you out with buying that toy.");
    mission3.addTask(task1);
    
    Command command2 = new Command("do","homework",null);
    Task task2 = new Task(command2);
    task2.addItemToHave("pen");
    task2.addItemToHave("textbook");
    task2.addItemToHave("notebook");
    missionCommands.addMissionCommand("do");
    
    mission3.addTask(task2);
    
    Item item = new Item("homework",1,true);
    task2.addItemToGive(item);
    task2.setEndMessage("Whew! That was some work! Go and give the finished homework to Daria. HINT:Check your inventory, use the give command.");
    
    Command command3 = new Command("give","homework","daria");
    Task task3 = new Task(command3);
    task3.addItemPresentCharacterShouldHave("homework");
    task3.addCharacterToBePresent("Daria");
    task3.setEndMessage("Daria: Thank you Alex! I might just finish in time. Please, don't tell Dad! Here's something for your trouble.");
    task3.setCashReward(5);
    
    mission3.addTask(task3);
    
    }
    
    private void createTasksMission4(Mission mission4){
    Command command1 = new Command("chat","joseph",null);
    Task task1 = new Task(command1);
    
    task1.addCharacterToBePresent("Joseph");
    task1.setEndMessage("Joseph: Argh!! I'm so angry! I broke my favourite transformers toy! Can you help me fix it? There's some glue in the hall. Here, take my toy.. HINT:Check inventory");
    Item toy = new Item("broken_toy",2,true);
    task1.addItemToGive(toy);
    mission4.addTask(task1);
   
    Command command2 = new Command("fix","broken_toy",null);
    missionCommands.addMissionCommand("fix");
    Task task2 = new Task(command2);
    task2.addItemToHave("glue");
    task2.addItemToHave("broken_toy");
    task2.addItemToRemove("broken_toy");
    Item fixedToy = new Item("fixed_toy",2,true);
    task2.addItemToGive(fixedToy);
    task2.setEndMessage("You fixed Joseph's toy. Go ahead and give the fixed_toy to him");
    mission4.addTask(task2);
    
    Command command3 = new Command("give","fixed_toy","joseph");
    Task task3 = new Task(command3);
    task3.addCharacterToBePresent("Joseph");
    task3.setEndMessage("Joseph: ..poor Bumblebee.. it looks so damaged... could you maybe add something new to it? Take it back and look in the backyard for something cool. HINT: Take the toy from Joseph and add some wings");
    mission4.addTask(task3);
    
    Command command4 = new Command("add","toy_wings",null);
    missionCommands.addMissionCommand("add");
    Task task4 = new Task(command4);
    task4.addItemToHave("fixed_toy");
    task4.addItemToHave("glue");
    task4.addItemToHave("toy_wings");
    task4.addItemToRemove("fixed_toy");
    task4.addItemToRemove("toy_wings");
    Item upgradedToy = new Item("upgraded_toy",2,true);
    task4.addItemToGive(upgradedToy);
    task4.setEndMessage("You succesfully added wings to Bumblebee.. give the upgraded_toy to Joseph! HINT: Check inventory");
    mission4.addTask(task4);
    
    Command command5 = new Command("give","upgraded_toy", "joseph");
    Task task5 = new Task(command5);
    task5.addItemPresentCharacterShouldHave("upgraded_toy");
    task5.addCharacterToBePresent("Joseph");
    task5.setEndMessage("Joseph: Wow! This is so cool! Hey.. Alex, I really appreciate you fixing my toy.. I want to help you buy that watercannon, here, have this.");
    task5.setCashReward(5);
    
    mission4.addTask(task5);
    }

}
