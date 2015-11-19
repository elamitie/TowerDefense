package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.entities.Unit;

public class Lightning extends Weapon {
	
	public static final int arcRange = 150;
	public static final int maxArcs = 5;
	
	private List<Arc> arcs;
	private List<Unit> hitList;
	private int damage;
	
	public Lightning() {
		arcs = new ArrayList<Arc>();
		hitList = new ArrayList<Unit>();
		this.setAttackRadius(200);
		this.setRateOfFire(10);
		
		damage = 10;
	}

	@Override
	public void fire(Unit unit) {
		/*
		hitList.add(unit);
		
		//First arc
		for(Unit potentialArc : Game.instance().getUnitManager().getUnitList()) {
			if(arcs.get(arcs.size() - 1).distanceToUnit(unit) <= arcRange) {
				unit.inflictDamage(damage);
				arcs.add(new Arc(hitList.get(0), potentialArc));
				hitList.add(potentialArc);
				
			}
		}
		
		//Latter arcs
		for(int i = 0; i < maxArcs - 1; i++){
			for(Unit potentialArc : Game.instance().getUnitManager().getUnitList()) {
				//Make sure unit was not already arced to and is in range
				if(!hitList.contains(potentialArc) && arcs.get(arcs.size() - 1).distanceToUnit(unit) <= arcRange) {
					unit.inflictDamage(damage);
					arcs.add(new Arc(arcs.get(arcs.size() - 1).getArcTo(), potentialArc));
					hitList.add(potentialArc);
				}
			}
		}
		*/
	}
	
	@Override
	public void update(long gameTime) {
		
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		/*
		g2d.setColor(Color.yellow);
		
		if(!hitList.isEmpty()){
			g2d.drawLine(parent.getX(),parent.getY(),hitList.get(0).getX(), hitList.get(0).getY());
		}
					
		
		for (Arc arc : arcs) {
			arc.draw(g2d);
		}
		*/
	}
	
}
