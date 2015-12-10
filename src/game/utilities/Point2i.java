package game.utilities;

public class Point2i {

	private int x;
	private int y;
	
	public Point2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Utility functions
	public boolean intersectsCircle(int x, int y, float radius) {
		return Math.pow((this.x - x), 2) + Math.pow((this.y - y), 2) < Math.pow(radius, 2);
	}
	
	public boolean intersectsCircle(Point2i center, float radius) {
		return intersectsCircle(center.getX(), center.getY(), radius);
	}
	
	// Getters / Setters
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
}
