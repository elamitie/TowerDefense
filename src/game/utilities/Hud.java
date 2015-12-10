package game.utilities;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Game;

public class Hud {

	private int mHudPosX;
	private int mHudPosY;
	
	static final int TEXT_SIZE = 30;
	
	private BufferedImage mHudFrame;
	private static final String HUD_FRAME_PATH = "images/HUD-BG.png";
	private BufferedImage mHudUserMenu;
	private static final String HUD_USER_MENU_PATH = "images/HUD-UserMenu.png";
	private BufferedImage mHudTowerMenu;
	private static final String HUD_TOWER_MENU_PATH = "images/HUD-TowerMenu.png";
	
	
	public Hud(){
		
		mHudPosX = 0;
		mHudPosY = 512;
		
		loadHud();
	}
	
	public void draw(Graphics2D g2d){
		g2d.drawImage(mHudFrame, mHudPosX, mHudPosY, null);
		g2d.drawImage(mHudUserMenu, mHudPosX + 675, mHudPosY, null);
		g2d.setFont(new Font("Andy", Font.PLAIN, TEXT_SIZE));
		g2d.drawString(Integer.toString(Game.instance().getLives()), mHudPosX + 845, mHudPosY + 30);
		g2d.drawString(Integer.toString(Game.instance().getMoney()), mHudPosX + 845, mHudPosY + 60);
		g2d.drawImage(mHudTowerMenu, mHudPosX + 445, mHudPosY, null);
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
		
	}
}
