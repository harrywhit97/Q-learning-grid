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
	private JPanel[][] gridContainers;
	private JButton next;
	
	private JLabel[][][] QvalLbls;
	private JLabel[] emptyLabels;
	
	Border border;
	
	private int xGridLength, yGridLength, xLengthPx, yLengthPx, gridCellSize, topBarSize;
	
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
		QvalLbls = new JLabel[yGridLength][xGridLength][4];
		
		NextButtonHandler nextButtonHandler = new NextButtonHandler();
		next = new JButton("Done");
		next.addActionListener(nextButtonHandler);
		next.setSize(next.getHeight(), 50);		
		
		contents = new JPanel();
		contents.setLayout(new GridLayout(yGridLength, xGridLength));
		
		emptyLabels = new JLabel[numEmptyLabels];
		
		for(int i = 0; i < numEmptyLabels; i++){
			emptyLabels[i] = new JLabel();
		}
	}
	
	private void initButtons(){
		GridButtonHandler gridButtonHandler = new GridButtonHandler();
		for(int y = 0; y < yGridLength; y++){
			for(int x = 0; x < xGridLength; x++){
				
				gridBtn[y][x] = new JButton();
				//gridBtn[y][x].setPreferredSize(new Dimension(30,30));
				
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
		int numCellsStatePanel = 9;
		int stateGridSize = 3;
		
		int numEmptyLabelsUsed = 0;
		for(int row = 0; row < yGridLength; row++){
			
			for(int column = 0; column < xGridLength; column++){
				
				gridContainers[row][column] = new JPanel();
				gridContainers[row][column].setLayout(new GridLayout(stateGridSize, stateGridSize));
				gridContainers[row][column].setBorder(border);
				int numLblsAdded = 0;
				for(int lbl = 0; lbl < numCellsStatePanel; lbl++){
													
					if(lbl % 2 == 1){
						QvalLbls[row][column][numLblsAdded] = new JLabel("0.0");									
						QvalLbls[row][column][numLblsAdded].setHorizontalAlignment(JLabel.CENTER);
						QvalLbls[row][column][numLblsAdded].setVerticalAlignment(JLabel.CENTER);
						gridContainers[row][column].add(QvalLbls[row][column][numLblsAdded++]);
						
					}else{						
						gridContainers[row][column].add(emptyLabels[numEmptyLabelsUsed++]);
					}
				}
			}
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
						}else{
							gridBtn[y][x].setBackground(stateColor);
						}
					}
				}
			}
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
}


