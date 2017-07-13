package box;

import java.awt.Color;

public class Trap extends Reward {
	
	public Trap(Color _color, double _reward){
		super(_color);
		type = BoxType.Trap;
		reward = _reward;
	}
}
