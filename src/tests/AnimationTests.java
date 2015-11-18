package tests;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import game.graphics.Animation;

public class AnimationTests 
{	
	private BufferedImage image;
	private ArrayList<BufferedImage> images;
	
	@Before
	public void init()
	{
		try {
			image = ImageIO.read(new File("images/mouses.png"));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

		images = new ArrayList<BufferedImage>();
		images.add(image);
	}
	
	@Test
	public void animaionCreated()
	{
		Animation anim = new Animation(images);
		assertEquals(1, anim.getNumFrames());
	}
	
	@Test
	public void animaionTimePerFrame()
	{
		Animation anim = new Animation(images);
		assertEquals(100, anim.getTimePerFrame(), 0.01);
	}

}
