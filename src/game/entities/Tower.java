package game.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.graphics.Animation;

public class Tower {

	private int x;
	private int y;
	
	private List<Unit> targets;
	private float attackRadius;
	private float rateOfFire;
	
	private List<Animation> animations;
	private Animation currentAnim;
	
	public Tower(int x, int y, int radius, int rateOfFire, ArrayList<Animation> animations) {
		targets = new ArrayList<Unit>();
		
		this.x = x;
		this.y = y;
		
		this.attackRadius = radius;
		this.rateOfFire = rateOfFire;
		this.animations = animations;
		this.currentAnim = this.animations.get(0);
		//this.weapon = weapon;
	}
	
	public void setAnimation(String direction) {
		switch (direction) {
		case "down":
			currentAnim = animations.get(0);
			break;
		case "left":
			currentAnim = animations.get(1);
			break;
		case "right":
			currentAnim = animations.get(2);
			break;
		case "up":
			currentAnim = animations.get(3);
			break;
		case "down-left":
			currentAnim = animations.get(4);
			break;
		case "down-right":
			currentAnim = animations.get(5);
			break;
		case "up-right":
			currentAnim = animations.get(6);
			break;
		case "up-left":
			currentAnim = animations.get(7);
			break;
		default:
			currentAnim = animations.get(0);
			break;
		}
	}
	
	public void update(long gametime) {
		currentAnim.update(gametime);
	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(currentAnim.getCurrentFrame(), x, y, null);
	}
}
