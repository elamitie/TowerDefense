package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.entities.Unit;

public class PineNeedle extends ProjectileBasedWeapon {
	
	private int projectileWidth;
	private int projectileHeight;

	public PineNeedle() {
		super();
		this.setAttackRadius(200);
		this.setRateOfFire(2);
		this.setDamage(20);
		
		projectileWidth = 10;
		projectileHeight = 5;
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
