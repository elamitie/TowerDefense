package game.weapons;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.entities.Unit;

public class Water extends Weapon {
	
	public static final long slowTime = 3000;
	public static final float slowSpeed = 1.0f;
	
	private List<Projectile> projectiles;
	private int projectileRadius;
	private int damage;
	
	public Water() {
		projectiles = new ArrayList<Projectile>();
		this.setAttackRadius(200);
		this.setRateOfFire(6);
		
		damage = 20;
		projectileRadius = 4;
	}

	@Override
	public void fire(Unit unit) {
		Projectile p = new Projectile(parent.getX() + parent.getHalfWidth(), parent.getY() + parent.getHalfHeight(), 15);
		p.targetX = unit.getX() + unit.getHalfWidth();
		p.targetY = unit.getY() + unit.getHalfHeight();
		p.setTarget(unit);
		
		p.angle = Math.atan2(p.targetY - p.y, p.targetX - p.x);
		Math.toDegrees(p.angle);
		projectiles.add(p);
	}
	
	@Override
	public void update(long gameTime) {
		for (int i = projectiles.size(); i > 0; i--) {
			Projectile p = projectiles.get(i - 1);
			
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
						p.getTarget().applySlow(slowSpeed, slowTime);
						p.getTarget().inflictDamage(damage);
					}
				}
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		for (Projectile p : projectiles) {
			g2d.fillOval(p.x - projectileRadius, p.y - projectileRadius, projectileRadius * 2, projectileRadius * 2);
		}
	}
	
}
