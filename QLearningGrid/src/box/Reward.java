package box;

import java.awt.Color;

public abstract class Reward extends Box{
	
	protected double reward;
	
	public Reward(Color _color) {
		super(_color);
	}
	
	public double getReward(){
		return reward;
	}
}
