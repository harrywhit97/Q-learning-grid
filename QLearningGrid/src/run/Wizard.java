package run;

import gui.GUI;

public class Wizard {
	final static int SIZE = 20;
	final static int X = 0;
	final static int Y = 1;
	final static int[] GRID_DIMENSIONS = {4,3};	// cant be smaller than 2x2
	final static int[] AGENT_INTITIAL_LOCATION = {0,GRID_DIMENSIONS[Y]-1};
	final static int[] TARGET_LOCATION = {GRID_DIMENSIONS[X]-1,0};
	final static int[] TRAP_LOCATION = {GRID_DIMENSIONS[X]-1,1};
	
	public static void main(String args[]) throws Exception{
		if(GRID_DIMENSIONS[X] < 2 || GRID_DIMENSIONS[Y] < 2){
			throw new Exception("Invalid grid size");
		}else{
			GUI gui = new GUI(GRID_DIMENSIONS, AGENT_INTITIAL_LOCATION, TARGET_LOCATION, TRAP_LOCATION);	
		}			
	}
}
