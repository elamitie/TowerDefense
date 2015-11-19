package game.weapons;

import game.entities.Unit;

public class Projectile {

	// Java doesn't have structs so I'm just using public data. No point in using
	// Getters and setters when the extra function call is pointless and is that
	// much slower. It's just data.
	public int x;
	public int y;
	public int targetX;
	public int targetY;
	public double angle;
	public float speed;
	private Unit target;
	
	public Projectile(int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public float distanceToTarget(){
		return (float) Math.pow((Math.pow(targetY - y, 2) + Math.pow(targetX - x, 2)),.5f);
	}

	public Unit getTarget() {
		return target;
	}

	public void setTarget(Unit target) {
		this.target = target;
	}
	
}
