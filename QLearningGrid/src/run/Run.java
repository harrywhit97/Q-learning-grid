package run;

import gui.GUI;

public class Run {
	final static int SIZE = 20;
		
	final static int[] GRID_DIMENSIONS = {4,3};
	final static int[] AGENT_INTITIAL_LOCATION = {0,2};
	final static int[] TARGET_LOCATION = {3,0};
	final static int[] TRAP_LOCATION = {3,1};
	
	public static void main(String args[]){
		GUI gui = new GUI(GRID_DIMENSIONS, AGENT_INTITIAL_LOCATION, TARGET_LOCATION, TRAP_LOCATION);
		
	}
}
