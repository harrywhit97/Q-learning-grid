package box;

import java.awt.Color;

/**
 * Superclass Box
 * @author harry
 *
 */
public abstract class Box {
	public static int boxSize = 20;
	protected Color color;
	protected BoxType type;
	
	public Box(Color _color){
		color = _color;
	}
	
	/**
	 * Get the colour of this box
	 * @return Colour that has been assigned to this box
	 */
	public Color getColor(){ 
		return color;
	}
	
	public BoxType getBoxType(){
		return type;
	}
	
}
