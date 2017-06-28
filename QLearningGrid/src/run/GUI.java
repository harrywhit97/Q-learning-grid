package run;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends JFrame{
	
	private JPanel contents;
	private Color wallColor = Color.BLACK;
	private Color stateColor = Color.WHITE;
	private Color agentColor = Color.BLUE;
	private Color targetColor = Color.GREEN;
	private Color trapColor = Color.RED;
	
	private JButton[][] gridBtn;
	
	private int xLength, yLength;
	
	public GUI(int _xLength, int _yLength){
		xLength = _xLength;
		yLength = _yLength;
		
		gridBtn = new JButton[yLength][xLength];
		
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contents = new JPanel();
		contents.setLayout(new GridLayout(yLength, xLength));
		
		ButtonHandler buttonHandler = new ButtonHandler();
		
		for(int y = 0; y < yLength; y++){
			for(int x = 0; x < xLength; x++){
				
				gridBtn[y][x] = new JButton();
				
				//set agent location (bottom left corner)
				if(y == yLength - 1 && x ==  0){
					gridBtn[y][x].setBackground(agentColor);
				//set target location (top right corner)
				}else if(y == 0 && x ==  xLength - 1){
					gridBtn[y][x].setBackground(targetColor);
					
				//set target location (top right corner)
				}else if(y == 1 && x ==  xLength - 1){
					gridBtn[y][x].setBackground(trapColor);
				}else{
					gridBtn[y][x].setBackground(stateColor);
					gridBtn[y][x].addActionListener(buttonHandler);
				}
				contents.add(gridBtn[y][x]);
			}
		}
		this.add(new JLabel("Click for wall"), BorderLayout.NORTH);
		this.add(contents);
		this.setSize(500, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null); //center window
		this.setVisible(true);
	}
	
	
	public static void main(String args[]){
		GUI gui = new GUI(4,3);
	}
	
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			
			for(int y = 0; y < yLength; y++){
				for(int x = 0; x < xLength; x++){
					
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
	
}


