package game.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class TowerManager {

	private List<Tower> towers;
	
	public TowerManager() {
		towers = new ArrayList<Tower>();
	}
	
	public void update(long gameTime) {
		for (Tower tower : towers) {
			tower.update(gameTime);
		}
	}
	
	public void draw(Graphics2D g2d) {
		for (Tower tower : towers) {
			tower.draw(g2d);
		}
	}
	
	public Tower add(Tower tower) {
		Tower t = tower;
		towers.add(t);
		return t;
	}
	
	public Tower add(Tower tower, Direction direction) {
		Tower t = tower;
		t.setAnimation(direction);
		towers.add(t);
		return t;
	}
	
	public Tower remove(Tower tower){
		towers.remove(tower);
		return tower;
	}
	
	public Tower get(int i) {
		return towers.get(i);
	}
	
	public List<Tower> getAll() {
		return towers;
	}
	
}
