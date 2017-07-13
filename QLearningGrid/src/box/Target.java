package box;

import java.awt.Color;

public class Target extends Reward{
		
	public Target(Color _color, double _reward){
		super(_color);
		type = BoxType.Target;
		reward = _reward;
	}
}
