package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entities.Unit;

public class Water extends ProjectileBasedWeapon {
	
	public static final int ATTACK_RADIUS = 200;
	public static final int RATE_OF_FIRE = 6;
	public static final int DAMAGE = 0;
	public static final int PROJECTILE_RADIUS = 6;
	public static final int AOE = 0;
	public static final float SLOW = 1.0f;
	public static final int SLOW_TIME = 3000;
	
	public Water() {
		super();
		this.setAttackRadius(ATTACK_RADIUS);
		this.setRateOfFire(RATE_OF_FIRE);
		this.setDamage(DAMAGE);
		this.setProjectileRadius(PROJECTILE_RADIUS);
		this.setAoe(AOE);
		this.setSlow(SLOW);
		this.setSlowTime(SLOW_TIME);
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
			g2d.setColor(Color.blue);
			g2d.fillOval(p.x - getProjectileRadius(), p.y - getProjectileRadius(), getProjectileRadius() * 2, getProjectileRadius() * 2);
			g2d.setColor(color);
		}
	}
	
}
