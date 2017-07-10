package box;

import java.awt.Color;

public class BoxFactory {
	private static Color stateColor = Color.white;
	private static Color wallColor = Color.black;
	private static Color targetColor = Color.green;
	private static Color trapColor = Color.red;		
	
	/**
	 * Get the box of the type specified
	 * @param type type of box to make (BoxType enum)
	 * @return relevant box or null if invalid input
	 */
	public static Box makeBox(BoxType type){
		Box box = null;
		
		switch(type){
			case State:
				box = new State(stateColor);
				break;
			case Wall:
				box = new State(wallColor);
				break;
			case Target:
				box = new State(targetColor);
				break;
			case Trap:
				box = new State(trapColor);
				break;
			default:
				return null;
		}		
		return box;		
	}
}
