package box;

import java.awt.Color;

public class Trap extends Box {
	public Trap(Color color){
		super(color);
		type = BoxType.Trap;
	}
}
