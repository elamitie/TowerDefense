package game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.entities.Tower;
import game.entities.TowerManager;
import game.entities.Unit;
import game.entities.UnitManager;
import game.entities.WaveScroller;
import game.graphics.Canvas;
import game.levelSystems.LevelLayout;
import game.utilities.JSONReader;
import game.utilities.Sound;

@SuppressWarnings("serial")
public class Game extends Canvas
{
	private JSONReader mFileReader;
	private Sound mBackgroundSound;
	private ArrayList<LevelLayout> mLevels;
	
	public static final long secInNanosec = 1000000000L;
    public static final long milisecInNanosec = 1000000L;
    public static final int walkingStartPoint = 375;
    public static final int flyingStartPoint = 270;
    public static final int unitCount = 7;
    private int mTimeBetweenSpawns = 500;
    private int mTimeBetweenGroups = 1000;
    private int mTimeBetween = 0;
	
    private int mFPS = 60;
    private long mUpdatePeriod = secInNanosec / mFPS;	
	private long mGameTime;
	private int mWave;
	private int mEnemiesSpawned;
	private boolean isSpawning;
	
	private WaveScroller mScroller;
	private UnitManager mUnitManager;
	private TowerManager mTowerManager;
	
	private Boolean mIsRunning;
	
	public Game()
	{
	    // Sets variables and objects for the game.
	    init();
	    // Load game files (images, sounds, ...)
	    loadContent();
	    
	    Thread gameThread = new Thread() 
	      {
	          @Override
	          public void run()
	          {
	        	  gameLoop();
	          }
	      };
	      gameThread.start();
	}
	
	private void init()
	{	  
		mFileReader = new JSONReader();
		mUnitManager = new UnitManager();
		mTowerManager = new TowerManager();
		
		mBackgroundSound = new Sound("music/music.wav");
		mBackgroundSound.startMusic();
		
		mGameTime = (long) 0;
		
		mEnemiesSpawned = 0;
		mWave = 1;
		
		mIsRunning = true;
		isSpawning = false;
	  }
	
	public boolean getIsRunning()
	{
		return mIsRunning;
	}
	  
	
	private void loadContent()
	{
		  mFileReader.loadFiles();
		  
		  mLevels = mFileReader.readLevelInfo();
		  
		  mScroller = new WaveScroller();
		  
		  mTowerManager.add(new Tower(197, 260, 50, 5, mFileReader.readTowerInfo("kernel")), "right");
		  mTowerManager.add(new Tower(197, 200, 50, 5, mFileReader.readTowerInfo("kernel")), "up");
	}    
	  
	  
	public void update(long gameTime)
	{
	      mUnitManager.update(gameTime);
	      mScroller.updateSpawner(gameTime, mWave);
	      mTowerManager.update(gameTime);
	      
	      if(!isSpawning)
	      {
	    	  isSpawning = mScroller.startNewWave();
	      }
	      spawnWave(gameTime);
	}

	public void spawnWave(long gameTime){
		if(isSpawning){
			mTimeBetween -= gameTime;
			
			if(mTimeBetween <= 0){
				if(mEnemiesSpawned < mWave * unitCount){
					spawnNextUnit();
					mEnemiesSpawned++;
				}
				else{
					isSpawning = false;
					mWave++;
					mEnemiesSpawned = 0;
				}
			}
		}
	}
	
	private void spawnNextUnit(){
		
		Unit unit;
		String unitType = "scorption";
		Boolean canFly = false;
		
		switch(mEnemiesSpawned / mWave){
		case 0:
			unitType = "bee";
			canFly = true;
			break;
		case 1:
			unitType = "bird";
			canFly = true;
			break;
		case 2:
			unitType = "scorption";
			canFly = false;
			break;
		case 3:
			unitType = "wereWolf";
			canFly = false;
			break;
		case 4:
			unitType = "wolf";
			canFly = false;
			break;
		case 5:
			unitType = "flower";
			canFly = false;
			break;
		case 6:
			unitType = "mouse";
			canFly = false;
			break;
		}
		
		if(mEnemiesSpawned % mWave == mWave - 1){
			mTimeBetween = mTimeBetweenGroups;
		}
		else{
			mTimeBetween = mTimeBetweenSpawns;
		}
		
		if(canFly){
			unit = new Unit(-50, flyingStartPoint, mFileReader.readMonstersInfo(unitType));
		}
		else{
			unit = new Unit(-50, walkingStartPoint, mFileReader.readMonstersInfo(unitType));
		}
		unit.setCanFly(canFly);
		mUnitManager.addUnit(unit);
	}
	
	@Override
	public void draw(Graphics2D g2d) 
	{
		mLevels.get(0).draw(g2d);
		mTowerManager.draw(g2d);
		mUnitManager.draw(g2d);
		mScroller.draw(g2d);
	}
	
	public void gameLoop()
	{
		long beginTime, timeTaken, timeLeft;
		long lastTime = System.nanoTime();
		
		while(mIsRunning)
		{
			beginTime = System.nanoTime();
			
			mGameTime = (System.nanoTime() - lastTime) / milisecInNanosec;
			update(mGameTime);
			lastTime = System.nanoTime();

			repaint();
			
			timeTaken = System.nanoTime() - beginTime;
	        timeLeft = (mUpdatePeriod - timeTaken) / milisecInNanosec;
			
	        if (timeLeft < 10) 
	            timeLeft = 10; //set a minimum
	        try {
	             //Provides the necessary delay and also yields control so that other thread can do work.
	             Thread.sleep(timeLeft);
	        } catch (InterruptedException ex) { }
		}
        
	}

}

