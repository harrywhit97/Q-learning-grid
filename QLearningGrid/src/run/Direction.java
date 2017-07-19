package run;

public enum Direction {
	North, East, South, West;
	
	private static final int NORTH_VALUE = 0;
	private static final int EAST_VALUE = 1;
	private static final int SOUTH_VALUE = 2;
	private static final int WEST_VALUE = 3;
	
	private static final int[] LABEL_INDEXES = {1,5,7,3};
	
	public static int getDirectionIndex(Direction dir){
		switch(dir){
			case North:	return NORTH_VALUE;
			case East:	return EAST_VALUE;
			case South: return SOUTH_VALUE;
			default:	return WEST_VALUE;
		}
	}
	
	/**
	 * Get a direction by an indef (0-3)
	 * 0-North, 1-East, 2-South,3-West
	 * @param index
	 * @return Direction
	 * @throws Exception throws when index is not 0-3
	 */
	public static Direction getByIndex(int index) throws Exception{
		switch(index){
			case NORTH_VALUE:	return North;
			case EAST_VALUE:	return East;
			case SOUTH_VALUE: 	return South;
			case WEST_VALUE: 	return West;
			default:			throw new Exception("Invalid index");
		}		
	}
		
	/**
	 * gets the numer of the label in a 3x3 grid
	 * 
	 * #N#	#1#
	 * W#E	3#5
	 * #S#	#7#
	 */
	public static int getLabelNum(Direction dir){
		switch(dir){
			case North:	return LABEL_INDEXES[NORTH_VALUE];
			case East:	return LABEL_INDEXES[EAST_VALUE];
			case West: 	return LABEL_INDEXES[WEST_VALUE];
			default:	return LABEL_INDEXES[SOUTH_VALUE];		
		}
	}
	
	/**
	 * gets the direction of the label 
	 * 
	 * #N#	#1#
	 * W#E	3#4
	 * #S#	#5#
	 * @throws Exception 
	 */
	public static Direction getDirectionWithLabelNum(int lbl) throws Exception{
		switch(lbl){
			case 1:		return North;
			case 5:		return East;
			case 3: 	return West;
			case 7: 	return South;
			default:	throw new Exception("Invlaid lbl number : " + lbl);
		}	
	}
	
	/**
	 * Get the direction to the left of inputed direction
	 * @param dir Direction 
	 * @return 
	 */
	public static Direction getLeftOf(Direction dir){
		if(dir.equals(North)) return West;
		
		int dirIndex = getDirectionIndex(dir);
		try {
			dir = getByIndex(--dirIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dir;
	}
	
	public static Direction getRightOf(Direction dir){
		if(dir.equals(West)) return North;
		
		int dirIndex = getDirectionIndex(dir);
		try {
			dir = getByIndex(++dirIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dir;
	}
	
	public static int[] getLabelIndexes(){
		return LABEL_INDEXES;
	}
}

