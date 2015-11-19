package game.weapons;

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
	
	public Projectile(int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
}
