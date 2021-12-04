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

	// adjacency matrix representation of the G(V,E)
	private int[][] graph;
	// we have to track whether we have visited the given nodes
	private boolean[] visited;
	// we have to track the path (that may form a cycle)
	private List<Integer> path;
	// because we are after the MINIMUM hamiltonian cycle
	private int min;

	private Map<String, String> paths_taken = new TreeMap<>();

	public TravellingSalesmanProblem(int[][] graph, int start) {
		this.graph = graph;
		this.visited = new boolean[graph.length];
		this.path = new ArrayList<>();

		initilaize(start);
	}

	private void initilaize(int start) {
		// start with vertex index 0
		visited[start] = true;
		path.add(start);
		min = Integer.MAX_VALUE;
	}

	private boolean isValid(int vertex, int actualPosition) {

		// if the vertex is already been visited then it is not good
		if(visited[vertex])
			return false;

		// is there a connection between the 2 vertices?
		if(graph[actualPosition][vertex] == 0)
			return false;

		return true;
	}

	public void solve(int actualPosition, int counter, int cost) {

		if (counter == graph.length && graph[actualPosition][0] != 0) {
			path.add(0);

			if (cost + graph[actualPosition][0] < min)
				min = cost + graph[actualPosition][0];

			path.remove(path.size() - 1);
			return;
		}

		for (int i = 0; i < graph.length; ++i) {
			if (isValid(i, actualPosition) ) {
				visited[i] = true;
				path.add(i);
				int temp_cost = cost + graph[actualPosition][i];
				paths_taken.put(actualPosition + "," + i, temp_cost + "");
				solve(i, counter + 1, cost + graph[actualPosition][i]);

				//backgtracking
				visited[i] = false;
				path.remove(path.size() - 1);
			}
		}
	}

	public int getDistance() {
		return min;
	}
}
