
public class MazeDriver {

	public static void main(String[] args) {
		Maze maze = new Maze(4);
		maze.randomize();
		System.out.println(maze.toString());
	}

}
