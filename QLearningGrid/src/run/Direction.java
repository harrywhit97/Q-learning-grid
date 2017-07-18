package run;

public enum Direction {
	North, East, South, West;
	
	private static final int NORTH_VALUE = 0;
	private static final int EAST_VALUE = 1;
	private static final int SOUTH_VALUE = 2;
	private static final int WEST_VALUE = 3;
	
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
	
	//FIX THIS
	
	/**
	 * gets the numer of the label in a 3x3 grid
	 * 
	 * #N#	#1#
	 * W#E	3#4
	 * #S#	#5#
	 */
	public static int getLabelNum(Direction dir){
		switch(dir){
			case North:	return 1;
			case East:	return 5;
			case West: 	return 3;
			case South:	return 7;
		}	
		return 0;
	}
}

