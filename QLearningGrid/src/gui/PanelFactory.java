package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import box.Box;
import box.BoxFactory;
import box.BoxType;
import box.Reward;
import box.State;
import run.Agent;
import run.Direction;

public class PanelFactory extends GUI{
	
	
	
	public PanelFactory(int[] gridDimensions, int[] agent, int[] target, int[] trap) {
		super(gridDimensions, agent, target, trap);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Makes a JPanel for a state
	 * @param row row in the grid where this panel will be placed (used to assign labels)
	 * @param column column in the grid where this panel will be placed (used to assign labels)
	 * @return State
	 */
	private JPanel makeStatePanel(int row, int column, State stateBox){			
		JPanel state = makeNonWallPanel();			
		state.setBackground(BoxType.getColor(BoxType.State));					
		stateLabels[row][column] = initLabels(true);
		
		for(int lbl = 0; lbl < NUM_CELLS_IN_STATE; lbl++){
							
			//for every odd index (NESW and center are all odd) 
			if(lbl % 2 == 1){	
				Direction dir = getDirectionFromLbl(lbl);
				
				double val = stateBox.getQValue(dir);
				String initVal = Double.toString(val);				
				stateLabels[row][column][lbl].setText(initVal);
				centerLabel(row, column, lbl);				
			}			
			state.add(stateLabels[row][column][lbl]);
		}
		return state;
	}	
	
	private Direction getDirectionFromLbl(int lbl){
		try {
			return Direction.getDirectionWithLabelNum(lbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void centerLabel(int row, int column, int lbl){					
			stateLabels[row][column][lbl].setHorizontalAlignment(JLabel.CENTER);
			stateLabels[row][column][lbl].setVerticalAlignment(JLabel.CENTER);		
	}
	
	/**
	 * Makes a wall panel
	 * @return wall panel
	 */
	private JPanel makeWallPanel(){
		JPanel wall = new JPanel();
		wall.setBackground(BoxType.getColor(BoxType.Wall));
		return wall;
	}
	
	/**
	 * Makes a JPanel and sets the layout to grid layout with 9 cells
	 * @return JPanel
	 */
	private JPanel makeNonWallPanel(){
		int gridSquareLength = 3;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(gridSquareLength, gridSquareLength));
		panel.setBorder(border);
		return panel;
	}	
	
	/**
	 * Makes a target or trap panel 
	 * @param row y coordinate of box in grid
	 * @param column x coordinate of box in grid
	 * @param rewardBox reward box to make panel for
	 * @return Panel
	 */
	private JPanel makeRewardPanel(int row, int column, Reward rewardBox){
		int topMiddle = 1;	//index of top middle square in grid	
		int agentLbl = 4;	//index of top middle square in grid	
		double reward = rewardBox.getReward();
		
		JPanel panel = makeNonWallPanel();
		panel.setBackground(rewardBox.getColor());	
		
		stateLabels[row][column] = initLabels(false);
		stateLabels[row][column][topMiddle].setText(Double.toString(reward));						
		stateLabels[row][column][topMiddle].setHorizontalAlignment(JLabel.CENTER);
		stateLabels[row][column][topMiddle].setVerticalAlignment(JLabel.CENTER);
		panel.add(stateLabels[row][column][topMiddle]);
		panel.add(stateLabels[row][column][agentLbl]);
		return panel;
	}
	
					

	/**
	 * makes an array of 9 labels and makes them opaque 
	 * @return JLabel[9]
	 */
	private JLabel[] initLabels(boolean isState){
		JLabel[] labels = new JLabel[NUM_CELLS_IN_STATE];  
		int agentLbl = 4;
		for(int lbl = 0; lbl < NUM_CELLS_IN_STATE; lbl++){
			labels[lbl] = new JLabel();
			if(isState || lbl == agentLbl){
				labels[lbl].setOpaque(true);
			}				
		}
		return labels;
	}
}
