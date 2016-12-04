
/**
 * @author Derek Lopes
 * Source credit to "Data Structures and Algorithms in Java" by Robert Lafore
 * 2 Ed. Chapter 13
 */
class Maze {
	private final int mazeSize, // size of the maze (MAX_SIZE x MAX_SIZE)
					  maxRooms; // maximum number of rooms in maze
	private Room roomList[]; // array of rooms
	private int adjMat[][]; // adjacency matrix
	
	/**
	 * Creates a maze with full walls (no doors).
	 * @param size the size of the maze (size x size).
	 */
	public Maze(int size) {
		mazeSize = size;
		maxRooms = mazeSize * mazeSize;
		adjMat = new int[maxRooms][maxRooms];
		for(int i = 0; i < mazeSize; i++) {
			for(int j = 0; j < mazeSize; j++) {
				adjMat[i][j] = 0;
			}
		}
	}
	
	/**
	 * If the rooms are not adjacent, no door is added.
	 * Otherwise, adds a connecting door between two rooms.
	 * @param start start room
	 * @param end end room
	 */
	public void addDoor(int start, int end) {
		if(checkAdjacent(start, end)) {
			adjMat[start][end] = 1;
			adjMat[end][start] = 1;
		} else {
			System.err.println("Error creating door: rooms not adjacent.");
		}
	}
	
	/**
	 * Prints the label for a specific room number.
	 * @param r the room number to print
	 */
	public void displayRoom(int r) {
		System.out.println(roomList[r].label);
	}
	
	/**
	 * @param start first room
	 * @param end second room
	 * @return if the two rooms are horizontally or vertically adjacent to each other
	 */
	private boolean checkAdjacent(int start, int end) {
		// + or - 1
		if(start + 1 == end || start - 1 == end)
			return true;
		// + or - MAZE_SIZE
		if(start + mazeSize == end || start - mazeSize == end)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		String maze = new String();
		// top border
		maze += "+ ";
		for(int i = 1; i < mazeSize; i++) {
			maze += "+-";
		}
		maze += "+\n";
		
		// insides
		for(int i = 0; i < mazeSize; i++) {
			// test horizontal connections
			maze += "| ";
			int start = i * mazeSize;
			int finish = start + mazeSize;
			for(int j = start; j < finish - 1; j++) {
				if(adjMat[j][j+1] == 1)
					maze += "  ";
				else
					maze += "| ";
			}
			maze += "|\n";
			// test vertical connections
			if(i != mazeSize - 1) {
				maze += "+";
				for(int j = start; j < finish; j++) {
					if(adjMat[j][j + mazeSize] == 1)
						maze += " +";
					else
						maze += "-+";
				}
				maze += "\n";
			}
		}
		
		// bottom border
		for(int i = 0; i < mazeSize - 1; i++) {
			maze += "+-";
		}
		maze += "+ +";
		
		return maze;
	}
}
