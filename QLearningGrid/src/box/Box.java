package box;

import run.Colour;

public class Box {
	protected int x, y;
	public static int boxSize = 20;
	protected Colour colour;
	
	/**
	 * Get the x coordinate of this box
	 * @return int x coordinate
	 */
	public int getX(){ return x;}
	
	/**
	 * Get the y coordinate of this box
	 * @return int y coordinate
	 */
	public int getY(){ return y;}
	
	/**
	 * Get the colour of this box
	 * @return Colour that has been assigned to this box
	 */
	public Colour getColour(){ return colour;}
	
}
