package game.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.Game;
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
		
		this.attackRadius = 200;
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
		removeTargets();
		addTargets();
		
	}
	
	private void addTargets(){
		
		for(Unit unit : Game.instance().getUnitManager().getUnitList()){
			if(distanceToUnit(unit) <= attackRadius){
				targets.add(unit);
			}
		}
		
	}
	
	private void removeTargets(){
		
		for(int i = targets.size(); i > 0; i--){

			//Remove unit from target list if died of other means
			if(!Game.instance().getUnitManager().getUnitList().contains(targets.get(i - 1))){
				targets.remove(i - 1);
			}
		}
		
		//Needed to go backwards in a for loop instead of for each to prevent removal error
		for(int i = targets.size(); i > 0; i--){

			Unit unit = targets.get(i - 1);
			
			if(distanceToUnit(unit) > attackRadius){
				targets.remove(unit);
			}	
		}
	}
	
	private float distanceToUnit(Unit unit){
		return (float) Math.pow((Math.pow(unit.getY() - y, 2) + Math.pow(unit.getX() - x, 2)),.5f);
	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(currentAnim.getCurrentFrame(), x, y, null);
		
		//Draws range of the tower
		//weapon.getRange()?
		g2d.drawOval((int)(x - attackRadius/2 + 27), (int) ((int)(y) - attackRadius/2 + 27), (int)attackRadius, (int)attackRadius);
	}
}