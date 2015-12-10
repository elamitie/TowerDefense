package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.entities.Unit;

public class PineNeedle extends ProjectileBasedWeapon {
	
	public static final int ATTACK_RADIUS = 200;
	public static final int RATE_OF_FIRE = 2;
	public static final int DAMAGE = 20;
	public static final int PROJECTILE_WIDTH = 10;
	public static final int PROJECTILE_HEIGHT = 5;

	public PineNeedle() {
		super();
		this.setAttackRadius(ATTACK_RADIUS);
		this.setRateOfFire(RATE_OF_FIRE);
		this.setDamage(DAMAGE);
		this.setAntiAir(true);
		this.setCost(150);
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
		Graphics2D gg = (Graphics2D)g2d.create();
		Rectangle rect = new Rectangle();
		rect.width = PROJECTILE_WIDTH;
		rect.height = PROJECTILE_HEIGHT;
		
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
