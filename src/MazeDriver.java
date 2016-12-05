public class MazeDriver {

    public static void main(String[] args){
        int path = 1;
        int dfs_path = 2;
        int bfs_path = 3;
        int size = 5;

        Maze maze1 = new Maze(size);
        Maze maze2 = new Maze(size);
        
        maze1.fill();
        System.out.println("Maze 1 for DFS");
        maze1.showMaze(path);
        maze1.dfs();
        maze1.printPath(maze1.graph[0][0], maze1.graph[size-1][size-1]);
        System.out.println("Direct Path :");
        maze1.showMaze(path);
        System.out.println("\nDFS: ");
        maze1.showMaze(dfs_path);

        maze2.fill();
        System.out.println("\nMaze 2 for BFS");
        maze2.showMaze(path);
        maze2.bfs();
        maze2.printPath(maze2.graph[0][0], maze2.graph[size-1][size-1]);
        System.out.println("\nDirect Path: ");
        maze2.showMaze(path);
        System.out.println("\nBFS: ");
        maze2.showMaze(bfs_path);
    }
}