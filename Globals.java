package PurpleBot;
import battlecode.common.*;
//Defines all the variables/information relevant to all of our robots, such as current game mode, etc.
public strictfp class Globals {
    
    public static RobotController rc;
    public static MapLocation here;
    public static Team us;
    public static Team them;
    public static int myID;
    public static RobotType myType;
    
    //This will automatically run if a class EXTENDS Globals
    public static void init(RobotController theRC) {
        rc = theRC;
        us = rc.getTeam();
        them = us.opponent();
        myID = rc.getID();
        myType = rc.getType();
    }

    public static MapLocation update() {
        here = rc.getLocation();
        return here;
    }
}
