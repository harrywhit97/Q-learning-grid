package run;

import box.*;

public class Run {
	final static int SIZE = 20;
		
	final static int[] GRID_DIMENSIONS = {4,3};
	final static int[] AGENT_INTITIAL_LOCATION = {0,2};
	final static int[] TARGET_LOCATION = {3,0};
	final static int[] TRAP_LOCATION = {3,1};
	final static int[] WALL_LOCATION = {1,1};


	/* INITIAL GRID SETUP:
	 S - state		 W - wall	
	 A - agent		 P - trap
	 T - target 
	 
	 S|S|S|T
	 S|W|S|P
	 A|S|S|S 	 
	 */
	
	public static void main(String args[]){
		char[][] gridChars =	{{'s','s','s','t'},
							 	 {'s','w','s','p'},
							 	 {'a','s','s','s'}};

		calcNewQValue(0.0, 0.0);
	}
	
	private static Box[][] setupGrid(int[] targetLocation, int[] trapLocation, int[] wallLocation){
		int X = 0;
		int Y = 1;
		Colour stateColour = Colour.White;
		Colour wallColour = Colour.White;
		Colour agentColour = Colour.White;
		Colour tColour = Colour.White;
				
		Box[][] grid = new Box[GRID_DIMENSIONS[X]][GRID_DIMENSIONS[Y]];
		
		grid[targetLocation[X]][targetLocation[Y]] = new Target(targetLocation[X], targetLocation[Y]);
		
		return grid;
	}
	
	private static double calcNewQValue(double oldQValue, double nextStateMaxQValue){
		double alpha = 0.2;
		double reward = -0.04;
		double gamma = 0.9;
		
		double newQValue  = oldQValue + (alpha * (reward + gamma 
								- nextStateMaxQValue - oldQValue));		
		return newQValue;
	}
}
