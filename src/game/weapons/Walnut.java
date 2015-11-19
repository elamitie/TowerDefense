package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entities.Unit;

public class Walnut extends ProjectileBasedWeapon {
	
	public Walnut() {
		super();
		this.setAttackRadius(200);
		this.setRateOfFire(5);
		this.setDamage(30);
		this.setProjectileRadius(6);
	}

	@Override
	public void fire(Unit unit) {
		super.fire(unit);
	}
	
	@Override
	public void update(long gameTime) {
		super.update(gameTime);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		for (Projectile p : projectiles) {
			Color color = g2d.getColor();
			g2d.setColor(Color.orange);
			g2d.fillOval(p.x - getProjectileRadius(), p.y - getProjectileRadius(), getProjectileRadius() * 2, getProjectileRadius() * 2);
			g2d.setColor(color);
		}
	}
	
}
