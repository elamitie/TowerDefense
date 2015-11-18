package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import game.graphics.Animation;
import game.levelSystems.LevelLayout;
import game.utilities.JSONReader;

public class JSONReaderTests 
{
	JSONReader reader;
	
	@Before
	public void init()
	{
		reader = new JSONReader();
		reader.loadFiles();
	}
	
	@Test
	public void readMouseInfo()
	{
		ArrayList<Animation> animations = reader.readMonstersInfo("bee");
		
		assertEquals(3, animations.get(0).getNumFrames());
	}
	
	@Test
	public void readTileInfo()
	{
		ArrayList<LevelLayout> levels = reader.readLevelInfo();
		assertEquals(1, levels.size());
	}

}
