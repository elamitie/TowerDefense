package game.utilities;

public class Util {

	public static float snapToGrid(float val, float increment) {
		return ((float)Math.floor(val / increment) * increment);
	}
	
}
