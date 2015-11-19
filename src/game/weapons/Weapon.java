package game.weapons;

import game.entities.Unit;

public abstract class Weapon {

	private float attackRadius;
	private float range;
	
	public Weapon(float attackRadius, float range) {
		this.attackRadius = attackRadius;
		this.range = range;
	}
	
	public abstract void fire(Unit unit);
	
}
