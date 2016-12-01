
/**
 * @author Derek Lopes
 * Source credit to "Data Structures and Algorithms in Java" by Robert Lafore
 * 2 Ed. Chapter 13
 */
class Maze {
	private final int MAZE_SIZE, // size of the maze (MAX_SIZE x MAX_SIZE)
					  MAX_ROOMS; // maximum number of rooms in maze
	private Room roomList[]; // array of rooms
	private int adjMat[][]; // adjacency matrix
	private int nRooms; // current number of rooms
	
	/**
	 * Creates a maze with full walls (no doors).
	 * @param size the size of the maze (size x size).
	 */
	public Maze(int size) {
		MAZE_SIZE = size;
		MAX_ROOMS = MAZE_SIZE * MAZE_SIZE;
		adjMat = new int[MAX_ROOMS][MAX_ROOMS];
		nRooms = 0;
		for(int i = 0; i < MAZE_SIZE; i++) {
			for(int j = 0; j < MAZE_SIZE; j++) {
				adjMat[i][j] = 0;
			}
		}
	}
	
	/**
	 * Adds a new room to the maze.
	 * @param label name of room to add
	 */
	public void addRoom(char label) {
		roomList[nRooms++] = new Room(label);
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
		if(start + MAZE_SIZE == end || start - MAZE_SIZE == end)
			return true;
		return false;
	}
}
