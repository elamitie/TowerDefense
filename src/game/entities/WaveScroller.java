package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WaveScroller
{
	private BufferedImage scrollerBg = null;
	private BufferedImage scrollerOverlay = null;
	private BufferedImage scrollerIcon = null;
	private float x = 0;
	private Boolean newWave = false;
	private int scrollTimer;
	private boolean canSpawn = true;
	
	public WaveScroller()
	{
		scrollTimer = 0;
		
		try {
			scrollerBg = ImageIO.read(new File("images/scroller-bg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			scrollerOverlay = ImageIO.read(new File("images/scroller-overlay.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			scrollerIcon = ImageIO.read(new File("images/scroller-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scrollTimer = (int) (1 * .5 + 6) * 100;
	}
	
	public void updateSpawner(long gameTime, int wave)
	{
		//wavecount * .5 + unitCount + x
		newWave = false;
		x += 865/(float)scrollTimer;
		
		if (x > 965)
		{
			x = -20;
			canSpawn = true;
		}
		if (x >= 100 && canSpawn)
		{
			newWave = true;
			scrollTimer = (int) ((6 * wave) * .5 + 6) * 100;
			canSpawn = false;
		}
	}
	
	public Boolean startNewWave()
	{
		return newWave;
	}
	
	public void draw(Graphics2D g2d)
	{
		g2d.drawImage(scrollerBg, 0, 465, null);
		g2d.drawImage(scrollerOverlay, 0, 465, null);
		g2d.drawImage(scrollerIcon, (int)x, 473, null);
	}
}