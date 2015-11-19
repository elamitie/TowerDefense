package game.weapons;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.entities.Unit;

public class ProjectileBasedWeapon extends Weapon {

	protected List<Projectile> projectiles;
	private int projectileRadius;
	private int damage;
	private int speed;
	
	// Special weapon specific data
	private int aoe;
	private float slow;
	private long slowTime;
	private boolean antiAir;
	
	public ProjectileBasedWeapon() {
		projectiles = new ArrayList<Projectile>();
		this.projectileRadius = 0;
		this.damage = 0;
		this.aoe = 0;
		this.slow = 0;
		this.slowTime = 0;
		this.speed = 50;
		this.antiAir = false;
	}

	@Override
	public void fire(Unit unit) {
		Projectile p = new Projectile(parent.getX() + parent.getHalfWidth(), parent.getY() + parent.getHalfHeight(), speed);
		
		if (antiAir) {
			if (unit.isCanFly()) {
				p.targetX = unit.getX() + unit.getHalfWidth();
				p.targetY = unit.getY() + unit.getHalfHeight();
				p.setTarget(unit);
			}
		}
		else {
			p.targetX = unit.getX() + unit.getHalfWidth();
			p.targetY = unit.getY() + unit.getHalfHeight();
			p.setTarget(unit);
		}
		
		p.angle = Math.atan2(p.targetY - p.y, p.targetX - p.x);
		Math.toDegrees(p.angle);
		projectiles.add(p);
	}
	
	@Override
	public void update(long gameTime) {
		for (int i = projectiles.size(); i > 0; i--) {
			Projectile p = projectiles.get(i - 1);
			
			if (p.getTarget() != null) {
				p.targetX = p.getTarget().getX() + p.getTarget().getHalfWidth();
				p.targetY = p.getTarget().getY() + p.getTarget().getHalfHeight();
				p.angle = Math.atan2(p.targetY - p.y, p.targetX - p.x);
				
				boolean lessThanRadiusX = p.x < (parent.getX() + parent.getHalfWidth()) - getAttackRadius();
				boolean greaterThanRadiusX = p.x > (parent.getX() + parent.getHalfWidth()) + getAttackRadius();
				boolean lessThanRadiusY = p.y < (parent.getY() + parent.getHalfHeight()) - getAttackRadius();
				boolean greaterThanRadiusY = p.y > (parent.getY() + parent.getHalfHeight()) + getAttackRadius();
				
				double velX = (Math.cos(p.angle) * Math.PI / 180) * (p.speed * gameTime);
				double velY = (Math.sin(p.angle) * Math.PI / 180) * (p.speed * gameTime);
				
				if (lessThanRadiusX || greaterThanRadiusX || lessThanRadiusY || greaterThanRadiusY) {
					if (projectiles.contains(p)) {
						projectiles.remove(p);
					}
				}
				else {
					p.x += velX;
					p.y += velY;
					
					//Hits unit
					if(p.distanceToTarget() <= 10){
						if (projectiles.contains(p)) {
							projectiles.remove(p);
							if (aoe != 0) {
								explode(p);
							}
							else {
								if (damage != 0) 
									p.getTarget().inflictDamage(damage);
								
								if (slow != 0 && slowTime != 0) 
									p.getTarget().applySlow(slow, slowTime);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
	}
	
	@Override
	public double getFiringAngle() {
		if (!projectiles.isEmpty())
			return projectiles.get(0).angle;
		else
			return 0;
	}
	
	private void explode(Projectile p){			
		for(Unit unit : Game.instance().getUnitManager().getUnitList()) {
			if(p.distanceToUnit(unit) <= aoe) {
				unit.inflictDamage(damage);
			}
		}
	}
	
	public int getProjectileRadius() { return projectileRadius; }
	public void setProjectileRadius(int radius) { this.projectileRadius = radius; }
	public int getDamage() { return damage; }
	public void setDamage(int damage) {	this.damage = damage; }
	public int getAoe() { return aoe; }
	public void setAoe(int aoe) { this.aoe = aoe; }
	public float getSlow() { return slow; }
	public void setSlow(float slow) { this.slow = slow; }
	public long getSlowTime() { return slowTime; }
	public void setSlowTime(long slowTime) { this.slowTime = slowTime; }
	public int getSpeed() { return speed; }
	public void setSpeed(int speed) { this.speed = speed; }
	public boolean getAntiAir() { return this.antiAir; }
	public void setAntiAir(boolean antiAir) { this.antiAir = antiAir; }
	
}
