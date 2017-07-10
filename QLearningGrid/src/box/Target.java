package box;

import java.awt.Color;

public class Target extends Box{
	
	private double reward = 1.0;
	
	public Target(Color color){
		super(color);
		type = BoxType.Target;
	}
	
	public double getReward(){
		return reward;
	}
}
