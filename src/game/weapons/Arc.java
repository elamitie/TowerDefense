package game.weapons;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entities.Unit;

public class Arc {

	private Unit mFrom;
	private Unit mTo;
	
	public Arc(Unit from, Unit to){
		mFrom = from;
		mTo = to;
	}
	
	public void draw(Graphics2D g2d){
		
		g2d.setColor(Color.yellow);
		g2d.drawLine(mFrom.getX(), mFrom.getY(), mTo.getX(), mTo.getY());
		
	}
	
	//from last unit in arc
	public float distanceToUnit(Unit unit){
		return (float)Math.pow((Math.pow(unit.getY() + unit.getHalfHeight() - mTo.getY(), 2) + Math.pow(unit.getX() + unit.getHalfWidth() - mTo.getX(), 2)),.5f);
	}
	
	public Unit getArcTo(){ return mTo; }

	
}
