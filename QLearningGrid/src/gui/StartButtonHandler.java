package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import box.Box;
import box.BoxType;
import box.Reward;
import box.State;
import run.Direction;

private class StartButtonHandler implements ActionListener{
	private int X = 0;
	private int Y = 1;
	private int maxPercent = 100;
	private int maxDirection = 4;
	private int eps = 10; //10% of time do random action
	private Random randomGenerator;
	
	public void actionPerformed(ActionEvent e){
		randomGenerator = new Random();
		
		while(true){
			int[] agentLoc = {agent.getX(), agent.getY()};
			paintAgent(false);
			Box currentBox = boxGrid[agentLoc[X]][agentLoc[Y]];
			
			if(currentBox.getBoxType().equals(BoxType.State)){
				
				State currentState = (State) boxGrid[agentLoc[X]][agentLoc[Y]];					
				makeMove(getDirectionToGo(currentState), agentLoc, currentState);
				
			}else{	// has reached a reward
				agent.resetToInitial();
			}
			paintAgent(true);
			GUI.this.validate();
			sleep(500);				
		}			
	}
	
	private Direction getDirectionToGo(State currentState){
		Direction dir = null;
		int randNum = randomGenerator.nextInt(maxPercent);
		if(randNum > eps){	
			dir = currentState.getBestDirection();
			
		}else{	//do random action
			randNum = randomGenerator.nextInt(maxDirection);
			try {
				dir = Direction.getByIndex(randNum);
			} catch (Exception e1) {
				e1.printStackTrace();
			}						
		}
		return dir;
	}
	
	private void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * moves agent if needed, update relevent Q value
	 * @param dir
	 */
	private void makeMove(Direction dir, int[] agentLoc, State currentState){
		
		double nextStateMaxQ = State.getInitialQValue();			
		int[] newBoxLoc = getNextBoxCoords(agentLoc[X], agentLoc[Y], dir);		
		
		if(isValidBox(newBoxLoc)){
			Box nextBox = boxGrid[newBoxLoc[Y]][newBoxLoc[X]];
			BoxType  type = nextBox.getBoxType();
			if(type.equals(BoxType.State)){
				State state = (State)nextBox;
				nextStateMaxQ = state.getBestQValue();
			}else if(nextBox.isRewardBox()){
				Reward reward = (Reward)nextBox;
				nextStateMaxQ = reward.getReward();
			}
			agent.move(dir);
		}
		currentState.updateQValue(dir, nextStateMaxQ);
		
		double q = currentState.getQValue(dir);
		String Qval = Double.toString(q);
		if(Qval.contains("-")){
			Qval = Qval.substring(0, 4);
		}else{
			Qval =  " " + Qval.substring(0, 3);
		}
		
		stateLabels[agentLoc[Y]][agentLoc[X]][Direction.getDirectionIndex(dir)].setText(Qval);		
		System.out.println(Qval);
	}
	
	/**
	 * Checks if agent can enter box
	 * @param coords
	 * @return true if agent can enter and false if box is
	 * outside of bounds or a wall
	 */
	private boolean isValidBox(int[] coords){
		boolean valid = true;
		if(coords[X] < 0 || coords[X] >= xGridLength)		valid = false;
		if(coords[Y] < 0 || coords[Y] >= yGridLength)		valid = false;
		if(boxGrid[Y][X].getBoxType().equals(BoxType.Wall))	valid = false;			
		return valid;
	}
	
	private int[] getNextBoxCoords(int x, int y, Direction dir){			
		switch(dir){
			case North:
				y--;
				break;
			case East:
				x++;			
				break;
			case South:
				y++;
				break;
			case West:
				x--;
				break;
		}
		int[] coords = {x,y};
		return coords;
	}
}
