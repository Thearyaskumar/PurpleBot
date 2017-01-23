package PurpleBot;
import battlecode.common.*;
//Send and recieve messages through here, such as Messages.recieveEnemyArchonLocations() --> [MapLocation a, MapLocation b]
public strictfp class Messages {
	private int currentMode = 0;

    public void getMode(){
    	return currentMode();
    }

    public int update(){ //Whenever you wish to collect information, update will let you know how many messages are relevant to you!

    }
}

/*

	How the messages array works:
	[0] : Mode {(0:Growth), (1:Siege Defense), (2:Runaway Defence), (3:Archon Attack), (4:Supply Attack), (5:Cleanup Attack)}
	[1] : Enemy Archon Location
*/