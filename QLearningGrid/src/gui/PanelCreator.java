package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import box.BoxType;
import box.Reward;
import box.State;
import run.Direction;

public class PanelCreator {
	private final int NUM_CELLS_IN_STATE = 9;
	
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
			double initVal;
			
			try {
				switch(lbl){
				case 1:
					initVal = stateBox.getQValue(Direction.North);						
					stateLabels[row][column][lbl].setText(Double.toString(initVal));
					break;
				case 3:
					initVal = stateBox.getQValue(Direction.West);						
					stateLabels[row][column][lbl].setText(Double.toString(initVal));
					break;
				case 5:
					initVal = stateBox.getQValue(Direction.East);						
					stateLabels[row][column][lbl].setText(Double.toString(initVal));
					break;
				case 7:
					initVal = stateBox.getQValue(Direction.South);						
					stateLabels[row][column][lbl].setText(Double.toString(initVal));
					break;
				default:
					break;						
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//for every odd index (NESW and center are all odd) 
			if(lbl % 2 == 1){								
				stateLabels[row][column][lbl].setHorizontalAlignment(JLabel.CENTER);
				stateLabels[row][column][lbl].setVerticalAlignment(JLabel.CENTER);					
			}			
			state.add(stateLabels[row][column][lbl]);
		}
		return state;
	}	
	
	private JPanel makeWallPanel(){
		JPanel wall = new JPanel();
		wall.setBackground(BoxType.getColor(BoxType.Wall));
		return wall;
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @param rewardBox
	 * @return
	 */
	private JPanel makeRewardPanel(int row, int column, Reward rewardBox){
		int topMiddle = 1;	//index of top middle square in grid			

		JPanel panel = makeNonWallPanel();
		panel.setBackground(rewardBox.getColor());

		double reward = rewardBox.getReward();
		stateLabels[row][column] = initLabels(false);
		
		for(int lbl = 0; lbl < NUM_CELLS_IN_STATE; lbl++){	
			
			if(lbl == topMiddle){
				stateLabels[row][column][lbl].setText(Double.toString(reward));						
				stateLabels[row][column][lbl].setHorizontalAlignment(JLabel.CENTER);
				stateLabels[row][column][lbl].setVerticalAlignment(JLabel.CENTER);					
			}			
			panel.add(stateLabels[row][column][lbl]);
		}
		return panel;
	}
}
