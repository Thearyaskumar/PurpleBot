package PurpleBot;

import battlecode.common.BulletInfo;
import battlecode.common.MapLocation;

//Call the appropriate movement method from here, eg. Movement.bugMove(MapLocation m)
public strictfp class Movement extends Globals {
	public static boolean move(MapLocation m) {
		tryShake();
		if (checkForDanger(m))
			return false;
		else
			return pathFind(m);
	}

	public static void tryShake() {
		// This is the shake method : Ansh
		// This will try to shake the first tree it can (if any), because trees
		// yield rewards!
		// Use update() to get current location, search for nearby trees in 6
		// directions. (2 unitsgit away)
		// If trees exist, shake one. (THIS WILL THROW AN EXCEPTION EASILY - USE
		// TRY-CATCH)
	}

	/**
	 * COSTS A LOT OF BYTECODES - checks for bullets in vicinity and if there
	 * are, moves away
	 */
	public static boolean checkForDanger(MapLocation m) {
		// This is a 6 point danger sensor: Dylan.
		// Look in 6 directions, at a location 0.25 units away and 2 units away
		// for each direction.
		// If there are bullets travelling TOWARDS you, calculate the damage
		// from each direction
		// Then move towards the location of the least damage. "rc" is your
		// Robot Controller.
		// At the end, if there was danger, return TRUE. else, return FALSE.
		// NOTE: If you returned false, and there was no danger, you should not
		// have moved.
		BulletInfo[] bullets = rc.senseNearbyBullets();
		if (bullets.length == 0)
			return false;
		int blen = bullets.length;
		float ourX = rc.getLocation().x, ourY = rc.getLocation().y;
		for (int i = 0; i < blen && i < 20; i++) {
			BulletInfo bulletInfo = bullets[i];
			MapLocation bStart = bulletInfo.location;
			MapLocation tmp = bulletInfo.location.add(bulletInfo.dir, 6);
			// Movement vector
			Vector a = new Vector(tmp.x - bStart.x, tmp.y - bStart.y);
			Vector b = new Vector(ourX - bStart.x, ourY - bStart.y);
			double scalar = (b.dot(a) / a.dot(a));
			Vector c = new Vector(a.x * scalar, a.y * scalar);
			Vector d = new Vector(b.x - c.x, b.y - c.y);
			System.out.println(d.magnitude());
		}
		return true;
	}

	public static boolean pathFind(MapLocation m) {
		// This is the Bug Pathfinding: Kiran
		// Bug Pathfind towards location m, avoiding obstacles and robots
		// (friendly or otherwise). DO NOT Clock.yield() at the end.
		// If pathFind was successful, return TRUE. Else, return FALSE (Some
		// error occored, impossible to reach, etc.)
		return false;
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