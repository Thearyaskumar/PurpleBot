package PurpleBot;
import battlecode.common.*;

public strictfp class Archon extends Globals {

	//Final Ints: Modes
	public final static int GROW = 0;
	public final static int DEFENCE = 1;
	public final static int FLEE = 2;
	public final static int ARCHON_ATTACK = 3;
	public final static int CLEANUP_ATTACK = 4; //Will not be used for now.

	//Private Fields
	private static int currentMode = GROW;

    public static void loop() throws GameActionException{
    	while(true){
    		trySubmitBullets();
    		determineMode();
    		switch(currentMode){
    			//Add switch cases here based on mode!
    			case GROW:
    				System.out.println("Grow");
    				break;
    			case DEFENCE:
    				System.out.println("Defence");
    				break;
    			case FLEE:
    				System.out.println("Flee");
    				break;
    			case ARCHON_ATTACK:
    				System.out.println("Archon Attack");
    				break;
    		}
    		Clock.yield();
    	}
    }
    public static void trySubmitBullets() throws GameActionException {
    	if(rc.getTeamBullets() > 1000 * rc.getVictoryPointCost()) //Can Win
    		rc.donate(1000 * rc.getVictoryPointCost());
    	if(rc.getTeamBullets() > 500)
    		rc.donate(50);
    }

    public static void determineMode() throws GameActionException { //Decides the mode for the turn, then broadcasts it.
    	//Default: GROW
    	if(rc.getTreeCount() < 10) //Grow if we don't have a minimum number of trees.
    		currentMode = GROW;

    	//If an enemy is nearby (not Archon)
    	int count = 0;
    	boolean containsArchon = false;
    	for(RobotInfo rob : rc.senseNearbyRobots(-1, them)){
    		if(rob.getType() == RobotType.SOLDIER)
    			count+=5;
    		if(rob.getType() == RobotType.SCOUT)
    			count+=2;
    		if(rob.getType() == RobotType.TANK)
    			count+=10;
    		if(rob.getType() == RobotType.ARCHON)
    			containsArchon = true;
    	}
    	count += rc.senseNearbyBullets().length; //Add bullets to count.
    	System.out.println(count + "");
    	if(count > 4)
    		currentMode = DEFENCE;
    	if(count > 9)
    		currentMode = FLEE;
    	
    	//Testing for Enemy Archons (This overrides FLEE iff count < 5)
    	if(count < 5 && containsArchon)
    		currentMode = ARCHON_ATTACK;

    	rc.broadcast(0, currentMode);
    }
}