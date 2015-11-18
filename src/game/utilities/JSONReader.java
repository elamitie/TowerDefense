package game.utilities;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.*;

import game.graphics.*;
import game.levelSystems.Layer;
import game.levelSystems.LevelLayout;

public class JSONReader
{	
	private BufferedImage mMonstersSpriteSheet;
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
		try 
		{
			mMonstersSpriteSheet = ImageIO.read(new File("images/monsters.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			mSpriteSheets.put("tiles-grass.png", ImageIO.read(new File("images/tiles-grass.png")));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			mSpriteSheets.put("tiles-shrooms.png", ImageIO.read(new File("images/tiles-shrooms.png")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			mSpriteSheets.put("tiles-tree.png", ImageIO.read(new File("images/tiles-tree.png")));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		String jsonString = "";
		
		try 
		{
			Scanner scanner = new Scanner(new File("images/monsters.json"));
			jsonString = scanner.useDelimiter("\\Z").next();
			scanner.close();
		}
		catch (IOException e)
		{
			System.out.println("File not found");
		}
		
		
		mMonstersFileInfo = new JsonParser().parse(jsonString).getAsJsonObject();
		
		try 
		{
			Scanner scanner = new Scanner(new File("images/tilemap.json"));
			jsonString = scanner.useDelimiter("\\Z").next();
			scanner.close();
		}
		catch (IOException e)
		{
			System.out.println("File not found");
		}
		
		
		mTileFileInfo = new JsonParser().parse(jsonString).getAsJsonObject();
		
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
	
	
}
