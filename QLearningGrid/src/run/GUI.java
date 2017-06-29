package run;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;



public class GUI extends JFrame{
	
	private JPanel contents;
	private Color wallColor = Color.BLACK;
	private Color stateColor = Color.WHITE;
	private Color agentColor = Color.BLUE;
	private Color targetColor = Color.GREEN;
	private Color trapColor = Color.RED;
	
	private JButton[][] gridBtn;
	private JPanel[][] gridContainers; //hold states, walls,target,trap
	//private JPanel[] stateGridPanels; //hold QVals
	private JButton next;
	
	private JLabel[][] stateLbls;
	
	Border border;
	
	private int xGridLength, yGridLength, xLengthPx, yLengthPx, gridCellSize, 
				topBarSize, numTargets, numTraps, numWalls;
	
	public static void main(String args[]){
		GUI gui = new GUI(4,3);
	}
	
	/**
	 * Constructor for GUI 
	 * @param _xGridLength number of columns
	 * @param _yGridLength number of rows
	 */
	public GUI(int _xGridLength, int _yGridLength){
		
		xGridLength = _xGridLength;
		yGridLength = _yGridLength;		
		gridCellSize = 100;
		topBarSize = 50;
		
		numTargets = 1;
		numTraps = 1;
		numWalls = 0;
		
		xLengthPx = xGridLength * gridCellSize;
		yLengthPx = yGridLength * gridCellSize + topBarSize;
				
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initComponents();		
		border = BorderFactory.createLineBorder(Color.BLACK);			
		initButtons();
		
		this.add(next, BorderLayout.NORTH);
		this.add(contents);
		this.setSize(xLengthPx, yLengthPx);
		this.setResizable(false);
		this.setLocationRelativeTo(null); //center window
		this.setVisible(true);
	}
	
	private void initComponents(){
		int numEmptyLblsInState = 5;
		
		int numEmptyLabels = xGridLength * yGridLength * numEmptyLblsInState;		
		
		gridBtn = new JButton[yGridLength][xGridLength];
		gridContainers = new JPanel[yGridLength][xGridLength];
		
		
		NextButtonHandler nextButtonHandler = new NextButtonHandler();
		next = new JButton("Done");
		next.addActionListener(nextButtonHandler);
		next.setSize(next.getHeight(), 50);		
		
		contents = new JPanel();
		contents.setLayout(new GridLayout(yGridLength, xGridLength));
		
		
	}
	
	private void initQValLabels(){		
		int numLabelsInState = 9;		
		int numStates = ((xGridLength * yGridLength) - (numWalls + numTargets + numTraps));
		stateLbls = new JLabel[numStates][numLabelsInState];
	}
	
		
	private void initButtons(){
		GridButtonHandler gridButtonHandler = new GridButtonHandler();
		for(int y = 0; y < yGridLength; y++){
			for(int x = 0; x < xGridLength; x++){
				
				gridBtn[y][x] = new JButton();
				
				//set agent location (bottom left corner)
				if(y == yGridLength - 1 && x ==  0){
					gridBtn[y][x].setBackground(agentColor);
				//set target location (top right corner)
				}else if(y == 0 && x ==  xGridLength - 1){
					gridBtn[y][x].setBackground(targetColor);
					
				//set target location (top right corner)
				}else if(y == 1 && x ==  xGridLength - 1){
					gridBtn[y][x].setBackground(trapColor);
				}else{
					gridBtn[y][x].setBackground(stateColor);
					gridBtn[y][x].addActionListener(gridButtonHandler);
				}
				
				contents.add(gridBtn[y][x]);
			}
		}
	}
	

	
	private void initQValLables(){		
				
		initQValLabels();
		int nonStatesPassed = 0;
		
		for(int row = 0; row < yGridLength; row++){
			
			for(int column = 0; column < xGridLength; column++){
				
				gridContainers[row][column] = new JPanel();
				
				Color btnColor = gridBtn[row][column].getBackground();
				
				if(btnColor.equals(stateColor) || btnColor.equals(agentColor)){
					
					makeSetStatePanel(row, column, btnColor.equals(agentColor), nonStatesPassed);
					
				}else if(btnColor.equals(wallColor)){
					gridContainers[row][column].setBackground(wallColor);
					nonStatesPassed++;
				}else if(btnColor.equals(targetColor)){
					gridContainers[row][column].setBackground(targetColor);
					nonStatesPassed++;
				}else if(btnColor.equals(trapColor)){
					gridContainers[row][column].setBackground(trapColor);
					nonStatesPassed++;
				}					

				gridContainers[row][column].setBorder(border);				
			}
		}
	}
		
		private void makeSetStatePanel(int row, int column, boolean hasAgent, int numNonStatesPassed){
			int numCellsStatePanel = 9;		
			int stateGridSize = 3;	
			int numEmptyLabelsUsed = 0;
			int centerElement = 4;
			double stateInitValue = 0.0;
			String initQ = Double.toString(stateInitValue);
			
			gridContainers[row][column].setBackground(stateColor);
			gridContainers[row][column].setLayout(new GridLayout(stateGridSize, stateGridSize));
			for(int lbl = 0; lbl < numCellsStatePanel; lbl++){
				int state = (row * yGridLength) + column - numNonStatesPassed;	
				stateLbls[state][lbl] = new JLabel();
				stateLbls[state][lbl].setOpaque(true);
				
				if(lbl % 2 == 1){
					stateLbls[state][lbl].setText(initQ);									
					stateLbls[state][lbl].setHorizontalAlignment(JLabel.CENTER);
					stateLbls[state][lbl].setVerticalAlignment(JLabel.CENTER);					
				}else if (hasAgent && lbl == centerElement){		
					stateLbls[state][lbl].setBackground(agentColor);
				}
				gridContainers[row][column].add(stateLbls[state][lbl]);
			}
		}
	

	
	/**
	 * Action Handler for next button on wall selector page
	 * @author harry
	 *
	 */
	private class NextButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			next.setEnabled(false);
						
			contents.removeAll();
			contents.setLayout(new GridLayout(yGridLength, xGridLength));
			contents.setBorder(border);
			contents.repaint();
			
			initQValLables();
			
			for(int y = 0; y < yGridLength; y++){
				for(int x = 0; x < xGridLength; x++){
					contents.add(gridContainers[y][x]);
				}
			}				
			GUI.this.pack();
			GUI.this.setSize(xLengthPx, yLengthPx);
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
						if(gridBtn[y][x].getBackground().equals(stateColor)){
							gridBtn[y][x].setBackground(wallColor);
							numWalls++;
						}else{
							gridBtn[y][x].setBackground(stateColor);
							numWalls--;
						}
					}
				}
			}
		}
	}
}


