package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entities.Unit;

public class Kernel extends ProjectileBasedWeapon {
	
	public Kernel() {
		super();
		this.setAttackRadius(200);
		this.setRateOfFire(2);
		this.setDamage(10);
		this.setProjectileRadius(4);
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
			g2d.setColor(Color.yellow);
			g2d.fillOval(p.x - getProjectileRadius(), p.y - getProjectileRadius(), getProjectileRadius() * 2, getProjectileRadius() * 2);
			g2d.setColor(color);
		}
	}
	
}
