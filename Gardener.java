package PurpleBot;
import battlecode.common.GameActionException;


public strictfp class Gardener extends Globals {
	public static int currentMode;

    public static void loop() throws GameActionException{
    	findClearSpace();
    	setupTrees();
    	while(true){
    		currentMode = rc.readBroadcast(0);
    		waterTrees();
    		generateRobot();

    	}
    }

    public static void findClearSpace() throws GameActionException{

    }
    public static void setupTrees() throws GameActionException{

    }
    public static void waterTrees() throws GameActionException{
    	//if(currentMode != Archon.FLEE)
    		
    }
    public static void generateRobot() throws GameActionException{
    	
    }
}
