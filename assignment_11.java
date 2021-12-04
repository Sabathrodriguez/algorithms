package com.globalsoftwaresupport;
import java.util.*;

public class assignment_11 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int V = Integer.parseInt(scanner.next().trim());
		int[][] graph = new int[V][V];

		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				graph[i][j] = Integer.parseInt(scanner.next().trim());
			}
		}

			TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(graph, 0);
			tsp.solve(0, 1, 0);

		System.out.print(tsp.getDistance());

	}
}
class TravellingSalesmanProblem {

	private int[][] graph;
	private boolean[] visited;
	private List<Integer> path;
	private int min;

	private Map<String, String> paths_taken = new TreeMap<>();

	public TravellingSalesmanProblem(int[][] graph, int start) {
		this.graph = graph;
		this.visited = new boolean[graph.length];
		this.path = new ArrayList<>();

		initilaize(start);
	}

    /*
     @args:
        start: starting vertex
     */
	private void initilaize(int start) {
		// start with vertex index 0
		visited[start] = true;
		path.add(start);
		min = Integer.MAX_VALUE;
	}

    /*
     @args:
        vertex: is "vertex" a valid move:
                * has it been visited?
                * does it have an edge to actualPosition vertex?
        actualPosition: current vertex in G
     @returns: if vertex is a valid next vertex, then return true, if not then return false
     */
	private boolean isValid(int vertex, int actualPosition) {

		// has the vertex already been visited?
		if(visited[vertex])
			return false;

		// is there a connection between the 2 vertices?
		if(graph[actualPosition][vertex] == 0)
			return false;

		return true;
	}
    
    /*
     @args:
        actualPosition: current vertex in G
        counter: number of vertices traversed in G
        cost: total "cost" of trip from beginning to actualPosition
     */
	public void solve(int actualPosition, int counter, int cost) {

        //if we've traversed the entire graph OR
		if (counter == graph.length && graph[actualPosition][0] != 0) {
			path.add(0);

			if (cost + graph[actualPosition][0] < min)
				min = cost + graph[actualPosition][0];

			path.remove(path.size() - 1);
			return;
		}

        //check every row in current column i.e. actualPosition
		for (int i = 0; i < graph.length; ++i) {
            //if we can traverse from actualPosition vertex to i'th vertex then continue
			if (isValid(i, actualPosition) ) {
                //mark visited array at i'th index (vertex) as visited
				visited[i] = true;
                //add vertex i to verteces visited
				path.add(i);
                //current cost (to get to current vertex) + cost of new vertex i
				int temp_cost = cost + graph[actualPosition][i];
                //memoization
				paths_taken.put(actualPosition + "," + i, temp_cost + "");
				//recur and check next vertex:
                    //actualVertex = i
                    //counter += 1
                    //cost = current cost (to get to current vertex) + cost of new vertex i
                solve(i, counter + 1, cost + graph[actualPosition][i]);

				//backgtracking
				visited[i] = false;
				path.remove(path.size() - 1);
			}
		}
	}
    /*
     @returns: min distance hamoltonian cycle
     */
	public int getDistance() {
		return min;
	}
}
