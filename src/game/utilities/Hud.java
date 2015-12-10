package game.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Game;
import game.entities.Tower;
import game.weapons.ProjectileBasedWeapon;

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
		
		mCanSell = true;
		mCanUpgrade = true;
		mIsSellDown= false;
		mIsUpgradeDown = false;
		
		mKillData = " ";
		mSpeedData = " ";
		mCostData = " ";
		mRangeData = " ";
		mUpgradeData = " ";
		mSellData = " ";
		
		loadHud();
	}
	
	public void updateStats(Tower tower)
	{
		//Set data to tower data
		mKillData = String.valueOf(tower.getWeapon().getDamage());
		mSpeedData = String.valueOf(tower.getWeapon().getRateOfFire());
		mRangeData = String.valueOf(tower.getWeapon().getAttackRadius());
		mCostData = String.valueOf(tower.getWeapon().getCost());
		mSellData = String.valueOf(tower.getWeapon().getSellAmount());
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
				g2d.drawImage(mUpgradeButtonUp, mHudPosX + 710, mHudPosY + 10, null);
			else
				g2d.drawImage(mUpgradeButtonDown, mHudPosX + 710, mHudPosY + 10, null);
			
			g2d.drawString(mUpgradeData, mHudPosX + 745, mHudPosY + 35);
		}
		else
			g2d.drawImage(mUpgradeButtonDisabled, mHudPosX + 710, mHudPosY + 10, null);
		
		if(mCanSell){
			if(!mIsSellDown)
				g2d.drawImage(mSellButtonUp, mHudPosX + 710, mHudPosY + 40, null);
			else
				g2d.drawImage(mSellButtonDown, mHudPosX + 710, mHudPosY + 40, null);
			
			g2d.drawString(mSellData, mHudPosX + 745, mHudPosY + 65);
		}
		else
			g2d.drawImage(mSellButtonDisabled, mHudPosX + 710, mHudPosY + 40, null);
		
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
	}
	
	public void setDefaults() {
		mKillData = " ";
		mSpeedData = " ";
		mCostData = " ";
		mRangeData = " ";
		mUpgradeData = " ";
		mSellData = " ";
	}
}
