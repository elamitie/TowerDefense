package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import game.entities.Direction;
import game.entities.Tower;
import game.entities.TowerManager;
import game.utilities.JSONReader;
import game.weapons.Kernel;
import game.weapons.PineNeedle;
import game.weapons.Water;

public class TowerWeaponTests {
	
	private Tower tower;
	private JSONReader reader;
	
	@Before
	public void init() {
		reader = new JSONReader();
		reader.loadFiles();
		
		tower = new Tower(0, 0, reader.readTowerInfo("kernel"), new Kernel());
		tower.setAnimation(Direction.Up);
	}
	
	@Test
	public void testAdding() {
		TowerManager towerManager = new TowerManager();
		towerManager.add(tower);
		towerManager.add(tower);
		assertEquals(2, towerManager.getAll().size());
	}
	
	@Test
	public void testDeleting() {
		TowerManager towerManager = new TowerManager();
		towerManager.add(tower);
		towerManager.remove(tower);
		assertEquals(0, towerManager.getAll().size());
	}
	
	@Test
	public void validateTowerX() {
		assertEquals(0, tower.getX());
	}

	@Test
	public void validateTowerY() {
		assertEquals(0, tower.getY());
	}
	
	@Test
	public void validateKernelProperties() {
		Kernel kernel = new Kernel();
		Tower tower = new Tower(0, 0, reader.readTowerInfo("kernel"), kernel);
		tower.setAnimation(Direction.Up);
		
		assertEquals(200, kernel.getAttackRadius());
		assertEquals(1, kernel.getRateOfFire());
		assertEquals(10, kernel.getDamage());
		assertEquals(4, kernel.getProjectileRadius());
	}

	@Test
	public void validateNeedleProperties() {
		PineNeedle needle = new PineNeedle();
		Tower tower = new Tower(0, 0, reader.readTowerInfo("needle"), needle);
		tower.setAnimation(Direction.Up);
		
		assertEquals(200, needle.getAttackRadius());
		assertEquals(2, needle.getRateOfFire());
		assertEquals(20, needle.getDamage());
		assertEquals(true, needle.getAntiAir());
	}
	
	@Test
	public void validateWaterProperties() {
		Water water = new Water();
		Tower tower = new Tower(0, 0, reader.readTowerInfo("water"), water);
		tower.setAnimation(Direction.Up);
		
		assertEquals(200, water.getAttackRadius());
		assertEquals(6, water.getRateOfFire());
		assertEquals(0, water.getDamage());
		assertEquals(3000, water.getSlowTime());
		assertEquals(1.0f, water.getSlow(), 0.1);
	}
}
