package game.utilities;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound 
{
	private String mSoundFile;
	private AudioInputStream mAudioIn;
	private Clip mClip = null;
	
	public Sound(String soundFile)
	{
		mSoundFile = soundFile;
	}
	
	public String getSoundFile()
	{
		return mSoundFile;
	}
	
	public void playSound(boolean loops)
	{
		File soundFile = new File(mSoundFile);
		
		try
		{
			mAudioIn = AudioSystem.getAudioInputStream(soundFile);
			
		} catch (UnsupportedAudioFileException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			mClip = AudioSystem.getClip();
		} catch (LineUnavailableException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			mClip.open(mAudioIn);
		} catch (LineUnavailableException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if(loops){
			mClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		else{
			mClip.start();
		}
	}
}
