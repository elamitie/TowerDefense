package game.utilities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Game;
import game.entities.Direction;
import game.entities.Tower;
import game.weapons.Kernel;
import game.weapons.Lightning;
import game.weapons.PineCone;
import game.weapons.PineNeedle;
import game.weapons.Walnut;
import game.weapons.Water;
import game.weapons.Weapon;

public class HudTower {

	private String mName;
	private BufferedImage mImage;
	private boolean mIsSelected;
	private int mX, mY;
	private JSONReader mReader;
	
	
	public HudTower(String name, int x, int y){
		
		mReader = new JSONReader();
		mReader.loadFiles();
		
		mName = name;
		mX = x;
		mY = y;
		
		mIsSelected = false;
		
		if(name.equals("lightning")){
			mImage =  mReader.readTowerInfo(mName).get(3).getCurrentFrame();
		}
		else
			mImage =  mReader.readTowerInfo(mName).get(0).getCurrentFrame();
	}
	
	public void update(){
		checkClick();
	}
	
	public void draw(Graphics2D g2d){
		g2d.drawImage(mImage, mX, mY, null);
		
		if(mIsSelected){
			int radius = mImage.getWidth() / 2;
			g2d.drawImage(mImage, Mouse.getX() - radius, Mouse.getY() - radius, null);
		}
		
	}
	
	
	public int getX(){
		return mX;
	}
	
	public int getY(){
		return mY;
	}
	
	private void checkClick(){
		int radius = mImage.getWidth() / 2;
		boolean intersects = Mouse.getXY().intersectsCircle(mX + radius, mY + radius, radius);
		Weapon weapon = null;
		
		if (Mouse.leftPressed) {
			if(intersects){
				mIsSelected = !mIsSelected;
				
				if(mIsSelected){
					Tower tower = new Tower(Mouse.getX() - mImage.getWidth() / 2, Mouse.getY() - mImage.getWidth() / 2, mReader.readTowerInfo(mName), getWeapon());
					Game.instance().getHud().updateStats(tower);
				}else{
					Game.instance().getHud().setDefaults();
				}
			}
			else if(mIsSelected && Mouse.getY() < 450)
			{
				weapon = getWeapon();
				
				if(Game.instance().getMoney() >= weapon.getCost()){
					Tower tower = new Tower(Mouse.getX() - mImage.getWidth() / 2, Mouse.getY() - mImage.getWidth() / 2, mReader.readTowerInfo(mName), weapon);
					Game.instance().getTowerManager().add(tower, Direction.Up);
					Game.instance().setMoney(Game.instance().getMoney() - weapon.getCost());
					Game.instance().getHud().updateStats(tower);
					tower.setSelected(true);
				}
				mIsSelected = false;
			}

		}
		
	}
	
	private Weapon getWeapon(){
		Weapon weapon = new Kernel();
		
		switch(mName){
			case "water":
				weapon = new Water();
				break;
			case "kernel":
				weapon = new Kernel();
				break;
			case "cone":
				weapon = new PineCone();
				break;
			case "lightning":
				weapon = new Lightning();
				break;
			case "needle":
				weapon = new PineNeedle();
				break;
			case "walnut":
				weapon = new Walnut();
				break;	
		}
		
		return weapon;
	}
	
}
