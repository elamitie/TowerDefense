package game.levelSystems;
import java.awt.Graphics2D;
import java.util.*;

public class LevelLayout 
{
	private ArrayList<Layer> layers;
	
	public LevelLayout()
	{
		layers = new ArrayList<Layer>();
	}
	
	public void addLayer(Layer layer, int index)
	{
		if (index >= layers.size())
		{
			layers.add(layer);
		}
		else
		{
			layers.add(index, layer);
		}
	}
	
	public Layer getLayer(int index)
	{
		if (index >= 0 && index < layers.size())
		{
			return layers.get(index);
		}
		
		return null;
	}
	
	public int getNumLayers()
	{
		return layers.size();
	}
	
	public void draw(Graphics2D g2d)
	{
		for (int i = 0; i < layers.size(); ++i)
		{
			layers.get(i).draw(g2d);
		}
	}
}
