package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import game.Game;
import game.entities.WaveScroller;
import game.utilities.Sound;
import game.utilities.Waypoint;

public class Utility 
{	
	@Test
	public void soundCreated()
	{
		Sound sound = new Sound("music.wav");
		assertEquals("music.wav", sound.getSoundFile());
	}
	
	@Test
	public void gameCreated()
	{
		Game game = Game.instance();
		assertEquals(true, game.getIsRunning());
	}

	@Test
	public void waveTest()
	{
		WaveScroller scroller = new WaveScroller();
		
		while(!scroller.startNewWave()){
			scroller.updateSpawner(15, 1);
		}
		
		assertTrue(scroller.startNewWave());
	}
	
	@Test
	public void waypointTest()
	{
		ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
		waypoints.add(new Waypoint(150,"right"));
		waypoints.add(new Waypoint(150,"up"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		
		assertEquals(waypoints.get(0).getNextWaypoint(), waypoints.get(waypoints.size() - 1));
	}
	
	@Test
	public void waypointNullTest()
	{
		ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
		waypoints.add(new Waypoint(150,"right"));
		waypoints.add(new Waypoint(150,"up"));
		waypoints.get(waypoints.size() - 2).setNextWaypoint(waypoints.get(waypoints.size()-1));
		
		assertNull(waypoints.get(waypoints.size() - 1).getNextWaypoint());
	}
}
