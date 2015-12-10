package game.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import game.Game;
import game.entities.Tower;

public class Hud {

	private int mHudPosX;
	private int mHudPosY;
	private boolean mCanSell;
	private boolean mCanUpgrade;
	private boolean mIsSellDown;
	private boolean mIsUpgradeDown;
	private String mKillData;
	private String mSpeedData;
	private String mCostData;
	private String mRangeData;
	private String mUpgradeData;
	private String mSellData;
	private int mSellUpgradeButtonsXOff;
	private int mSellUpgradeButtonsYOff;
	private Tower mTower;
	private List<HudTower> mHudTowers;
	
	static final int TEXT_SIZE = 30;
	
	private BufferedImage mHudFrame;
	private static final String HUD_FRAME_PATH = "images/HUD-BG.png";
	private BufferedImage mHudUserMenu;
	private static final String HUD_USER_MENU_PATH = "images/HUD-UserMenu.png";
	private BufferedImage mHudTowerMenu;
	private static final String HUD_TOWER_MENU_PATH = "images/HUD-TowerMenu.png";
	private BufferedImage mSellButtonUp;
	private static final String HUD_SELL_BUTTON_UP_PATH = "images/sellButtonUp.png";
	private BufferedImage mSellButtonDown;
	private static final String HUD_SELL_BUTTON_DOWN_PATH = "images/sellButtonDown.png";
	private BufferedImage mSellButtonDisabled;
	private static final String HUD_SELL_BUTTON_DISABLED_PATH = "images/sellButtonDisabled.png";
	private BufferedImage mUpgradeButtonUp;
	private static final String HUD_UPGRADE_BUTTON_UP_PATH = "images/upgradeButtonUp.png";
	private BufferedImage mUpgradeButtonDown;
	private static final String HUD_UPGRADE_BUTTON_DOWN_PATH = "images/upgradeButtonDown.png";
	private BufferedImage mUpgradeButtonDisabled;
	private static final String HUD_UPGRADE_BUTTON_DISABLED_PATH = "images/upgradeButtonDisabled.png";
	
	public Hud(){
		
		mHudPosX = 0;
		mHudPosY = 512;
		
		mSellUpgradeButtonsXOff = mHudPosX + 710;
		mSellUpgradeButtonsYOff = mHudPosY + 10;
		
		mIsSellDown= false;
		mIsUpgradeDown = false;
		
		setDefaults();
		
		loadHud();
	}
	
	public void update()
	{
		checkUpgradeClick();
		checkSellClick();
		
		for(HudTower hudTower : mHudTowers){
			hudTower.update();
		}
		
	}
	
	public void updateStats(Tower tower)
	{
		mTower = tower;
		
		//Set data to tower data
		mKillData = String.valueOf(tower.getWeapon().getDamage());
		mSpeedData = String.valueOf(tower.getWeapon().getRateOfFire());
		mRangeData = String.valueOf(tower.getWeapon().getAttackRadius());
		mCostData = String.valueOf(tower.getWeapon().getCost());
		mUpgradeData = String.valueOf(tower.getWeapon().getUpgradeValue());
		mSellData = String.valueOf(tower.getWeapon().getSellAmount());
		
		mCanSell = true;
		mCanUpgrade = true;
	}
	
	public void draw(Graphics2D g2d){
		g2d.drawImage(mHudFrame, mHudPosX, mHudPosY, null);
		g2d.drawImage(mHudUserMenu, mHudPosX + 675, mHudPosY, null);
		g2d.setFont(new Font("Andy", Font.PLAIN, TEXT_SIZE));
		g2d.setColor(new Color(255,255,255));
		g2d.drawString(Integer.toString(Game.instance().getLives()), mHudPosX + 845, mHudPosY + 35);
		g2d.drawString(Integer.toString(Game.instance().getMoney()), mHudPosX + 845, mHudPosY + 65);
		g2d.drawImage(mHudTowerMenu, mHudPosX + 445, mHudPosY, null);
		g2d.drawString(mKillData, mHudPosX + 500, mHudPosY + 35);
		g2d.drawString(mSpeedData, mHudPosX + 630, mHudPosY + 35);
		g2d.drawString(mRangeData, mHudPosX + 500, mHudPosY + 65);
		g2d.drawString(mCostData, mHudPosX + 630, mHudPosY + 65);
		
		if(mCanUpgrade){
			if(!mIsUpgradeDown)
				g2d.drawImage(mUpgradeButtonUp, mSellUpgradeButtonsXOff, mSellUpgradeButtonsYOff, null);
			else
				g2d.drawImage(mUpgradeButtonDown, mSellUpgradeButtonsXOff, mSellUpgradeButtonsYOff, null);
			
			g2d.drawString(mUpgradeData, mSellUpgradeButtonsXOff + 35, mSellUpgradeButtonsYOff + 25);
		}
		else
			g2d.drawImage(mUpgradeButtonDisabled, mSellUpgradeButtonsXOff, mSellUpgradeButtonsYOff, null);
		
		if(mCanSell){
			if(!mIsSellDown)
				g2d.drawImage(mSellButtonUp, mSellUpgradeButtonsXOff, mSellUpgradeButtonsYOff + 30, null);
			else
				g2d.drawImage(mSellButtonDown, mSellUpgradeButtonsXOff, mSellUpgradeButtonsYOff + 30, null);
			
			g2d.drawString(mSellData, mSellUpgradeButtonsXOff + 35, mSellUpgradeButtonsYOff + 55);
		}
		else
			g2d.drawImage(mSellButtonDisabled, mSellUpgradeButtonsXOff, mSellUpgradeButtonsYOff + 30, null);
	
		
		for(HudTower hudTower : mHudTowers){
			hudTower.draw(g2d);
		}
		
	}
	
