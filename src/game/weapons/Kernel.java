package game.weapons;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.entities.Tower;
import game.entities.Unit;

public class Kernel extends Weapon {
	
	private List<Projectile> projectiles;
	private int projectileRadius;
	private int damage;
	
	public Kernel(Tower tower) {
		super(tower);
		
		projectiles = new ArrayList<Projectile>();
		this.setAttackRadius(200);
		this.setRateOfFire(2);
		
		damage = 20;
		projectileRadius = 4;
	}

	@Override
	public void fire(Unit unit) {
		Projectile p = new Projectile(parent.getX() + parent.getHalfWidth(), parent.getY() + parent.getHalfHeight(), 50);
		p.targetX = unit.getX();
		p.targetY = unit.getY();
		
		p.angle = Math.atan2(p.targetY - p.y, p.targetX - p.x);
		Math.toDegrees(p.angle);
		projectiles.add(p);
	}
	
	@Override
	public void update(long gameTime) {
		for (int i = projectiles.size(); i > 0; i--) {
			Projectile p = projectiles.get(i - 1);
			
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
