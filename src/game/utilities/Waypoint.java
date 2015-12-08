package game.utilities;

import game.entities.Direction;

public class Waypoint {

	private int mPos;//Either x or y pos to switch to next waypoint at
	private Direction mDirection;//Direction to this waypoint
	private Waypoint mNextWaypoint;//The next waypoint
	
	public Waypoint(int pos, Direction direction)
	{
		mPos = pos;
		mDirection = direction;
	}
	
	public Waypoint(int pos, Direction direction, Waypoint waypoint)
	{
		mPos = pos;
		mDirection = direction;
		mNextWaypoint = waypoint;
	}
	
	public Direction getDirection() {
		return mDirection;
	}
	public void setDirection(Direction mDirection) {
		this.mDirection = mDirection;
	}

	public int getPosition() {
		return mPos;
	}

	public void setPosition(int mPos) {
		this.mPos = mPos;
	}

	public Waypoint getNextWaypoint() {
		return mNextWaypoint;
	}

	public void setNextWaypoint(Waypoint mNextWaypoint) {
		this.mNextWaypoint = mNextWaypoint;
	}
	
	
}
