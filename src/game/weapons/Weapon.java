package game.weapons;

import game.entities.Unit;

public abstract class Weapon {

	private float attackRadius;
	private float range;
	
	public Weapon() {
	}
	
	public abstract void fire(Unit unit);

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public float getAttackRadius() {
		return attackRadius;
	}

	public void setAttackRadius(float attackRadius) {
		this.attackRadius = attackRadius;
	}
	
}
