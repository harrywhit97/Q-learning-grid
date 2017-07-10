package box;

import java.awt.Color;

	

public enum BoxType {
	State, Wall, Target, Trap;	
	
	private static Color wallColor = Color.black;
	private static Color stateColor = Color.white;
	private static Color targetColor = Color.green;
	private static Color trapColor = Color.red;

	/**
	 * Get the Color associated with a box type
	 * @param type type of box to find the color of
	 * @return Color of box or null if invalid input
	 */
	public static Color getColor(BoxType type){
		
		switch(type){
			case State:	 return stateColor;
			case Wall: 	 return wallColor;
			case Target: return targetColor;
			case Trap: 	 return trapColor;
			default:	 return null;
		}		
	}

}
