package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import box.*;
import box.Box;
import run.*;

/**
 * 
 * @author harry
 *
 */
public class GUI{
	
	private final String FRAME_TITLE = "Q Learning Grid";
	private final int GRID_CELL_SIZE = 100;
	private final int TOP_BAR_SIZE = 50;
	protected final int NUM_CELLS_IN_STATE = 9;
	private final int X = 0;
	private final int Y = 1;
	
	private static JFrame frame;
	protected static JPanel contents, gridContainers[][];	//hold states, walls,target,trap
	private JButton[][] gridBtn;
	private JButton next;	
	protected static JLabel[][][] stateLabels;	//[Y][X][0-9]
	protected Border border;
	
	private Box[][] boxGrid;
	private Agent agent;	
	
	protected int[] gridSize;	// width and length of frame
	private int[] gridSizePx;	//pixel size (x,y) of frame
	
	
	
	/**
	 * Constructor for GUI 
	 * @param _xGridLength number of columns
	 * @param _yGridLength number of rows
	 */
	public GUI(int[] gridDimensions, int[] agentLoc, int[] target, int[] trap){
		
		frame = new JFrame(FRAME_TITLE);
		
		gridSize = gridDimensions;		
		gridSizePx = new int[gridSize.length];
		gridSizePx[X] = gridSize[X] * GRID_CELL_SIZE;
		gridSizePx[Y] = gridSize[Y] * GRID_CELL_SIZE + TOP_BAR_SIZE;
				
		frame.setLayout(new BorderLayout());		
		
		agent = Agent.getInstance(agentLoc[X], agentLoc[Y]);
		
		initComponents();
		initButtons(target, trap);
		initFrame();
	}
	
	/**
	 * Initialize containers and components
	 */
	private void initComponents(){		
		gridBtn = new JButton[gridSize[Y]][gridSize[X]];
		gridContainers = new JPanel[gridSize[Y]][gridSize[X]];		
		
		NextButtonHandler nextButtonHandler = new NextButtonHandler();
		next = new JButton("Done");
		next.addActionListener(nextButtonHandler);
		next.setSize(next.getHeight(), 50);		
		
		contents = new JPanel();
		contents.setLayout(new GridLayout(gridSize[Y], gridSize[X]));		
		
		border = BorderFactory.createLineBorder(Color.BLACK);	
		
		stateLabels = new JLabel[gridSize[Y]][gridSize[X]][NUM_CELLS_IN_STATE];
	}
	
