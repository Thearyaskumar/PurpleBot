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
	private static RobotInfo[] nearbyRobots;

	//Main Method:
    public static void loop() throws GameActionException{
    	System.out.println(locationToInt(new MapLocation(51,439)) + ""); //51, 439
    	while(true){
    		trySubmitBullets();
    		determineMode();
    		if(currentMode != FLEE)
    			genGardener();
    		else
    			flee();
    		broadcastLocation();
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
    	nearbyRobots = rc.senseNearbyRobots(-1, them);
    	for(RobotInfo rob : nearbyRobots){
    		if(rob.getType() == RobotType.SOLDIER)
    			count+=5;
    		if(rob.getType() == RobotType.SCOUT)
    			count+=2;
    		if(rob.getType() == RobotType.TANK)
    			count+=10;
    		if(rob.getType() == RobotType.ARCHON)
    			containsArchon = true;
    	}
    	count += rc.senseNearbyBullets().length * 3; //Add bullets to count.
    	if(count > 4)
    		currentMode = DEFENCE;
    	if(count > 9)
    		currentMode = FLEE;
    	
    	//Testing for Enemy Archons (This overrides FLEE iff count < 5)
    	if(count < 5 && containsArchon)
    		currentMode = ARCHON_ATTACK;

    	//Commit to it (Broadcast to all robots)
    	rc.broadcast(0, currentMode);
    }

    public static void genGardener() throws GameActionException {
    	for(Direction d : new Direction[]{Direction.NORTH,Direction.SOUTH,Direction.EAST,Direction.WEST})
    		if(rc.canHireGardener(d)){
    			rc.hireGardener(d);
    			break;
    		}
    }

    public static void flee(){ //MAKE DYLAN DO THIS! (Current code is temporary)
    	System.out.println("Flee!");
    	Movement.checkForDanger();
    }

    public static void broadcastLocation() throws GameActionException{
    	if(here != rc.getLocation()){
    		rc.broadcast(1, locationToInt(update()));
    		System.out.println("Broadcasted New Location");
    	}
    }
    public static int locationToInt(MapLocation m){
    	String str = fastIntLength((int)m.x) + "" + fastIntLength((int)m.y) + "" + (int)m.x + "" + (int)m.y;
    	return Integer.parseInt(str);
    }
    public static MapLocation intToLocation(int i){ //FIX THIS! (Arya)
    	return null;
    }
    public static int fastIntLength(int n){
    	return (n<100000)?((n<100)?((n<10)?1:2):(n<1000)?3:((n<10000)?4:5)):((n<10000000)?((n<1000000)?6:7):((n<100000000)?8:((n<1000000000)?9:10)));
    }
}


/* The broadcast:
[0] : currentMode
[1] : currentArchonLocation [length of x coord] [length of y coord] [x coord] [y coord] NOTE: Use Archon.locationToInt() and Archon.intToLocation()
*/