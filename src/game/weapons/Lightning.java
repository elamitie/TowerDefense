package game.weapons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.entities.Unit;

public class Lightning extends Weapon {
	
	public static final int ARC_RANGE = 200;
	public static final int MAX_ARCS = 5;
	public static final int LIGHTNING_WIDTH = 2;
	public static final long MAX_LIGHTNING_TIMER = 750;
	public static final int ATTACK_RADIUS = 200;
	public static final int RATE_OF_FIRE = 10;
	public static final int DAMAGE = 15;
	
	private List<Unit> hitList;
	private long lightningTimer;
	
	public Lightning() {
		
		hitList = new ArrayList<Unit>();
		this.setAttackRadius(ATTACK_RADIUS);
		this.setRateOfFire(RATE_OF_FIRE);
		
		this.setDamage(DAMAGE);
		this.setCost(200);
		this.setSellAmount(getCost() / 2);
		lightningTimer = 0;
	}

	@Override
	public void fire(Unit unit) {
		
		lightningTimer = MAX_LIGHTNING_TIMER;
		
		hitList.clear();
		
		hitList.add(unit);
		unit.inflictDamage(getDamage());
		
		for(int i = 0; i < MAX_ARCS; i++){
			for(Unit potentialArc : Game.instance().getUnitManager().getUnitList()){
				if(!hitList.contains(potentialArc) && rangeCheck(potentialArc)){
					hitList.add(potentialArc);
					potentialArc.inflictDamage(getDamage());
				}
			}
		}
		
	}
	
	@Override
	public void update(long gameTime) {
		if(lightningTimer > 0){
			lightningTimer -= gameTime;
			
			if(lightningTimer <= 0){
				hitList.clear();
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		validateHitList();
		
		Stroke prevStroke = g2d.getStroke();
		Color prevColor = g2d.getColor();
		
		g2d.setStroke(new BasicStroke(LIGHTNING_WIDTH));
		g2d.setColor(Color.yellow);
		
		if(!hitList.isEmpty()){
			
			int x1 = parent.getX() + parent.getHalfWidth();
			int y1 = parent.getY() + parent.getHalfHeight();
			int x2 = hitList.get(0).getX() + hitList.get(0).getHalfWidth();
			int y2 = hitList.get(0).getY() + hitList.get(0).getHalfHeight();
			
			g2d.drawLine(x1,y1,x2, y2);
			
			for(int i = 1; i < hitList.size(); i++){
				
				x1 = hitList.get(i - 1).getX() + hitList.get(i - 1).getHalfWidth();
				y1 = hitList.get(i - 1).getY() + hitList.get(i - 1).getHalfHeight();
				x2 = hitList.get(i).getX() + hitList.get(i).getHalfWidth();
				y2 = hitList.get(i).getY() + hitList.get(i).getHalfHeight();
				
				g2d.drawLine(x1,y1,x2, y2);
			}
			
		}
		
		g2d.setStroke(prevStroke);
		g2d.setColor(prevColor);		
	}
	
	
	private void validateHitList(){
		for(int i = hitList.size(); i > 0; i--){
			if(!Game.instance().getUnitManager().getUnitList().contains(hitList.get(i - 1))){
				hitList.remove(i - 1);
			}
		}
	}
	
	//Checks range from last arc to new arc
	private boolean rangeCheck(Unit unit){
		
		return ((float)Math.pow((Math.pow(unit.getY() + unit.getHalfHeight() - hitList.get(hitList.size() - 1).getY(), 2) + Math.pow(unit.getX() + unit.getHalfWidth() - hitList.get(hitList.size() - 1).getX(), 2)),.5f) <= ARC_RANGE);
		
	}

	@Override
	public double getFiringAngle() {
		return 0;
	}
}
