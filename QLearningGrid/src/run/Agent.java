package run;

import java.awt.Color;

/**
 * This is the class for the agent. The agent will navigate through the grid to 
 * find the target. This class implements the singleton design pattern.
 * @author harry
 *
 */
public class Agent {
	
	private static Agent agent;
	private static int xInitLocation, yInitLocation;
	private static int xLocation, yLocation;
	private final static Color color = Color.blue;
	
	private Agent(int x, int y){
		setInitPosistion(x, y);
	}
	
	/**
	 * If an instance exists this sets the x and y locations to the new
	 * Specified coordinates. If the instance has not been initialized this
	 * creates and returns a new instance on Agent.
	 * @param x X position on the agent in the grid
	 * @param y Y position of the agent in the grid
	 * @return instance of agent
	 */
	public static Agent getInstance(int x, int y){
		if(agent == null){
			agent = new Agent(x, y);
		}else{
			setInitPosistion(x, y);
		}
		return agent;
	}
	
	private static void setInitPosistion(int x, int y){
		xInitLocation = x;
		yInitLocation = y;
		xLocation = xInitLocation;
		yLocation = yInitLocation;
	}
	
	public int getX(){
		return xLocation;
	}
	
	public int getY(){
		return yLocation;
	}
	
	public static Color getColor(){
		return color;
	}
	
	/**
	 * Move the agent one place in the specified direction
	 * @param dir direction to move agent in
	 */
	public void move(Direction dir){
		switch(dir){
			case North:
				yLocation--;
				break;
			case East:
				xLocation++;			
				break;
			case South:
				yLocation++;
				break;
			case West:
				xLocation--;
				break;
		}
	}
	
	/**
	 * Reset Agent to initial position
	 */
	public void resetToInitial(){
		xLocation = xInitLocation;
		yLocation = yInitLocation;
	}
}
