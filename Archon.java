package PurpleBot;
import battlecode.common.*;

public strictfp class Archon extends Globals {
    public static void loop() throws GameActionException{
    	while(true){
    		trySubmitBullets();
    		int currentMode = determineMode();
    		switch(currentMode){
    			//Add switch cases here based on mode!
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

    public static int determineMode(){ //Arya will do this one
    	return 0;
    }
}