package PurpleBot;
import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;


public strictfp class Gardener extends Globals {
	public static int currentMode;

    public static void loop() throws GameActionException{
    	findClearSpace();
    	setupTrees();
    	while(true){
    		// Temporary
    		rc.move(Direction.NORTH);
    		currentMode = rc.readBroadcast(0);
    		waterTrees();
    		generateRobot();
    		Clock.yield();
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
