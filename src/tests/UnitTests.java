package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import game.entities.Unit;
import game.entities.UnitManager;
import game.utilities.JSONReader;


public class UnitTests 
{
	Unit mUnit;
	JSONReader mFileReader;
	
	@Before
	public void init()
	{
		mFileReader = new JSONReader();
		mFileReader.loadFiles();
		mUnit = new Unit(300, 100,mFileReader.readMonstersInfo("bee"));
	}
	
	@Test
	public void testX()
	{
		assertEquals(300, mUnit.getX());
	}
	
	@Test
	public void testY()
	{
		assertEquals(100, mUnit.getY());
	}
	
	@Test
	public void testDeletion()
	{
		UnitManager unitManager = new UnitManager();
		unitManager.addUnit(mUnit);
		
		unitManager.removeUnit(mUnit);
		
		assertEquals(0,unitManager.getUnitList().size());
		
	}
	
	@Test
	public void testAdding()
	{
		UnitManager unitManager = new UnitManager();
		unitManager.addUnit(mUnit);
		unitManager.addUnit(mUnit);
		
		assertEquals(2,unitManager.getUnitList().size());
		
	}
}
