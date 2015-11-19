package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entities.Unit;

public class PineCone extends ProjectileBasedWeapon {
	
	public PineCone() {
		super();
		this.setAttackRadius(200);
		this.setRateOfFire(8);
		this.setDamage(40);
		this.setProjectileRadius(13);
		this.setAoe(100);
		this.setSpeed(25);
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
			Color brown = new Color(139, 69, 19);
			g2d.setColor(brown);
			g2d.fillOval(p.x - getProjectileRadius(), p.y - getProjectileRadius(), getProjectileRadius() * 2, getProjectileRadius() * 2);
			g2d.setColor(color);
		}
	}
	
}
