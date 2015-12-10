package game.weapons;

import java.awt.Graphics2D;

import game.entities.Tower;
import game.entities.Unit;

public abstract class Weapon {

	public int fireTimer;
	private int rateOfFire;
	private int attackRadius;
	private int damage;
	
	private int cost;
	
	protected Tower parent;
	
	public Weapon() {
	}
	
	public void init(Tower parent) {
		this.fireTimer = 1;
		this.parent = parent;
	}
	
	public abstract void fire(Unit unit);	
	public abstract void update(long gametime);
	public abstract void draw(Graphics2D g2d);
	public abstract double getFiringAngle();
	
	public int getUpgradeValue(){ return cost / 3; } ;

	public void upgrade(){
		damage += 2;
		
		if(rateOfFire > 1)
		{
			rateOfFire--;
		}
		else
			damage += 2;
		
	}
	
	public int getRateOfFire() {
		return rateOfFire;
	}

	public void setRateOfFire(int rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	public int getAttackRadius() {
		return attackRadius;
	}

	public void setAttackRadius(int attackRadius) {
		this.attackRadius = attackRadius;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void reset() {
		fireTimer = rateOfFire * 1000; // millis
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getSellAmount() {
		return cost / 2;
	}

}
