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
	private static int currentMode = 0;

    public static void loop() throws GameActionException{
    	while(true){
    		trySubmitBullets();
    		switch(currentMode){
    			//Add switch cases here based on mode!
    			case GROW: System.out.println("Grow");
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
    	rc.broadcast(0, currentMode);
    }
}