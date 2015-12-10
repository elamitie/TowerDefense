package game.levelSystems;
import java.awt.Color;
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
	private ArrayList<Boolean> buildables;
	
	public static boolean showMarkers = false;
	
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
			if (buildables != null && showMarkers) {
				Color color = g2d.getColor();
				g2d.setColor(Color.yellow);
				
				if (buildables.get(i) == true) {
					g2d.fillOval(x + mTileWidth / 2, y + mTileWidth / 2, mTileWidth / 2, mTileHeight / 2);
				}
				
				g2d.setColor(color);
			}
		}
	}

	public void setMarkers(ArrayList<Boolean> markers) {
		this.buildables = markers;
	}
}