	/**
	 * Initialize grid buttons for wall selector GUI	
	 * @param agent agent x,y
	 * @param target target x,y
	 * @param trap trap x,y
	 */
	private void initButtons(int[] target, int[] trap){
		GridButtonHandler gridButtonHandler = new GridButtonHandler();
		int X = 0;
		int Y = 1;
		
		for(int y = 0; y < gridSize[Y]; y++){
			for(int x = 0; x < gridSize[X]; x++){
				
				gridBtn[y][x] = new JButton();
				Color color = null;

				if(y == agent.getY() && x ==  agent.getX()){
					color = Agent.getColor();

				}else if(y == target[Y] && x ==  target[X]){
					color = BoxType.getColor(BoxType.Target);					
				
				}else if(y == trap[Y] && x ==  trap[X]){
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(next, BorderLayout.NORTH);
		frame.add(contents);
		frame.setSize(gridSizePx[X], gridSizePx[Y]);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); //center window
		frame.setVisible(true);
	}
	
	/**
	 * Paint the agent on to the frame if paint = true
	 * if paint = false unpaint agent 
	 */
	private void paintAgent(boolean paint){
		int agentCell = 4;
		int agentX = agent.getX();
		int agentY = agent.getY();
		Color color = Color.GRAY;
		if(paint){
			color = Agent.getColor();
		}
		stateLabels[agentY][agentX][agentCell].setBackground(color);		
	}
		
	/**
	 * Action Handler for next button on wall selector page
	 * @author harry
	 */
	private class NextButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e){						
			contents.removeAll();
			contents.setLayout(new GridLayout(gridSize[Y], gridSize[X]));
			contents.setBorder(border);
			
			boxGrid = Grid.makeGridBoxes(gridBtn);
			makeGUIGrid(boxGrid);
						
			for(int y = 0; y < gridSize[Y]; y++){
				for(int x = 0; x < gridSize[X]; x++){
					contents.add(gridContainers[y][x]);
				}
			}
			
			paintAgent(true);
			next.setText("Start");
			StartButtonHandler startButtonHandler = new StartButtonHandler();
			next.addActionListener(startButtonHandler);
			frame.pack();
			frame.setSize(gridSizePx[X], gridSizePx[Y]);
		}
		
		/**
		 * Populate the gridContainers 2d panel array with relevent panels
		 * be using the Box grid
		 * @param boxGrid 2d array of Boxes where each box is a wall,state,trap,target
		 */
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
	
	/**
	 * Starts the QLearning
	 * @author harry
	 *
	 */
	private class StartButtonHandler implements ActionListener{
		
		private class Move extends SwingWorker<Boolean, Integer>{
			
			protected Boolean doInBackground() throws Exception{
				
				while(true){
					paintAgent(false);
					int[] agentLoc = {agent.getX(), agent.getY()};				
					Box currentBox = boxGrid[agentLoc[Y]][agentLoc[X]];
					
					if(currentBox.getBoxType().equals(BoxType.State)){
						
						State currentState = (State) boxGrid[agentLoc[Y]][agentLoc[X]];					
						makeMove(agent.getDirectionToGo(currentState), agentLoc, currentState);
						
					}else{	// has reached a reward
						agent.resetToInitial();
					}
					paintAgent(true);
					frame.revalidate();
					frame.repaint();
					sleep(250);			
				}
			}			
			
			protected void done(){				
			}
		}
		
		public void actionPerformed(ActionEvent e){
			new Move().execute();								
		}
		
		/**
		 * moves agent if needed, update relevent Q value
		 * @param dir
		 */
		private void makeMove(Direction dir, int[] agentLoc, State currentState){			
			double nextStateMaxQ = currentState.getBestQValue();			
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
			
			int lbl = Direction.getLabelNum(dir);
			double Qval = currentState.getQValue(dir);
			updateStateLabel(agentLoc[Y], agentLoc[X], lbl, Qval);
		}
		
			
		
		private void updateStateLabel(int row, int column, int lbl, double Qval){
			double rQval = Math.round(Qval*100.0)/100.0;			
			String sQval = Double.toString(rQval);										
			stateLabels[row][column][lbl].setText(sQval);
			stateLabels[row][column][lbl].setBackground(getLabelColor(rQval));
		}
		
		private Color getLabelColor(double Qval){
			int c = Math.abs((int)(Qval * 255));
			
			if(Qval > 0) 			return new Color(0,c,0);
			else if(Qval < -0.2)	return new Color(c,0,0);
			else 					return BoxType.getColor(BoxType.State);
		}		 
		
		private void sleep(int time){
			try {
				Thread.sleep(time);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
				
		/**
		 * Checks if agent can enter box
		 * @param coords
		 * @return true if agent can enter and false if box is
		 * outside of bounds or a wall
		 */
		private boolean isValidBox(int[] coords){
			if(coords[X] < 0 || coords[X] >= gridSize[X])		return false;
			if(coords[Y] < 0 || coords[Y] >= gridSize[Y])		return false;
			if(boxGrid[coords[Y]][coords[X]].getBoxType().equals(BoxType.Wall)){
				return false;			
			}
			return true;
		}
		
		/**
		 * Get the coordinates of the next boxfrom the 
		 * @param x
		 * @param y
		 * @param dir
		 * @return
		 */
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
	
	/**
	 * Action Handler for wall state toggle buttons
	 * @author harry
	 *
	 */
	private class GridButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			Color stateColor = BoxType.getColor(BoxType.State);
			
			for(int y = 0; y < gridSize[Y]; y++){
				for(int x = 0; x < gridSize[X]; x++){
					
					//toggle between wall and state
					if(source == gridBtn[y][x]){
						if(gridBtn[y][x].getBackground().equals(stateColor)){							
							gridBtn[y][x].setBackground(BoxType.getColor(BoxType.Wall));
						}else{
							gridBtn[y][x].setBackground(stateColor);
						}
					}
				}
			}
		}
	}
}