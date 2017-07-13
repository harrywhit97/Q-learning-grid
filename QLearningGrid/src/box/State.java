package box;

import java.awt.Color;

import run.Direction;

public class State extends Box{
	private static final int NORTH_VALUE = 0;
	private static final int EAST_VALUE = 1;
	private static final int SOUTH_VALUE = 2;
	private static final int WEST_VALUE = 3;
	
	private final int NUM_Q_VALUES = 4;
	private final double Q_INITIAL_VALUE = 0.0;
	
	private double[] QValues;
	
	/**
	 * State constructor
	 * @param stateColor colour to assign to this state
	 * @param _x x coordinate of this state
	 * @param _y y coordinate of this state
	 */
	public State(Color color){
		super(color);
		initQVals();	
		type = BoxType.State;
	}
	
	/**
	 * Initialize Q values array
	 */
	private void initQVals(){
		QValues = new double[NUM_Q_VALUES];
		
		for(int i = 0; i < NUM_Q_VALUES; i++){
			QValues[i] =  Q_INITIAL_VALUE;
		}
	}
	
	/**
	 * Get a specified q value of this state
	 * @param dir Direction of the q value (North, East, South, West)
	 * @return double current q value of this state at direction dir
	 * @throws Exception invalid direction
	 */
	public double getQValue(Direction dir) throws Exception{		
		switch(dir){
			case North: 	return QValues[NORTH_VALUE];			
			case East:		return QValues[EAST_VALUE];
			case South: 	return QValues[SOUTH_VALUE];
			case West: 		return QValues[WEST_VALUE];
			default:		throw new Exception("Invalid direction");
		}
	}
	
	/**
	 * update a specified q value of this state
	 * @param dir Direction of QValue to update (North, East, South, West)
	 * @param newQValue double new Q value to add
	 * @throws Exception Invalid direction
	 */
	public void updateQValue(Direction dir, double newQValue) throws Exception{
		switch(dir){
			case North: 
					QValues[NORTH_VALUE] = newQValue;
					break;				
			case East: 
					QValues[EAST_VALUE] = newQValue;
					break;
			case South: 
					QValues[SOUTH_VALUE] = newQValue;
					break;
			case West: 
					QValues[WEST_VALUE] = newQValue;
					break;
			default:
					throw new Exception("Invalid direction");
		}
	}
	
}
