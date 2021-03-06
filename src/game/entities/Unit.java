package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import game.graphics.Animation;
import game.utilities.Waypoint;

public class Unit 
{
	static final int HEALTH_BAR_WIDTH = 40;
	static final int HEALTH_BAR_HEIGHT = 7;
	static final int HEALTH_Y_OFFSET = -10;
	static final int BOUNTY = 10;
	
	
	private ArrayList<Animation> mAnimations;
	private Animation mCurrentAnimation;
	private int mX;
	private int mY;
	private int mWidth;
	private int mHeight;
	private int mMaxHealth;
	private int mCurrentHealth;
	private Waypoint mCurrentWaypoint;
	private float mMaxSpeed = 2;
	private long mSlowMaxTime = 0;
	private long mSlowTime = 0;
	private float mSpeed = 2;
	private boolean mCanFly;
	private int mBounty;
	
	public Unit(int locX, int locY, ArrayList<Animation> animation)
	{
		mAnimations = animation;
		mCurrentAnimation = mAnimations.get(2);
		mWidth = mCurrentAnimation.getCurrentFrame().getWidth();
		mHeight = mCurrentAnimation.getCurrentFrame().getHeight();
		mX = locX;
		mY = locY;
		
		mMaxHealth = 100;
		mCurrentHealth = mMaxHealth;
		mBounty = BOUNTY;
	}
	
	public void setAnimation(Direction direction)
	{	
		//Gives unit animation corresponding with move direction
		switch(direction)
		{
			case Down:
				mCurrentAnimation = mAnimations.get(0);
				break;
			case Left:
				mCurrentAnimation = mAnimations.get(1);
				break;
			case Right:
				mCurrentAnimation = mAnimations.get(2);
				break;
			case Up:
				mCurrentAnimation = mAnimations.get(3);
				break;
			default:
				mCurrentAnimation = mAnimations.get(0);
				
		}
	}
	
	public int getX()
	{
		return mX;
	}
	
	public int getY()
	{
		return mY;
	}
	
	public int getWidth()
	{
		return mWidth;
	}
	
	public int getHeight()
	{
		return mHeight;
	}
	
	public int getBounty()
	{
		return mBounty;
	}
	
	public void update(long gametime){
		mCurrentAnimation.update(gametime);
		
		//Only moves unit if valid waypoint is set
		if(mCurrentWaypoint != null){
			calcSpeed(gametime);
			move();
		}
	}
	
	public void draw(Graphics2D g2d){
		
		//Draw health bar
		float healthPercentage = (float)mCurrentHealth / mMaxHealth;
		
		if(healthPercentage < 0)
			healthPercentage = 0;
		else if(healthPercentage > 1)
			healthPercentage = 1;
			
		Color healthBarColor;
		
		if(healthPercentage > .5){
			healthBarColor = new Color(((255 - (int)(255*(healthPercentage))) * 2),255,0,255);
		}else{
			healthBarColor = new Color(255,((int)(255*(healthPercentage))) * 2,0,255);
		}
		
		//Check Slowed
		if (mSpeed < mMaxSpeed){
			g2d.drawImage(mCurrentAnimation.getCurrentFrameSlowed(), mX, mY, null);
			healthBarColor = new Color(healthBarColor.getRed(), healthBarColor.getGreen(), healthBarColor.getBlue() + 150, healthBarColor.getAlpha());
			//g2d.drawImage(mCurrentAnimation.getCurrentFrame(), mX, mY, null);
		}
		else{
			g2d.drawImage(mCurrentAnimation.getCurrentFrame(), mX, mY, null);
		}
		
		
		//Health bar position is relative to unit position
		//Health bar fades from green to yellow to red as it decreases!
		g2d.setColor(healthBarColor);
		g2d.fillRect(mX, mY + HEALTH_Y_OFFSET, (int)(HEALTH_BAR_WIDTH * healthPercentage), HEALTH_BAR_HEIGHT);
		g2d.setColor(Color.black);
		g2d.drawRect(mX, mY + HEALTH_Y_OFFSET, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
	}
	
	public void applySlow(float speed, long time){
		
		//Only applies slow if unit will actually be slowed down
		if(mSpeed >= speed){
			mSpeed = speed;
			mSlowMaxTime = time;
			mSlowTime = mSlowMaxTime;
		}
		
	}
	
	private void calcSpeed(long gametime)
	{
		if(mSlowTime > 0){
			mSlowTime -= gametime;
			
			if(mSlowTime <= 0){
				mSpeed = mMaxSpeed;
			}
		}
		
	}
	
	private void move() {		
		switch(mCurrentWaypoint.getDirection()){
		case Right:
			mX +=mSpeed;
			if(mX > mCurrentWaypoint.getPosition()){
				setCurrentWaypoint(mCurrentWaypoint.getNextWaypoint());
			}
			break;
		case Left:
			mX -=mSpeed;	
			if(mX < mCurrentWaypoint.getPosition()){
				setCurrentWaypoint(mCurrentWaypoint.getNextWaypoint());
			}
			break;
		case Up:
			mY -=mSpeed;	
			if(mY < mCurrentWaypoint.getPosition()){
				setCurrentWaypoint(mCurrentWaypoint.getNextWaypoint());
			}
			break;
		case Down:
			mY +=mSpeed;	
			if(mY > mCurrentWaypoint.getPosition()){
				setCurrentWaypoint(mCurrentWaypoint.getNextWaypoint());
			}
			break;
		default:
			break;
		}
	}
	
	public int getMaxHealth() {
		return mMaxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.mMaxHealth = maxHealth;
	}

	public int getCurrentHealth() {
		return mCurrentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.mCurrentHealth = currentHealth;
	}

	public void inflictDamage(int damage){
		mCurrentHealth -= damage;
	}
	
	public Waypoint getCurrentWaypoint() {
		return mCurrentWaypoint;
	}

	public void setCurrentWaypoint(Waypoint waypoint) {
		this.mCurrentWaypoint = waypoint;
		if(waypoint != null){
			setAnimation(mCurrentWaypoint.getDirection());
		}
	}

	public boolean isCanFly() {
		return mCanFly;
	}

	public void setCanFly(boolean mCanFly) {
		this.mCanFly = mCanFly;
	}
	
	public int getHalfWidth(){
		return (int)(mCurrentAnimation.getCurrentFrame().getWidth()/2);
	}
	
	public int getHalfHeight(){
		return (int)(mCurrentAnimation.getCurrentFrame().getHeight()/2);
	}
	
}
