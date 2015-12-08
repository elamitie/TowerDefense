package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.entities.Direction;
import game.entities.Tower;
import game.entities.TowerManager;
import game.entities.Unit;
import game.entities.UnitManager;
import game.entities.WaveScroller;
import game.levelSystems.LevelLayout;
import game.utilities.JSONReader;
import game.utilities.Sound;
import game.weapons.Kernel;
import game.weapons.Lightning;
import game.weapons.PineCone;
import game.weapons.PineNeedle;
import game.weapons.Walnut;
import game.weapons.Water;

@SuppressWarnings("serial")
public class Game extends JPanel
{
	private JSONReader mFileReader;
	private Sound mBackgroundSound;
	
	//Might want more than one level in the future
	private ArrayList<LevelLayout> mLevels;
	
	public static final long SEC_IN_NANOSEC = 1000000000L;
    public static final long MILISEC_IN_NANOSEC = 1000000L;
    public static final int WALKING_START_POINT = 375;
    public static final int FLYING_START_POINT = 270;
    public static final int UNIT_COUNT = 7;
    private int mTimeBetweenSpawns = 500;
    private int mTimeBetweenGroups = 1000;
    private int mTimeBetween = 0;
	
    private int mFPS = 60;
    private long mUpdatePeriod = SEC_IN_NANOSEC / mFPS;	
	private long mGameTime;
	private int mWave;
	private int mEnemiesSpawned;
	private boolean isSpawning;
	
	private WaveScroller mScroller;
	private UnitManager mUnitManager;
	private TowerManager mTowerManager;
	
	private Boolean mIsRunning;
	
	private static Game instance;
	
	public static Game instance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	private Game()
	{
	    // Sets variables and objects for the game.
	    init();
	    // Load game files (images, sounds, ...)
	    loadContent();
	    
	 // We use double buffer to draw on the screen.
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.black);
        
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
		mBackgroundSound.playSound(true);
		
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
		  
		  mTowerManager.add(new Tower(197, 260, mFileReader.readTowerInfo("water"), new Water()), Direction.Up);
		  mTowerManager.add(new Tower(197, 200, mFileReader.readTowerInfo("lightning"), new Lightning()), Direction.Up);
		  mTowerManager.add(new Tower(320, 200, mFileReader.readTowerInfo("cone"), new PineCone()), Direction.Up);
		  mTowerManager.add(new Tower(520, 320, mFileReader.readTowerInfo("needle"), new PineNeedle()), Direction.Up);
		  mTowerManager.add(new Tower(645, 320, mFileReader.readTowerInfo("walnut"), new Walnut()), Direction.Up);
		  mTowerManager.add(new Tower(770, 135, mFileReader.readTowerInfo("kernel"), new Kernel()), Direction.Up);
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
				if(mEnemiesSpawned < mWave * UNIT_COUNT){
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
			unit = new Unit(-50, FLYING_START_POINT, mFileReader.readMonstersInfo(unitType));
		}
		else{
			unit = new Unit(-50, WALKING_START_POINT, mFileReader.readMonstersInfo(unitType));
		}
		unit.setCanFly(canFly);
		mUnitManager.addUnit(unit);
	}
	
	public void draw(Graphics2D g2d) 
	{
		mLevels.get(0).draw(g2d);
		mTowerManager.draw(g2d);
		mUnitManager.draw(g2d);
		mScroller.draw(g2d);
	}
	
	@Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;        
        super.paintComponent(g2d);        
        draw(g2d);
    }
	
	public void gameLoop()
	{
		long beginTime, timeTaken, timeLeft;
		long lastTime = System.nanoTime();
		
		while(mIsRunning)
		{
			beginTime = System.nanoTime();
			
			mGameTime = (System.nanoTime() - lastTime) / MILISEC_IN_NANOSEC;
			update(mGameTime);
			lastTime = System.nanoTime();

			repaint();
			
			timeTaken = System.nanoTime() - beginTime;
	        timeLeft = (mUpdatePeriod - timeTaken) / MILISEC_IN_NANOSEC;
			
	        if (timeLeft < 10) 
	            timeLeft = 10; //set a minimum
	        try {
	             //Provides the necessary delay and also yields control so that other thread can do work.
	             Thread.sleep(timeLeft);
	        } catch (InterruptedException ex) { }
		}
        
	}
	
	public UnitManager getUnitManager() {
		return mUnitManager;
	}
	
	public TowerManager getTowerManager() {
		return mTowerManager;
	}

}

