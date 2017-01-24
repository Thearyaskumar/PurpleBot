package PurpleBot;

import battlecode.common.BulletInfo;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

//Call the appropriate movement method from here, eg. Movement.bugMove(MapLocation m)
public strictfp class Movement extends Globals {
	public static boolean move(MapLocation m) {
		tryShake();
		if (checkForDanger())
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
	public static boolean checkForDanger() {
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
		if (collisions.length == 0)
			return false;
		double fleeAngle = -collisions[0].direction;
		for (int i = 1; i < cIndex; i++) {
			fleeAngle += -collisions[i].direction * collisions[i].threatLevel;
		}
		try {
			rc.move(new Direction((float) fleeAngle));
		} catch (GameActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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