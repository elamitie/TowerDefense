package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.graphics.Animation;
import game.utilities.Mouse;
import game.weapons.Weapon;

public class Tower{

	private int x;
	private int y;
	private boolean selected;
	
	private List<Unit> targets;
	
	private List<Animation> animations;
	private Animation currentAnim;
	
	private Weapon weapon;
	private TowerManager parent;
	
	public Tower(int x, int y, ArrayList<Animation> animations, Weapon weapon) {
		targets = new ArrayList<Unit>();
		
		this.x = x;
		this.y = y;
		this.selected = false;
		
		this.animations = animations;
		this.currentAnim = this.animations.get(0);
		
		this.weapon = weapon;
		this.weapon.init(this);
		
		this.parent = null;
	}
	
	public void setAnimation(Direction direction) {
		switch (direction) {
		case Down:
			currentAnim = animations.get(0);
			break;
		case Left:
			currentAnim = animations.get(1);
			break;
		case Right:
			currentAnim = animations.get(2);
			break;
		case Up:
			currentAnim = animations.get(3);
			break;
		case DownLeft:
			currentAnim = animations.get(4);
			break;
		case DownRight:
			currentAnim = animations.get(5);
			break;
		case UpRight:
			currentAnim = animations.get(6);
			break;
		case UpLeft:
			currentAnim = animations.get(7);
			break;
		default:
			currentAnim = animations.get(0);
			break;
		}
	}
	
	public void update(long gametime) {
		// currentAnim.getCurrentFrame().getWidth() feels like it only works for perfect circles, not that it matters in this case
		int radius = currentAnim.getCurrentFrame().getWidth() / 2;
		boolean intersects = Mouse.getXY().intersectsCircle(x + radius, y + radius, radius);
		
		if (Mouse.leftPressed && intersects) {
			selected = true;
			
			// Deselect the rest of the towers
			deselectOthers();
		}
		
		currentAnim.update(gametime);
		weapon.update(gametime);
		removeTargets();
		addTargets(gametime);
		
		if(weapon.fireTimer > 0) {
			weapon.fireTimer -= gametime;
			
			if(weapon.fireTimer <= 0) {
				//weapon.fire(unit);
				weapon.reset();
			}
		}
	}
	
	private void addTargets(long gametime) {
		System.out.println(targets.size());
		for(Unit unit : Game.instance().getUnitManager().getUnitList()) {
			if(!targets.contains(unit) && distanceToUnit(unit) <= weapon.getAttackRadius()) {
				targets.add(unit);
			}
		}
	}
	
	private void removeTargets() {
		for(int i = targets.size(); i > 0; i--){

			//Remove unit from target list if died of other means
			if(!Game.instance().getUnitManager().getUnitList().contains(targets.get(i - 1))){
				targets.remove(i - 1);
			}
		}
		
		//Needed to go backwards in a for loop instead of for each to prevent removal error
		for(int i = targets.size(); i > 0; i--){

			Unit unit = targets.get(i - 1);
			
			if(distanceToUnit(unit) > weapon.getAttackRadius()){
				targets.remove(unit);
			}	
		}
	}
	
	private float distanceToUnit(Unit unit) {
		return (float) Math.pow((Math.pow(unit.getY() - y, 2) + Math.pow(unit.getX() - x, 2)),.5f);
	}
	
	public void draw(Graphics2D g2d) {
		weapon.draw(g2d);
		g2d.drawImage(currentAnim.getCurrentFrame(), x, y, null);
		
		// Draw selected circle
		if (selected) {
			Color col = g2d.getColor();
			int w = currentAnim.getCurrentFrame().getWidth();
			int h = currentAnim.getCurrentFrame().getHeight();
			g2d.setColor(Color.yellow);
			g2d.drawOval(x, y, w, h);
			g2d.setColor(col);
		}
	}
	
	private void deselectOthers() {
		for (Tower t : parent.getAll()) {
			if (t != this)
				t.selected = false;
		}
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return currentAnim.getCurrentFrame().getWidth(); }
	public int getHeight() { return currentAnim.getCurrentFrame().getHeight(); }
	public int getHalfWidth() { return getWidth() / 2; }
	public int getHalfHeight() { return getHeight() / 2; }
	public Animation getCurrentAnim() { return currentAnim; }
	
	public void setParent(TowerManager parent) { this.parent = parent; }

}