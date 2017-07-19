package run;

import gui.GUI;

public class Run {
	final static int SIZE = 20;
	final static int X = 0;
	final static int Y = 1;
	final static int[] GRID_DIMENSIONS = {4,4};
	final static int[] AGENT_INTITIAL_LOCATION = {0,GRID_DIMENSIONS[Y]-1};
	final static int[] TARGET_LOCATION = {GRID_DIMENSIONS[X]-1,0};
	final static int[] TRAP_LOCATION = {GRID_DIMENSIONS[X]-1,1};
	
	public static void main(String args[]){
		GUI gui = new GUI(GRID_DIMENSIONS, AGENT_INTITIAL_LOCATION, TARGET_LOCATION, TRAP_LOCATION);		
	}
}
