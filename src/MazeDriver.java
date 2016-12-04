
public class MazeDriver {

	public static void main(String[] args) {
		Maze maze = new Maze(4);
		maze.addDoor(0, 1);
		maze.addDoor(1, 2);
		maze.addDoor(2, 3);
		maze.addDoor(3, 7);
		maze.addDoor(7, 11);
		maze.addDoor(11, 15);
		System.out.println(maze.toString());
	}

}
