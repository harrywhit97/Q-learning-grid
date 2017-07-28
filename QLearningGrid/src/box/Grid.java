package box;

import java.awt.Color;

import javax.swing.JButton;

import run.Agent;

public abstract class Grid {
	
	/**
	 * Make the grid from Boxes (State, target, trap, wall) and make agent 
	 */
	public static Box[][] makeGridBoxes(JButton[][] buttons){
		int Y = buttons.length;
		int X = buttons[0].length;
		Box[][] boxGrid = new Box[Y][X];		
		
		for(int row = 0; row < Y; row++){				
			for(int column = 0; column < X; column++){
				BoxType type = null;
				Color btnColor = buttons[row][column].getBackground();					
				
				if(btnColor.equals(BoxType.getColor(BoxType.State))){
					type = BoxType.State;				
					
				}else if(btnColor.equals(BoxType.getColor(BoxType.Wall))){
					type = BoxType.Wall;
					
				}else if(btnColor.equals(Agent.getColor())){
					type = BoxType.State;

				}else if(btnColor.equals(BoxType.getColor(BoxType.Target))){
					type = BoxType.Target;

				}else if(btnColor.equals(BoxType.getColor(BoxType.Trap))){
					type = BoxType.Trap;
				}	
				boxGrid[row][column] = BoxFactory.makeBox(type);				
			}
		}
		return boxGrid;
	}
	
	
}
