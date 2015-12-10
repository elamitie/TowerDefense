package game.levelSystems;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Layer 
{
	private int mMapWidth;
	private int mMapHeight;
	private int mTileWidth;
	private int mTileHeight;
	private int mX;
	private int mY;
	
	private ArrayList<BufferedImage> mTiles;
	private ArrayList<Boolean> buildables;
	
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
			
			System.out.println(i);
			if(mTiles.get(i) != null)
			{
				g2d.drawImage(mTiles.get(i), x, y, null);
			}
			if (buildables != null) {
				if (buildables.get(i) == true) {
					g2d.fillOval(x + 5, y + 5, 10, 10);
				}
			}
		}
	}

	public void setMarkers(ArrayList<Boolean> markers) {
		this.buildables = markers;
	}
}
