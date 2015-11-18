package game.utilities;

public class Waypoint {

	private int mPos;//Either x or y pos to switch to next waypoint at
	private String mDirection;//Direction to this waypoint
	private Waypoint mNextWaypoint;//The next waypoint
	
	public Waypoint(int pos, String direction)
	{
		mPos = pos;
		mDirection = direction;
	}
	
	public Waypoint(int pos, String direction, Waypoint waypoint)
	{
		mPos = pos;
		mDirection = direction;
		mNextWaypoint = waypoint;
	}
	
	public String getmDirection() {
		return mDirection;
	}
	public void setmDirection(String mDirection) {
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
