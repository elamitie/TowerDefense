package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.entities.Tower;
import game.utilities.Hud;
import game.utilities.JSONReader;
import game.weapons.Kernel;

public class HudTests {

	private Game game;
	private Hud hud;
	
	
	@Before
	public void init()
	{
		game = Game.instance();
		hud = game.getHud();
	}
	
	@Test
	public void hudInitTest(){
		assertTrue(hud.getTower() == null && hud.getCostData().equals(" ") && hud.getDamageData().equals(" "));
	}
	
	@Test
	public void hudTowerTest(){
		
		JSONReader mReader = new JSONReader();
		mReader.loadFiles();
		Tower tower = new Tower(0, 0, mReader.readTowerInfo("kernel"), new Kernel());
		hud.updateStats(tower);
		
		assertTrue(hud.getTower() == tower && hud.getDamageData().equals(String.valueOf(tower.getWeapon().getDamage())));
	}
	
	@Test
	public void defaultResetTest(){
		JSONReader mReader = new JSONReader();
		mReader.loadFiles();
		Tower tower = new Tower(0, 0, mReader.readTowerInfo("kernel"), new Kernel());
		hud.updateStats(tower);
		hud.setDefaults();
		
		assertTrue(hud.getTower() == null && hud.getCostData().equals(" ") && hud.getDamageData().equals(" "));
	}
	
	@Test
	public void upgradeTest(){
		JSONReader mReader = new JSONReader();
		mReader.loadFiles();
		Tower tower = new Tower(0, 0, mReader.readTowerInfo("kernel"), new Kernel());
		int damage = tower.getWeapon().getDamage();
		tower.getWeapon().upgrade();
		
		assertTrue(tower.getWeapon().getDamage() > damage);
	}
	
	@Test
	public void upgradeUpdateTest(){
		JSONReader mReader = new JSONReader();
		mReader.loadFiles();
		Tower tower = new Tower(0, 0, mReader.readTowerInfo("kernel"), new Kernel());
		int damage = tower.getWeapon().getDamage();
		hud.updateStats(tower);
		tower.getWeapon().upgrade();
		hud.updateStats(tower);
		
		assertTrue(!hud.getDamageData().equals(String.valueOf(damage)) && hud.getDamageData().equals(String.valueOf(tower.getWeapon().getDamage())));
	}
	
	
}
