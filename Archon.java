package PurpleBot;
import battlecode.common.*;
//Done EXCEPT FOR FLEE!
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
    	while(true){
    		trySubmitBullets();
    		determineMode();
    		if(currentMode != FLEE)
    			genGardener();
    		else
    			System.out.println(flee() ? "Fleeing!" : "Unable to flee!");
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

    public static boolean flee(){ //Copied from Movement.java
        BulletInfo[] bullets = rc.senseNearbyBullets();
        int blen = bullets.length;
        float ourX = rc.getLocation().x, ourY = rc.getLocation().y;
        // All collisions from all directions
        Danger[] collisions = new Danger[25];
        int cIndex = 0;
        // Calculate the danger that each bullet presents
        for (int i = 0; i < blen && i < 20; i++) {
            BulletInfo bulletInfo = bullets[i];
            MapLocation bStart = bulletInfo.location;
            MapLocation tmp = bulletInfo.location.add(bulletInfo.dir, 6);
            // Movement vector
            Vector a = new Vector(tmp.x - bStart.x, tmp.y - bStart.y);
            // Vector between bullet and robot
            Vector b = new Vector(ourX - bStart.x, ourY - bStart.y);
            double scalar = (b.dot(a) / a.dot(a));
            // B projected onto A
            Vector c = new Vector(a.x * scalar, a.y * scalar);
            // perpendicular distance between bullet vector and robot position
            Vector d = new Vector(b.x - c.x, b.y - c.y);
            double dist = d.magnitude();
            // If the bullet will hit us on its current path
            if (dist <= rc.getType().bodyRadius + bulletInfo.getRadius()) {
                collisions[cIndex++] = new Danger(bulletInfo.damage, bulletInfo.speed, dist, bulletInfo.dir.radians);
            }
        }

        // Dylan : Add the code to check for Robots, weighted by Robot.TYPE

        if (cIndex == 0) //No Danger
            return;

        double fleeAngle = -collisions[0].direction; //There is danger - determine which direction to move in!
        for (int i = 1; i < cIndex; i++) {
            fleeAngle += -collisions[i].direction * collisions[i].threatLevel;
        }

        try {
            if(rc.canMove(new Direction((float) fleeAngle + 90)))
                rc.move(new Direction((float) fleeAngle + 90));
            else if(rc.canMove(new Direction((float) fleeAngle - 90)))
                rc.move(new Direction((float) fleeAngle - 90));
        } catch (GameActionException e) {
            e.printStackTrace();
        }
    }
}

class Danger {
    public final double threatLevel, direction;

    public Danger(float damage, float speed, double dist, double direction) {
        threatLevel = dist / speed * damage;
        this.direction = direction;
    }
}

class Vector {
    public final double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dot(Vector other) {
        return x * other.x + y * other.y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }
}

/* The broadcast:
[0] : currentMode
[1] : currentArchonLocation [length of x coord] [length of y coord] [x coord] [y coord] NOTE: Use Archon.locationToInt() and Archon.intToLocation()
*/