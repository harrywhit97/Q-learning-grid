package box;

import java.awt.Color;
import java.util.Random;

import run.Direction;

public class State extends Box{

	
	private final static int NUM_ACTIONS = 4;
	private final static double Q_INITIAL_VALUE = 0.00;
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
		QValues = new double[NUM_ACTIONS];
		
		for(int i = 0; i < NUM_ACTIONS; i++){
			QValues[i] =  Q_INITIAL_VALUE;
		}
	}
	
	public Direction getBestDirection(){
		int max = Direction.getDirectionIndex(Direction.North);
		int east = Direction.getDirectionIndex(Direction.East);
		int west = Direction.getDirectionIndex(Direction.West);
		
		for(int i = east; i <= west; i++){
			if(QValues[i] > QValues[max]){
				max = i;
			//Chance to random change if same
			}else if(QValues[i] == QValues[max]){
				Random rand = new Random();
				int r = rand.nextInt(100);
				if(r < 50){
					max = i;
				}
			}
		
		}
		
		Direction dir = null;
		try {
			dir = Direction.getByIndex(max);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dir;
	}
	
	
	public double getBestQValue(){
		Direction dir = this.getBestDirection();
		return getQValue(dir);
	}
	
	
	/**
	 * Get a specified q value of this state
	 * @param dir Direction of the q value (North, East, South, West)
	 * @return double current q value of this state at direction dir
	 */
	public double getQValue(Direction dir){		
		return QValues[Direction.getDirectionIndex(dir)];
	}
	
	/**
	 * update a specified q value of this state
	 * @param dir Direction of QValue to update (North, East, South, West)
	 * @param newQValue double new Q value to add
	 */
	public void updateQValue(Direction dir, double nextStateMaxQ){
		double oldQ = QValues[Direction.getDirectionIndex(dir)];
		QValues[Direction.getDirectionIndex(dir)] = calcNewQValue(oldQ, nextStateMaxQ);		
	}
	
	/**
	 * Calculate the new q value 
	 * @param oldQValue the old Q value of the current state
	 * @param nextStateMaxQValue the next states highest Q value
	 * @return the updated q value
	 */
	private static double calcNewQValue(double q, double maxQ){
		double alpha = 0.5;
		double r = -0.04;
		double gamma = 1.0;		
		return q + alpha * (r + gamma * maxQ - q);		
		//return Math.round(nq*1000.0)/1000.0;
	}	

}
