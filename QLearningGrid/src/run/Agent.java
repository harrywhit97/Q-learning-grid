package run;

import java.awt.Color;
import java.util.Random;

import box.State;

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
	private Random randomGenerator;
	private int maxPercent = 100;
	private int eps = 10; //10% of time do non optimal action
	
	private Agent(int x, int y){
		setInitPosistion(x, y);
		randomGenerator = new Random();
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
	
	/**
	 * Set the initial posistion of the agent
	 * @param x
	 * @param y
	 */
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
	
	/**
	 * Get the color of the agent
	 * @return
	 */
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

	/**
	 * Picks the direction the the agent will move next
	 * @param currentState the current state that the agent is in
	 * @return Direction for the agent to travel
	 */
	public Direction getDirectionToGo(State currentState){
		Direction bestDirection = currentState.getBestDirection();
		int randNum = randomGenerator.nextInt(maxPercent);
		
		if(randNum > eps)			return bestDirection;				
		else if (randNum < (eps/2))	return Direction.getLeftOf(bestDirection);										
		else						return Direction.getRightOf(bestDirection);	
	}	
}
