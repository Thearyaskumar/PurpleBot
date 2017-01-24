package PurpleBot;
import battlecode.common.*; //CHANGE THIS
//This must be simplified to just run the appropriate code from other classes:
public strictfp class RobotPlayer {
    static RobotController rc;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
    **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        Globals.init(rc); //Starts the init process in Globals.java
        RobotPlayer.rc = rc;

        //Switch to the correct class
        switch (rc.getType()) {
            case ARCHON:
                Archon.loop();
                break;
            case GARDENER:
                Gardener.loop();
                break;
            case SOLDIER:
                Soldier.loop();
                break;
            case LUMBERJACK:
                Lumberjack.loop();
                break;
            case TANK:
                Tank.loop();
                break;
        }
	}
}
