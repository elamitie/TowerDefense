package game.graphics;

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
