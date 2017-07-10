package box;

import java.awt.Color;

public class Wall extends Box{

	public Wall(Color color){
		super(color);
		type = BoxType.Wall;
	}
}
