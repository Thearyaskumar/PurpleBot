package PurpleBot;
import battlecode.common.*;
//Call the appropriate movement method from here, eg. Movement.bugMove(MapLocation m)
public strictfp class Movement {
    public static boolean bugMove(MapLocation m){
    	if(checkForDanger(m))
    		return false;
    	else
    		return pathFind();
    }

    public static boolean checkForDanger(MapLocation m){
    	//This is a 6 point danger sensor: Dylan. 
    	//Look in 6 directions, at a location 0.25 units away and 2 units away for each direction.
    	//If there are bullets travelling TOWARDS you, calculate the damage from each direction
    	//Then move towards the location of the least damage. "rc" is your Robot Controller.
    	//At the end, if there was danger, return TRUE. else, return FALSE. 
    	//NOTE: If you returned false, and there was no danger, you should not have moved.
    }

    public static boolean pathFind(MapLocation m){
        //This is the Bug Pathfinding: Kiran
        //Bug Pathfind towards location m, avoiding obstacles and robots (friendly or otherwise). DO NOT Clock.yield() at the end.
    	//If pathFind was successful, return TRUE. Else, return FALSE (Some error occored, impossible to reach, etc.)
    }
}
