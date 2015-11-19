package game.utilities;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import game.graphics.Animation;
import game.levelSystems.Layer;
import game.levelSystems.LevelLayout;

public class JSONReader
{	
	private BufferedImage mMonstersSpriteSheet;
	private BufferedImage towerSpriteSheet;
	private JsonObject towerFileInfo;
	private JsonObject mMonstersFileInfo;
	private JsonObject mTileFileInfo;
	private HashMap<String, BufferedImage> mSpriteSheets;
	HashMap<Integer, BufferedImage> mTileSet;

	public JSONReader()
	{
		mSpriteSheets = new HashMap<String, BufferedImage>();
		mTileSet = new HashMap<Integer, BufferedImage>();
	}
	
	public void loadFiles()
	{
		try {
			towerSpriteSheet = ImageIO.read(new File("images/towers.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mMonstersSpriteSheet = ImageIO.read(new File("images/monsters.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mSpriteSheets.put("tiles-grass.png", ImageIO.read(new File("images/tiles-grass.png")));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mSpriteSheets.put("tiles-shrooms.png", ImageIO.read(new File("images/tiles-shrooms.png")));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mSpriteSheets.put("tiles-tree.png", ImageIO.read(new File("images/tiles-tree.png")));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		String json = "";
		
		try {
			Scanner scanner = new Scanner(new File("images/towers.json"));
			json = scanner.useDelimiter("\\Z").next();
			scanner.close();
		} catch (IOException e) {
			System.out.println("File not found");
		}
		towerFileInfo = new JsonParser().parse(json).getAsJsonObject();
		
		try 
		{
			Scanner scanner = new Scanner(new File("images/monsters.json"));
			json = scanner.useDelimiter("\\Z").next();
			scanner.close();
		}
		catch (IOException e)
		{
			System.out.println("File not found");
		}
		mMonstersFileInfo = new JsonParser().parse(json).getAsJsonObject();		
		
		try 
		{
			Scanner scanner = new Scanner(new File("images/tilemap.json"));
			json = scanner.useDelimiter("\\Z").next();
			scanner.close();
		}
		catch (IOException e)
		{
			System.out.println("File not found");
		}
		mTileFileInfo = new JsonParser().parse(json).getAsJsonObject();
				
		loadTiles();
	}
	
	public ArrayList<Animation> readMonstersInfo(String type)
	{			
		JsonElement frames = mMonstersFileInfo.get("frames");
		
		JsonObject framesObject = frames.getAsJsonObject();
		
		int frameCount = 1;
		String[] baseStrings = {type + "Down", type + "Left", type + "Right", type + "Up"};
		
		String frameName = "";
		
		ArrayList<Animation> animations = new ArrayList<Animation>();
		
		for (int i = 0; i < baseStrings.length; ++i)
		{
			frameCount = 1;
			frameName = baseStrings[i] + frameCount + ".png";
			
			ArrayList<BufferedImage> framesList = new ArrayList<BufferedImage>();
			
			while (framesObject.has(frameName))
			{
				JsonElement elem = framesObject.get(frameName);
				JsonObject obj = elem.getAsJsonObject();
				JsonObject frameInfo = obj.get("frame").getAsJsonObject();
				
				JsonElement x = frameInfo.get("x");
				JsonElement y = frameInfo.get("y");
				JsonElement width = frameInfo.get("w");
				JsonElement height = frameInfo.get("h");
				
				//makes a Sprite out of the info and adds it to Animation
				framesList.add(mMonstersSpriteSheet.getSubimage(x.getAsInt(), y.getAsInt(), width.getAsInt(), height.getAsInt()));
				
				++frameCount;
				frameName = baseStrings[i] + frameCount + ".png";
			}
			
			animations.add(new Animation(framesList));
		}
		
		return animations;
	}
	
	public ArrayList<Animation> readTowerInfo(String type) {
		
		JsonElement frames = towerFileInfo.get("frames");
		JsonObject framesObject = frames.getAsJsonObject();
		
		String[] baseStrings = { type + "-down", type + "-left", type + "-right", type + "-up", type + "-dl", type + "-dr", type + "-ur", type + "-ul" };
		
		String frameName = "";
		
		ArrayList<Animation> animations = new ArrayList<Animation>();
		
		BufferedImage base = this.parseAnimationFrame(framesObject, "base.png", "frame", towerSpriteSheet);
		
		for (int i = 0; i < baseStrings.length; ++i) {
			frameName = baseStrings[i] + ".png";
			ArrayList<BufferedImage> framesList = new ArrayList<BufferedImage>();
			
			if (framesObject.has(frameName)) {
				// Here we draw the actual tower type onto the base image into one image
				BufferedImage tower = this.parseAnimationFrame(framesObject, frameName, "frame", towerSpriteSheet);
				int w = Math.max(base.getWidth(), tower.getWidth());
				int h = Math.max(base.getHeight(), tower.getHeight());
				BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				
				Graphics g = combined.getGraphics();
				g.drawImage(base, 0, 0, null);
				int halfWidth = (base.getWidth() / 2) - (tower.getWidth() / 2);
				int halfHeight = (base.getHeight() / 2) - (tower.getHeight() / 2);
				g.drawImage(tower, halfWidth, halfHeight, null);
				
				framesList.add(combined);
			}
			
			animations.add(new Animation(framesList));
		}
		
		return animations;
	}
	
	private void loadTiles()
	{		
		JsonArray tileSets = mTileFileInfo.get("tilesets").getAsJsonArray();
		
		for (int i = 0; i < tileSets.size(); ++i)
		{
			JsonObject setInfo = tileSets.get(i).getAsJsonObject();
			
			int firstGID = setInfo.get("firstgid").getAsInt();
			int imageWidth = setInfo.get("imagewidth").getAsInt();
			int imageHeight = setInfo.get("imageheight").getAsInt();
			int tileWidth = setInfo.get("tilewidth").getAsInt();
			int tileHeight = setInfo.get("tileheight").getAsInt();
			String fileName = setInfo.get("image").getAsString();
			
			for (int j = 0; j < imageHeight / tileHeight; ++j)
			{
				for (int k = 0; k < imageWidth / tileWidth; ++k)
				{
					int x = k * tileWidth;
					int y = j * tileHeight;
										
					mTileSet.put(firstGID + k + j * imageWidth / tileWidth , mSpriteSheets.get(fileName).getSubimage(x, y, tileWidth, tileHeight));
				}
			}
		}
	}
	
	public ArrayList<LevelLayout> readLevelInfo()
	{
		ArrayList<LevelLayout> levels = new ArrayList<LevelLayout>();
		
		LevelLayout newLevel = new LevelLayout();
		
		JsonArray levelLayers = mTileFileInfo.get("layers").getAsJsonArray();
		
		for (int i = 0; i < levelLayers.size(); ++i)
		{
			JsonObject currentLayer = levelLayers.get(i).getAsJsonObject();
			JsonArray data = currentLayer.get("data").getAsJsonArray();
			ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
			
			for (int j = 0; j < data.size(); ++j)
			{
				int key = data.get(j).getAsInt();
				if (key != 0)
				{
					tiles.add(mTileSet.get(key));
				}
				else
				{
					tiles.add(mTileSet.get(73));
				}
			}
			
			String layerName = currentLayer.get("name").getAsString();
			
			int layerWidth = currentLayer.get("width").getAsInt();
			int layerHeight = currentLayer.get("height").getAsInt();
			int x = currentLayer.get("x").getAsInt();
			int y = currentLayer.get("y").getAsInt();
						
			Layer layer = new Layer(tiles, x, y, layerWidth, layerHeight);
			
			switch (layerName)
			{
			case "Base":
				newLevel.addLayer(layer, 0);
				break;
			case "Tree":
				newLevel.addLayer(layer, 1);
				break;
			case "Top":
				newLevel.addLayer(layer, 2);
				break;
			default:
				newLevel.addLayer(layer, 2);
				break;
			}
		}
		
		levels.add(newLevel);
		
		return levels;
	}
	
	private BufferedImage parseAnimationFrame(JsonObject framesObject, String frameName, String jsonElement, BufferedImage spriteSheet) {
		JsonElement baseElem = framesObject.get(frameName);
		JsonObject baseObj = baseElem.getAsJsonObject();
		JsonObject baseFrameInfo = baseObj.get(jsonElement).getAsJsonObject();
		int x = baseFrameInfo.get("x").getAsInt();
		int y = baseFrameInfo.get("y").getAsInt();
		int width = baseFrameInfo.get("w").getAsInt();
		int height = baseFrameInfo.get("h").getAsInt();
		BufferedImage image = spriteSheet.getSubimage(x, y, width, height);
		return image;
	}
	
}
