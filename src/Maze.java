import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 * @author Derek Lopes & Hedi Moalla Source credit to "Data Structures and
 *         Algorithms in Java" by Robert Lafore 2 Ed. Chapter 13
 *
 */
public class Maze {
	private final int mazeSize;
	private final int maxRooms;
	static final int UNVISITED = 1;
	static final int VISITED = 2;
	static final int EXPLORED = 3;

	static final int PATH = 1;
	static final int DFS_PATH = 2;
	static final int BFS_PATH = 3;

	int[][] adjMat;
	Node[] adjList;

	Stack<Vertex> cellStack;
	Vertex[][] graph;
	Vertex currentCell;
	
	int visitedCells;

	public Maze(int size) {
		this.mazeSize = size;
		maxRooms = mazeSize * mazeSize;
		adjMat = new int[mazeSize][mazeSize];
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				adjMat[i][j] = 0;
			}
		} // fill adjMat with 0s
		adjList = new Node[maxRooms];
		for (int i = 0; i < maxRooms; i++) {
			adjList[i] = null;
		}
		cellStack = new Stack<Vertex>();
		graph = new Vertex[mazeSize][mazeSize];
	}

	/**
	 * @param path
	 */
	public void showMaze(int path) {
		System.out.println("");
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				if (graph[j][i].hasUpWall) {
					if (graph[j][i] == graph[0][0])
						System.out.print("+   ");
					else
						System.out.print("+---");
				} else {
					System.out.print("+   ");
				}
			}
			System.out.println("+");
			for (int j = 0; j < mazeSize; j++) {
				// show path with #s
				if (path == PATH) {
					if (graph[j][i].hasLeftWall) {
						if (graph[j][i].isOnPath)
							System.out.print("| " + "#" + " ");
						else
							System.out.print("|   ");
					} else {
						if (graph[j][i].isOnPath)
							System.out.print("  " + "#" + " ");
						else
							System.out.print("    ");
					}
				}

				else if (path == BFS_PATH) {
					if (graph[j][i].hasLeftWall) {
						if (graph[j][i].visitBFS <= graph[mazeSize - 1][mazeSize - 1].visitBFS) {
							System.out.print("| " + String.format("%2s", graph[j][i].visitBFS));
						} else
							System.out.print("|   ");
					} else {
						if (graph[j][i].visitBFS <= graph[mazeSize - 1][mazeSize - 1].visitBFS) {
							System.out.print("  " + String.format("%2s", graph[j][i].visitBFS));
						} else
							System.out.print("    ");
					}
				} else {
					if (graph[j][i].hasLeftWall) {
						if (graph[j][i].dtime <= graph[mazeSize - 1][mazeSize - 1].dtime) {
							System.out.print("| " + String.format("%2s", graph[j][i].visitedOrder));
						} else
							System.out.print("|   ");
					} else {
						if (graph[j][i].dtime <= graph[mazeSize - 1][mazeSize - 1].dtime) {
							System.out.print("  " + String.format("%2s", graph[j][i].visitedOrder));
						} else
							System.out.print("    ");
					}
				}

			}
			System.out.println("|");
		}
		for (int j = 0; j < mazeSize; j++) {
			if (j == mazeSize - 1)
				System.out.print("+   ");
			else
				System.out.print("+---");

		}
		System.out.println("+");

	}

	/**
	 * 
	 */
	public void mazeGenerator() {
		currentCell = graph[0][0];
		currentCell.isVisited = true;
		visitedCells = 1;

		while (visitedCells < maxRooms) {
			if (validNeighbors(currentCell) != 0) {
				Vertex vtx = null;

				Random generator = new Random();
				int random = generator.nextInt(validNeighbors(currentCell));
				vtx = currentCell.neighbors.get(random);

				removeWall(currentCell, vtx);

				Node newNode = new Node(vtx);
				Node currentNode = adjList[currentCell.label - 1];
				if (currentNode == null) {
					currentNode = newNode;
					adjList[currentCell.label - 1] = currentNode;
				} else {
					while (currentNode.next != null) {
						currentNode = currentNode.next;
					}
					currentNode.next = newNode;
				}

				Node newNode2 = new Node(currentCell);
				currentNode = adjList[vtx.label - 1];
				if (currentNode == null) {
					currentNode = newNode2;
					adjList[vtx.label - 1] = newNode2;
				} else {
					while (currentNode.next != null) {
						currentNode = currentNode.next;
					}
					currentNode.next = newNode2;
				}
				cellStack.push(currentCell);
				currentCell = vtx;
				currentCell.isVisited = true;
				visitedCells++;
			} else {
				if (!cellStack.isEmpty()) {

					Vertex v = cellStack.pop();
					currentCell = v;
				}
			}

		}
	}

	int visitBfsOrd;

	/**
	 * 
	 */
	public void bfs() {
		Vertex vrtx = this.graph[0][0];
		vrtx.status = VISITED;
		vrtx.distance = 0;
		vrtx.parent = null;

		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(vrtx);

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			Node v = adjList[u.label - 1];
			while (v != null) {
				if (v.v.status == UNVISITED) {
					v.v.status = VISITED;
					visitBfsOrd = visitBfsOrd + 1;
					v.v.visitBFS = visitBfsOrd;
					v.v.distance = u.distance + 1;
					v.v.parent = u;
					queue.add(v.v);
							}
				v = v.next;
			}
		}
	}

	int time;
	int visitOrd;

	/**
	 * 
	 */
	public void dfs() {
		time = 0;
		Vertex currentNode;
		System.out.println("");
		for (int i = 0; i < maxRooms; i++) {

			currentNode = graph[i / mazeSize][i % mazeSize];
			if (currentNode.status == UNVISITED)
				dfsVisit(currentNode);
		}
		System.out.println("");
	}

	/**
	 * @param u
	 */
	public void dfsVisit(Vertex u) {
		time++;
		u.visitedOrder = visitOrd;
		visitOrd = visitOrd + 1;
		u.dtime = time;
		u.status = VISITED;
		Node n = adjList[u.label - 1];
		while (n != null) {
			if (n.v.status == UNVISITED) {
				n.v.parent = u;
				dfsVisit(n.v);
			}
			n = n.next;
		}
		u.status = EXPLORED;
		time++;
		u.ftime = time;
	}

	/**
	 * @param startVertex
	 * @param endVertex
	 */
	public void printPath(Vertex startVertex, Vertex endVertex) {

		if (startVertex.label == endVertex.label)
			;
		else if (endVertex.parent == null)
			System.out.print("No paths exist");
		else
			printPath(startVertex, endVertex.parent);
		endVertex.isOnPath = true;

	}

	/**
	 * @param graph
	 */
	public void printAdjList(Node[] graph) {
		Node currentNode = null;
		for (int i = 0; i < maxRooms; i++) {
			System.out.print("\nRow : [" + (i + 1) + "]");
			currentNode = graph[i];
			while (currentNode != null) {
				System.out.print("->" + currentNode.v.label);
				currentNode = currentNode.next;
			}
		}
	}

	/**
	 * @param v
	 * @return
	 */
	public int validNeighbors(Vertex v) {
		Iterator<Vertex> it = v.neighbors.iterator();
		while (it.hasNext()) {
			Vertex vertex = it.next();
			if (vertex.isVisited) {
				it.remove();
			}
		}
		int neighbors = 0;
		
		for (int x = 0; x < v.neighbors.size(); x++) {
			if (!v.neighbors.get(x).isVisited) {
				neighbors++;
			}
		}
		return neighbors;
	}

	/**
	 * This method removes the walls between two given cells.
	 * 
	 * @param current
	 *            vertex
	 * @param next
	 *            vertex
	 */

	public void removeWall(Vertex current, Vertex next) {

		if (current.label + mazeSize == next.label) {
			
			current.hasDownWall = false;
			next.hasUpWall = false;
		} else if (current.label + 1 == next.label) {
			
			current.hasRightWall = false;
			next.hasLeftWall = false;
		} else if (current.label - 1 == next.label) {
			
			current.hasLeftWall = false;
			next.hasRightWall = false;
		} else if (current.label - mazeSize == next.label) {
			
			current.hasUpWall = false;
			next.hasDownWall = false;
		}

		current.neighbors.remove(next);
		next.neighbors.remove(current);
	}

	/**
	 * assign attributes to vertices
	 */
	public void fill() {
		int vertexNumber = 1;

		
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				Vertex v = new Vertex(j, i);
				graph[j][i] = v;
			}
		}

		
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				graph[j][i].label = vertexNumber;
				graph[j][i].parent = null;
				vertexNumber++;
			}
		}

		
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				assignNeighbors(graph[j][i]);
			}
		}
		mazeGenerator();
	}

	/**
	 * This method assigns neighbors to a specified cell. Uses simple
	 * constraints such as determining the (x,y) coordinates of the
	 * aforementioned cell
	 * 
	 * @param currentCell
	 */
	public void assignNeighbors(Vertex v) {
		
		if (v.y != 0) {
			v.neighbors.add(graph[v.x][v.y - 1]);
		}

		
		if (v.y != (mazeSize - 1)) {
			v.neighbors.add(graph[v.x][v.y + 1]);
		}

		
		if (v.x != 0) {
			v.neighbors.add(graph[v.x - 1][v.y]);
		}

		
		if (v.x != mazeSize - 1) {
			v.neighbors.add(graph[v.x + 1][v.y]);
		}
	}

	class Node {
		Node next = null;
		Vertex v;

		public Node() {
		}

		public Node(Vertex x) {
			v = x;
		}
	}

	class Vertex {
		int visitBFS;
		int distance;
		int label;
		int x;
		int y;
		int visitNum = 0;
		int status = UNVISITED;

		boolean isVisited = false;
		boolean isOnPath = false;

		int dtime;
		int ftime;
		Vertex parent;

		int visitedOrder = 0;

		boolean hasUpWall = true;
		boolean hasDownWall = true;
		boolean hasRightWall = true;
		boolean hasLeftWall = true;
		boolean hasAllWalls = true;

		ArrayList<Vertex> neighbors = new ArrayList<Vertex>();

		public Vertex(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}