package game.levelSystems;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Layer 
{
	private int mMapWidth;
	private int mMapHeight;
	private int mTileWidth;
	private int mTileHeight;
	private int mX;
	private int mY;
	
	private ArrayList<BufferedImage> mTiles;
	
	public Layer(ArrayList<BufferedImage> tiles, int x, int y, int w, int h)
	{
		mTiles = new ArrayList<BufferedImage>();
		
		mTiles = tiles;
		
		mX = x;
		mY = y;
		mMapWidth = w;
		mMapHeight = h;
		mTileWidth = 32;
		mTileHeight = 32;
	}
	
	public int getMapWidth()
	{
		return mMapWidth;
	}
	
	public int getMapHeight()
	{
		return mMapHeight;
	}
	
	public int getX()
	{
		return mX;
	}
	
	public int getY()
	{
		return mY;
	}
	
	public void draw(Graphics2D g2d)
	{
		for (int i = 0; i < mTiles.size(); ++i)
		{
			int row = i / mMapWidth;
			int col = i % mMapWidth;
			
			int x = mX + (col * mTileWidth);
			int y = mY + (row * mTileHeight);
			
			if(mTiles.get(i) != null)
			{
				g2d.drawImage(mTiles.get(i), x, y, null);
			}
		}
		
		
	}
}
