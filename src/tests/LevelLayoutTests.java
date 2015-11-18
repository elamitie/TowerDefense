package tests;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.junit.*;

import game.levelSystems.Layer;
import game.levelSystems.LevelLayout;


public class LevelLayoutTests 
{
	BufferedImage image;
	LevelLayout layout;
	Layer layer;
	ArrayList<BufferedImage> list;
	
	@Before
	public void init()
	{
		list = new ArrayList<BufferedImage>();
		try 
		{
			image = ImageIO.read(new File("images/monsters.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		list.add(image);
		
		layer = new Layer(list, 1, 2, 3, 4);
		
		layout = new LevelLayout();
	}
	
	@Test
	public void testLayerAdd()
	{
		layout.addLayer(layer, 0);
		
		assertEquals(1, layout.getNumLayers());
	}
	
	@Test
	public void testLayers()
	{
		layout.addLayer(layer, 0);
		
		assertSame(layout.getLayer(0), layer);
	}
	
}
