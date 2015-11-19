package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import game.entities.Unit;

public class PineNeedle extends Weapon {
	
	private List<Projectile> projectiles;
	//private int projectileRadius;
	private int projectileWidth;
	private int projectileHeight;
	private int projectileSpeed;
	private int damage;

	public PineNeedle() {
		projectiles = new ArrayList<Projectile>();
		this.setAttackRadius(200);
		this.setRateOfFire(2);
		
		damage = 20;
		//projectileRadius = 5;
		projectileWidth = 10;
		projectileHeight = 5;
		projectileSpeed = 50;
	}

	@Override
	public void fire(Unit unit) {
		Projectile p = new Projectile(parent.getX() + parent.getHalfWidth(), parent.getY() + parent.getHalfHeight(), projectileSpeed);
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
						p.getTarget().inflictDamage(damage);
					}
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		Graphics2D gg = (Graphics2D)g2d.create();
		Rectangle rect = new Rectangle();
		rect.width = projectileWidth;
		rect.height = projectileHeight;
		
		for (Projectile p : projectiles) {
			rect.x = p.x;
			rect.y = p.y;
			
			Color previous = gg.getColor();
			gg.setColor(Color.green);
			gg.rotate(p.angle, rect.x + rect.width / 2, rect.y + rect.height / 2);
			gg.fillRect(rect.x, rect.y, rect.width, rect.height);
			gg.setColor(previous);
			gg.dispose();
		}
	}
	
}
