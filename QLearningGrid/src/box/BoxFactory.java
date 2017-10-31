package box;

import java.awt.Color;

public class BoxFactory {
	private static Color stateColor = Color.white;
	private static Color wallColor = Color.black;
	private static Color targetColor = Color.green;
	private static Color trapColor = Color.red;		
	
	private static double targetReward = 1.0;
	private static double trapReward = -1.0;
	
	/**
	 * Get the box of the type specified (This class also defines the colors)
	 * @param type type of box to make (BoxType enum)
	 * @return relevant box or null if invalid input
	 */
	public static Box makeBox(BoxType type){			
		switch(type){
			case State: 	return new State(stateColor);
			case Wall:		return new Wall(wallColor);
			case Target:	return new Target(targetColor, targetReward);
			case Trap:		return new Trap(trapColor, trapReward);
			default:		return null;
		}		
	}
}
