package PurpleBot;
import battlecode.common.*;

public strictfp class Archon extends Globals {
    public void loop(){
    	while(true){
    		trySubmitBullets();
    		Clock.yield();
    	}
    }
    public void trySubmitBullets(){
    	if(rc.getTeamBullets() > 1000 * rc.getVictoryPointCost()) //Can Win
    		rc.donate(1000 * rc.getVictoryPointCost());
    	if(rc.getTeamBullets() > 500)
    		rc.donate(50);
    }
}
