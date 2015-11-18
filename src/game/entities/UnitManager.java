package game.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.utilities.Sound;
import game.utilities.Waypoint;

public class UnitManager {

	private List<Unit> unitList;
	private List<Waypoint> waypoints;
	private Waypoint flyingWaypoint;
	private Sound mExitNoise;
	
	public UnitManager(){
		createWaypoints();
		setUnitList(new ArrayList<Unit>());
		mExitNoise = new Sound("music/yoink.wav");
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Unit> unitList) {
		this.unitList = unitList;
	}
	
	public void addUnit(Unit unit){
		unitList.add(unit);
		
		//If unit is supposed to fly give it the flyers path
		if(unit.isCanFly()){
			unit.setCurrentWaypoint(flyingWaypoint);
		}
		else{
			unit.setCurrentWaypoint(waypoints.get(0));
		}
		
	}
	
	public void removeUnit(Unit unit){
		unitList.remove(unit);
	}
	
	public void update(long gameTime){
		for(Unit unit : unitList){
			unit.update(gameTime);
		}
		
		for(int i = unitList.size(); i > 0; i--){
			//Unit dead
			if(unitList.get(i - 1).getmCurrentHealth() <= 0){
				unitList.remove(i - 1);
			}
			
			//Unit reached end
			if(unitList.get(i - 1).getCurrentWaypoint() == null){
				unitList.remove(i - 1);
				mExitNoise.playSound();
			}
		}
	}
	
	public void draw(Graphics2D g2d){
		for(Unit unit : unitList){
			unit.draw(g2d);
		}
	}
	
	private void createWaypoints(){
		
		//Linked list style waypoint system
		waypoints = new ArrayList<Waypoint>();
		waypoints.add(new Waypoint(150,"right"));
		waypoints.add(new Waypoint(150,"up"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(250,"right"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(250,"down"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(600,"right"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(375,"down"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(700,"right"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(50,"up"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(955,"right"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		
		flyingWaypoint = new Waypoint(955,"right");
	}
	
}
