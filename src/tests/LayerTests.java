package tests;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import game.levelSystems.Layer;

public class LayerTests 
{	
	private BufferedImage image;
	private ArrayList<BufferedImage> mTiles;
	
	@Before
	public void init()
	{
		mTiles = new ArrayList<BufferedImage>();
		
		try {
			image = ImageIO.read(new File("images/monsters.png"));
			mTiles.add(image);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void layerCreated()
	{
		Layer layer = new Layer(mTiles, 0, 0, 1, 1);
		assertEquals(1, layer.getMapWidth());
	}
	
	@Test
	public void layerDimensions()
	{
		Layer layer = new Layer(mTiles, 0, 0, 1, 1);	
		assertEquals(1, layer.getMapHeight());
	}
	
	@Test
	public void layerLocation()
	{
		Layer layer = new Layer(mTiles, 0, 0, 1, 1);	
		assertEquals(0, layer.getX());
	}
	
}
