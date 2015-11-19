package game.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation 
{
	public static final int TIME_PER_FRAME = 100;
	
	private ArrayList<BufferedImage> mFrames;
	private long mTimeUntilNextFrame;
	private int mCurrentFrame;
	
	public Animation(ArrayList<BufferedImage> frames)
	{
		mCurrentFrame = 0;
		mFrames = frames;
		mTimeUntilNextFrame = TIME_PER_FRAME;
	}
	
	public BufferedImage getCurrentFrame()
	{
		return mFrames.get(mCurrentFrame);
	}
	
	public BufferedImage getCurrentFrameSlowed()
	{
		int width = mFrames.get(mCurrentFrame).getWidth(null);
	    int height = mFrames.get(mCurrentFrame).getHeight(null);
	    BufferedImage tinted = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D graphics = (Graphics2D) tinted.getGraphics();
	    graphics.drawImage(mFrames.get(mCurrentFrame), 0, 0, width, height, null);
	    Color c = new Color(0,0,(int)(200),128);
	    Color n = new Color(0,0,0,0);
	    BufferedImage tint = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    for(int i = 0 ; i < width ; i++){
	        for(int j = 0 ; j < height ; j++){
	            if(tinted.getRGB(i, j) != n.getRGB()){
	                tint.setRGB(i, j, c.getRGB());
	            }
	        }
	    }
	    graphics.drawImage(tint, 0, 0, null);
	    graphics.dispose();
	    return tinted;
	}
	
	public int getNumFrames()
	{
		return mFrames.size();
	}
	
	public float getTimePerFrame()
	{
		return TIME_PER_FRAME;
	}
	
	public void update(long gameTime)
	{
		mTimeUntilNextFrame -= gameTime;
		
		if (mTimeUntilNextFrame <= 0)
		{
			mCurrentFrame++;
			if (mCurrentFrame >= mFrames.size())
			{
				mCurrentFrame = 0;
			}
			
			mTimeUntilNextFrame = TIME_PER_FRAME;
		}
	}
}
