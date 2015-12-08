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
	private Sound mDeathNoise;
	
	public UnitManager(){
		createWaypoints();
		setUnitList(new ArrayList<Unit>());
		mExitNoise = new Sound("music/yoink.wav");
		mDeathNoise = new Sound("music/beastDead.wav");
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Unit> unitList) {
		this.unitList = unitList;
	}
	
	public void addUnit(Unit unit) {
		unitList.add(unit);
		
		//If unit is supposed to fly give it the flyers path
		if(unit.isCanFly()) {
			unit.setCurrentWaypoint(flyingWaypoint);
		}
		else {
			unit.setCurrentWaypoint(waypoints.get(0));
		}
		
	}
	
	public void removeUnit(Unit unit) {
		unitList.remove(unit);
	}
	
	public void update(long gameTime) {
		for(Unit unit : unitList){
			unit.update(gameTime);
		}
		
		for(int i = unitList.size(); i > 0; i--){
			//Unit dead
			if(unitList.get(i - 1).getCurrentHealth() <= 0){
				mDeathNoise.playSound(false);
				unitList.remove(i - 1);
			}
			//Unit reached end
			else if(unitList.get(i - 1).getCurrentWaypoint() == null){
				unitList.remove(i - 1);
				mExitNoise.playSound(false);
			}
		}
	}
	
	public void draw(Graphics2D g2d) {
		for(Unit unit : unitList) {
			unit.draw(g2d);
		}
	}
	
	private void createWaypoints() {
		//Linked list style waypoint system
		waypoints = new ArrayList<Waypoint>();
		waypoints.add(new Waypoint(150, Direction.Up));
		waypoints.add(new Waypoint(150, Direction.Up));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(250, Direction.Right));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(250, Direction.Down));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(600, Direction.Right));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(375, Direction.Down));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(700, Direction.Right));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(50, Direction.Up));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		waypoints.add(new Waypoint(955, Direction.Right));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		
		flyingWaypoint = new Waypoint(955, Direction.Right);
	}
	
}
