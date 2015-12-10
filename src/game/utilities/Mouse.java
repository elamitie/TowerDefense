package game.utilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter implements InputDevice {
	
	public static final int LEFT_BUTTON = 1;
	public static final int RIGHT_BUTTON = 3;
	
	private static double scale = 1.0;
	private static int x;
	private static int y;
	
	public static boolean leftPressed = false;
	public static boolean leftDown = false;
	public static boolean leftReleased = false;
	
	public static boolean rightPressed = false;
	public static boolean rightDown = false;
	public static boolean rightReleased = false;
	
	public Mouse() {
		
	}
	
	public Mouse(double scale) {
		Mouse.scale = scale;
	}
	
	public void clear() {
		Mouse.leftPressed = false;
		Mouse.rightPressed = false;
		Mouse.leftReleased = false;
		Mouse.rightReleased = false;
	}
	
	public static int getX() { 
		return x;
	}
	
	public static int getY() { 
		return y;
	}
	
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case Mouse.LEFT_BUTTON:
			Mouse.leftPressed = true;
			Mouse.leftDown = true;
			break;
		case Mouse.RIGHT_BUTTON:
			Mouse.rightPressed = true;
			Mouse.rightDown = true;
			break;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case Mouse.LEFT_BUTTON:
			Mouse.leftReleased = true;
			Mouse.leftDown = false;
			break;
		case Mouse.RIGHT_BUTTON:
			Mouse.rightReleased = true;
			Mouse.rightDown = false;
			break;
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		x = (int)(e.getX() / scale / scale);
		y = (int)(e.getY() / scale / scale);
	}
	
	public void mouseDragged(MouseEvent e){
		x = (int)(e.getX() / scale / scale);
		y = (int)(e.getY() / scale / scale);
	}
}
