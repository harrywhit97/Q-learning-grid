package run;

import box.*;
import gui.GUI;

public class Run {
	final static int SIZE = 20;
		
	final static int[] GRID_DIMENSIONS = {4,3};
	final static int[] AGENT_INTITIAL_LOCATION = {0,2};
	final static int[] TARGET_LOCATION = {3,0};
	final static int[] TRAP_LOCATION = {3,1};
	final static int[] WALL_LOCATION = {1,1};



	
	public static void main(String args[]){
		GUI gui = new GUI(GRID_DIMENSIONS[0], GRID_DIMENSIONS[1]);
		
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
