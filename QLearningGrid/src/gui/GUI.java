package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import box.*;
import box.Box;
import run.*;


//To Do: Clean up

public class GUI extends JFrame{
	
	private final int GRID_CELL_SIZE = 100;
	private final int TOP_BAR_SIZE = 50;
	private final int NUM_CELLS_IN_STATE = 9;
	
	private JPanel contents;	
	private JButton[][] gridBtn;
	private JPanel[][] gridContainers; //hold states, walls,target,trap
	private JButton next;	
	private JLabel[][][] stateLabels;
	private Border border;
	
	private Box[][] boxGrid;
	private Agent agent;	
	
	private int xGridLength, yGridLength, xLengthPx, yLengthPx;	
	
	/**
	 * Constructor for GUI 
	 * @param _xGridLength number of columns
	 * @param _yGridLength number of rows
	 */
	public GUI(int _xGridLength, int _yGridLength){
		
		xGridLength = _xGridLength;
		yGridLength = _yGridLength;		
		xLengthPx = xGridLength * GRID_CELL_SIZE;
		yLengthPx = yGridLength * GRID_CELL_SIZE + TOP_BAR_SIZE;
				
		this.setLayout(new BorderLayout());		
		
		initComponents();
		initFrame();
	}
	
	/**
	 * Initialize containers and components
	 */
	private void initComponents(){
		
		gridBtn = new JButton[yGridLength][xGridLength];
		gridContainers = new JPanel[yGridLength][xGridLength];		
		
		NextButtonHandler nextButtonHandler = new NextButtonHandler();
		next = new JButton("Done");
		next.addActionListener(nextButtonHandler);
		next.setSize(next.getHeight(), 50);		
		
		contents = new JPanel();
		contents.setLayout(new GridLayout(yGridLength, xGridLength));		
		
		border = BorderFactory.createLineBorder(Color.BLACK);	
		
		stateLabels = new JLabel[yGridLength][xGridLength][NUM_CELLS_IN_STATE];
		initButtons();
	}
	
	/**
	 * Initialize grid buttons for wall selector GUI	
	 */
	private void initButtons(){
		GridButtonHandler gridButtonHandler = new GridButtonHandler();
		for(int y = 0; y < yGridLength; y++){
			for(int x = 0; x < xGridLength; x++){
				
				gridBtn[y][x] = new JButton();
				Color color = null;
				
				//set agent location (bottom left corner)
				if(y == yGridLength - 1 && x ==  0){
					color = Agent.getColor();
				//set target location (top right corner)
				}else if(y == 0 && x ==  xGridLength - 1){
					color = BoxType.getColor(BoxType.Target);
					
				//set trap location (2nd from top right corner)
				}else if(y == 1 && x ==  xGridLength - 1){
					color = BoxType.getColor(BoxType.Trap);
				}else{
					color = BoxType.getColor(BoxType.State);
					gridBtn[y][x].addActionListener(gridButtonHandler);
				}
				gridBtn[y][x].setBackground(color);
				contents.add(gridBtn[y][x]);
			}
		}
	}
	
	/**
	 * Initialize frame settings
	 */
	private void initFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(next, BorderLayout.NORTH);
		this.add(contents);
		this.setSize(xLengthPx, yLengthPx);
		this.setResizable(false);
		this.setLocationRelativeTo(null); //center window
		this.setVisible(true);
	}
	
	private void paintAgent(){
		int agentCell = 4;
		int agentX = agent.getX();
		int agentY = agent.getY();
		stateLabels[agentY][agentX][agentCell].setBackground(Agent.getColor());
	}
	
	/**
	 * Action Handler for next button on wall selector page
	 * @author harry
	 */
	private class NextButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			next.setEnabled(false);
						
			contents.removeAll();
			contents.setLayout(new GridLayout(yGridLength, xGridLength));
			contents.setBorder(border);
			
			boxGrid = makeGridBoxesAndAgent();
			makeGUIGrid(boxGrid);
			
			for(int y = 0; y < yGridLength; y++){
				for(int x = 0; x < xGridLength; x++){
					contents.add(gridContainers[y][x]);
				}
			}
			
			paintAgent();
			GUI.this.pack();
			GUI.this.setSize(xLengthPx, yLengthPx);
		}
		
		private void makeGUIGrid(Box[][] boxGrid){
			for(int row = 0; row < boxGrid.length; row++){
				for(int column = 0; column < boxGrid[row].length; column++){
					
					switch(boxGrid[row][column].getBoxType()){
						case State: 
							gridContainers[row][column] = makeStatePanel(row, column, (State)boxGrid[row][column]);
							break;
						case Wall: 
							gridContainers[row][column] = makeWallPanel();
							break;
						case Target: 
							Reward rTarget = (Reward)boxGrid[row][column];
							gridContainers[row][column] = makeRewardPanel(row, column, rTarget);
							break;
						case Trap: 
							Reward rTrap = (Reward)boxGrid[row][column];
							gridContainers[row][column] = makeRewardPanel(row, column, rTrap);
							break;
					}
				}
			}
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
		
		/**
		 * Make the grid from Boxes (State, target, trap, wall) and make agent 
		 */
		private Box[][] makeGridBoxesAndAgent(){
			Box[][] boxGrid = new Box[yGridLength][xGridLength];
			
			int agentX = -1;
			int agentY = -1;
			
			
			for(int row = 0; row < yGridLength; row++){
				
				for(int column = 0; column < xGridLength; column++){
					BoxType type = null;
					Color btnColor = gridBtn[row][column].getBackground();
					
					
					if(btnColor.equals(BoxType.getColor(BoxType.State))){
						type = BoxType.State;				
						
					}else if(btnColor.equals(BoxType.getColor(BoxType.Wall))){
						type = BoxType.Wall;
						
					}else if(btnColor.equals(Agent.getColor())){
						type = BoxType.State;
						agentX = column;
						agentY = row;

					}else if(btnColor.equals(BoxType.getColor(BoxType.Target))){
						type = BoxType.Target;

					}else if(btnColor.equals(BoxType.getColor(BoxType.Trap))){
						type = BoxType.Trap;
					}	
					boxGrid[row][column] = BoxFactory.makeBox(type);
				}
			}
			agent = new Agent(agentX, agentY);
			return boxGrid;
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
	}	
	
	/**
	 * Action Handler for wall state toggle buttons
	 * @author harry
	 *
	 */
	private class GridButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			
			for(int y = 0; y < yGridLength; y++){
				for(int x = 0; x < xGridLength; x++){
					
					//toggle between wall and state
					if(source == gridBtn[y][x]){
						if(gridBtn[y][x].getBackground().equals(
								BoxType.getColor(BoxType.State))){
							
							gridBtn[y][x].setBackground(BoxType.getColor(BoxType.Wall));
						}else{
							gridBtn[y][x].setBackground(BoxType.getColor(BoxType.State));
						}
					}
				}
			}
		}
	}
}


