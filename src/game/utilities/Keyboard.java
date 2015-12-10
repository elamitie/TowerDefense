package game.utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter implements InputDevice {
	
	private static final int MAX_KEY_CAPACITY = 256;
	
	private static boolean[] pressed = new boolean[MAX_KEY_CAPACITY];
	private static boolean[] released = new boolean[MAX_KEY_CAPACITY];
	private static boolean[] down = new boolean[MAX_KEY_CAPACITY];
	
	public Keyboard() {
	}
	
	public static boolean pressed(int code) {
		return pressed[code];
	}
	
	public static boolean released(int code) {
		return released[code];
	}
	
	public static boolean held(int code) {
		return down[code];
	}
	
	public void keyPressed(KeyEvent e) {
		if (!down[e.getKeyCode()]) {
			pressed[e.getKeyCode()] = true;
			down[e.getKeyCode()] = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		released[e.getKeyCode()] = true;
		down[e.getKeyCode()] = false;
	}

	@Override
	public void clear() {
		pressed = new boolean[MAX_KEY_CAPACITY];
		released = new boolean[MAX_KEY_CAPACITY];
	}
	
}