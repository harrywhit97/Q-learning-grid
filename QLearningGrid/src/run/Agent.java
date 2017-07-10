package run;

import java.awt.Color;

public class Agent {
	private int xLocation, yLocation;
	private final static Color COLOR = Color.blue;
	
	public Agent(int x, int y){
		xLocation = x;
		yLocation = y;
	}
	
	public int getX(){
		return xLocation;
	}
	
	public int getY(){
		return yLocation;
	}
	
	/**
	 * Move the agent one place in the specified direction
	 * @param dir direction to move agent in
	 */
	public void move(Direction dir){
		switch(dir){
			case North:
				yLocation++;
				break;
			case East:
				xLocation++;			
				break;
			case South:
				yLocation--;
				break;
			case West:
				xLocation--;
				break;
		}
	}
	
	public static Color getColor(){
		return COLOR;
	}
}
