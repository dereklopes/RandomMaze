import java.util.Random;
import java.util.Stack;

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
	private Random rand; // random number generator for randomizing maze
	
	/**
	 * Creates a maze with full walls (no doors).
	 * @param size the size of the maze (size x size).
	 */
	public Maze(int size) {
		mazeSize = size;
		maxRooms = mazeSize * mazeSize;
		roomList = new Room[maxRooms];
		for(int i = 0; i < roomList.length; i++) {
			roomList[i] = new Room((char) i);
		}
		adjMat = new int[maxRooms][maxRooms];
		for(int i = 0; i < mazeSize; i++) {
			for(int j = 0; j < mazeSize; j++) {
				adjMat[i][j] = 0;
			}
		}
		rand = new Random();
	}
	
	/**
	 * If the rooms are not adjacent, no door is added.
	 * Otherwise, adds a connecting door between two rooms.
	 * @param start start room
	 * @param end end room
	 * @return if the door was added or not
	 */
	public boolean addDoor(int start, int end) {
		if(start >= 0 && start < maxRooms && end >= 0 && end < maxRooms) {
			if(checkAdjacent(start, end)) {
				adjMat[start][end] = 1;
				adjMat[end][start] = 1;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
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
	public boolean checkAdjacent(int start, int end) {
		// + or - 1
		if((start + 1 == end) && (((start + 1) % mazeSize) != 0))
			return true;
		if((start - 1 == end) && ((start % mazeSize) != 0))
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
		maze += "+  ";
		for(int i = 1; i < mazeSize; i++) {
			maze += "+--";
		}
		maze += "+\n";
		
		// insides
		for(int i = 0; i < mazeSize; i++) {
			// test horizontal connections
			maze += "|  ";
			int start = i * mazeSize;
			int finish = start + mazeSize;
			for(int j = start; j < finish - 1; j++) {
				if(adjMat[j][j+1] == 1)
					maze += "   ";
				else
					maze += "|  ";
			}
			maze += "|\n";
			// test vertical connections
			if(i != mazeSize - 1) {
				maze += "+";
				for(int j = start; j < finish; j++) {
					if(adjMat[j][j + mazeSize] == 1)
						maze += "  +";
					else
						maze += "--+";
				}
				maze += "\n";
			}
		}
		
		// bottom border
		for(int i = 0; i < mazeSize - 1; i++) {
			maze += "+--";
		}
		maze += "+  +";
		
		return maze;
	}
	
	/**
	 * @return random double from 0 to 1 inclusive
	 */
	private double getRandomNum() {
		return rand.nextDouble();
	}
	
	/**
	 * Randomly generate the maze.
	 */
	public void randomize() {
		Stack<Integer> stack = new Stack<Integer>();
		int currentCell, visitedCells;
		currentCell = visitedCells = 0;
		while(visitedCells < maxRooms - 1) {
			roomList[currentCell].visit();
			//find all neighbors that haven't been visited
			boolean top, bottom, left, right;
			top = bottom = left = right = false;
			// check bottom neighbor
			if(currentCell + mazeSize < maxRooms && !roomList[currentCell + mazeSize].wasVisited) {
				bottom = true;
			}
			// check top neighbor
			if(currentCell - mazeSize >= 0 && !roomList[currentCell - mazeSize].wasVisited) {
				top = true;
			}
			// check right neighbor
			if(currentCell + 1 < maxRooms && ((currentCell + 1) % mazeSize) != 0
					&& !roomList[currentCell + 1].wasVisited) {
				right = true;
			}
			// check left neighbor
			if(currentCell - 1 >= 0 && currentCell % mazeSize != 0 
					&& !roomList[currentCell - 1].wasVisited) {
				left = true;
			}
			// if an unvisited neighbor exists, randomly pick one
			if(top || bottom || left || right) {
				boolean notAdded = true;
				while(notAdded) {
					int rand = (int) (getRandomNum() * 4);
					switch(rand) {
					case 0:
						if(top) {
							if(!(notAdded = !addDoor(currentCell, currentCell - mazeSize))) {
								stack.push(currentCell);
								currentCell = currentCell - mazeSize;
								visitedCells++;
							}
						}
						break;
					case 1:
						if(bottom) {
							if(!(notAdded = !addDoor(currentCell, currentCell + mazeSize))) {
								stack.push(currentCell);
								currentCell = currentCell + mazeSize;
								visitedCells++;
							}
						}
						break;
					case 2:
						if(left) {
							if(!(notAdded = !addDoor(currentCell, currentCell - 1))) {
								stack.push(currentCell);
								currentCell = currentCell - 1;
								visitedCells++;
							}
						}
						break;
					case 3:
						if(right) {
							if(!(notAdded = !addDoor(currentCell, currentCell + 1))) {
								stack.push(currentCell);
								currentCell = currentCell + 1;
								visitedCells++;
							}
						}
						break;
					}
				}
			} else {
				currentCell = stack.pop();
			}
		}
	}
	
}