	private void loadHud(){
		
		try {
			mHudFrame = ImageIO.read(new File(HUD_FRAME_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mHudUserMenu = ImageIO.read(new File(HUD_USER_MENU_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mHudTowerMenu = ImageIO.read(new File(HUD_TOWER_MENU_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mSellButtonUp = ImageIO.read(new File(HUD_SELL_BUTTON_UP_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mSellButtonDown = ImageIO.read(new File(HUD_SELL_BUTTON_DOWN_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mSellButtonDisabled = ImageIO.read(new File(HUD_SELL_BUTTON_DISABLED_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mUpgradeButtonUp = ImageIO.read(new File(HUD_UPGRADE_BUTTON_UP_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mUpgradeButtonDown = ImageIO.read(new File(HUD_UPGRADE_BUTTON_DOWN_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mUpgradeButtonDisabled = ImageIO.read(new File(HUD_UPGRADE_BUTTON_DISABLED_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mHudTowers = new ArrayList<HudTower>();
		
		mHudTowers.add(new HudTower("kernel", 15, 525));
		mHudTowers.add(new HudTower("walnut", 85, 525));
		mHudTowers.add(new HudTower("needle", 155, 525));
		mHudTowers.add(new HudTower("cone", 230, 525));
		mHudTowers.add(new HudTower("lightning", 305, 525));
		mHudTowers.add(new HudTower("water", 375, 525));
		
	}
	
	public void setDefaults() {
		mTower = null;
		mKillData = " ";
		mSpeedData = " ";
		mCostData = " ";
		mRangeData = " ";
		mUpgradeData = " ";
		mSellData = " ";
		
		mCanSell = false;
		mCanUpgrade = false;
	}
	
	private void checkUpgradeClick(){
		int radius = mUpgradeButtonUp.getWidth() / 2;
		boolean intersects = Mouse.getXY().intersectsCircle(mSellUpgradeButtonsXOff + radius, mSellUpgradeButtonsYOff + radius, radius);
		
		if (Mouse.leftPressed && intersects) {
			if(mTower != null && Game.instance().getMoney() >= mTower.getWeapon().getUpgradeValue()){
				mTower.getWeapon().upgrade();
				Game.instance().setMoney(Game.instance().getMoney() - mTower.getWeapon().getUpgradeValue());
				mTower.getWeapon().setCost(mTower.getWeapon().getCost() + mTower.getWeapon().getUpgradeValue());
				updateStats(mTower);
			}
		}
	}
	
	private void checkSellClick(){
		int radius = mSellButtonUp.getWidth() / 2;
		boolean intersects = Mouse.getXY().intersectsCircle(mSellUpgradeButtonsXOff + radius, mSellUpgradeButtonsYOff + 30 + radius, radius);
		
		if (Mouse.leftPressed && intersects) {
			if(mTower != null){
				Game.instance().setMoney(Game.instance().getMoney() + mTower.getWeapon().getSellAmount());
				Game.instance().getTowerManager().remove(mTower);
				setDefaults();
			}
		}
	}
	
	public String getDamageData(){return mKillData;};
	public String getSpeedData(){return mSpeedData;};
	public String getCostData(){return mCostData;};
	public String getRangeData(){return mRangeData;};
	public String getUpgradeData(){return mUpgradeData;};
	public String getSellData(){return mSellData;};
	public Tower getTower(){return mTower;};
}
