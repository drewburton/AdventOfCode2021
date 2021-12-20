package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Main {
	private static int[][] grid;
	public static void main(String[] args) throws Exception {
		//File file = new File("day15/test.txt");
		File file = new File("day15/input.txt");	
		try (Scanner scanner = new Scanner(file)) {
			grid = new int[100][100];
			int i = 0;
			while (scanner.hasNextLine()) {
				int[] row = Arrays.stream(scanner.nextLine().split("|")).mapToInt(s -> Integer.parseInt(s)).toArray();
				grid[i] = row;
				i++;
			}
			Node start = new Node(0, 0);
			start.setDistance(0);
			System.out.println(calculateShortestPath(start));
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}

	private static int calculateShortestPath(Node start) throws Exception {
		Set<Node> settledNodes = new HashSet<>();
		PriorityQueue<Node> unsettledNodes = new PriorityQueue<>(11, (o1, o2) -> {
			return o1.getDistance() - o2.getDistance();
		});
		Set<String> settledString = new HashSet<>();
		unsettledNodes.add(start);

		while(unsettledNodes.size() != 0) {
			Node current = unsettledNodes.remove();
			unsettledNodes.remove(current);
			for (var adjacentEntry : current.getAdjacent(grid).entrySet()) {
				Node adjacent = adjacentEntry.getKey();
				int weight = adjacentEntry.getValue();
				if (!settledString.contains(adjacent.getRow() + " " + adjacent.getColumn())) {
					setMinimumDistance(adjacent, weight, current);
					unsettledNodes.add(adjacent);
				}
			}
			settledNodes.add(current);
			settledString.add(current.getRow() + " " + current.getColumn());
		}
		for (Node n : settledNodes) {
			if (n.getAdjacent(grid).size() == 0) {
				return printPath(n.getShortestPath());
			}
		}
		return 0;
	}

	private static int printPath(List<Node> path) {
		int risk = 0;
		for (Node n : path) {
			//System.out.println("(" + n.getRow() + "," + n.getColumn() + ")"); 
			risk = n.getDistance();
		}
		return risk;
	}

	private static Node lowestDistanceUnsettled(Set<Node> unsettledNodes) {
		Node lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (Node n : unsettledNodes) {
			if (n.getDistance() < lowestDistance) {
				lowestDistance = n.getDistance();
				lowestDistanceNode = n;
			}
		}
		return lowestDistanceNode;
	}

	private static void setMinimumDistance(Node n, int weight, Node source) {
		int sourceDist = source.getDistance();
		if (sourceDist + weight < n.getDistance()) {
			n.setDistance(sourceDist + weight);
			LinkedList<Node> shortestPath = new LinkedList<>(source.getShortestPath());
			shortestPath.add(n);
			n.setShortestPath(shortestPath);
		}
	}
}

class Node {
	private int row, column;
	private List<Node> shortestPath = new LinkedList<>();
	private int distance = Integer.MAX_VALUE;
	private Map<Node, Integer> adjacentNodes;

	public void addAdjacent(Node n, int dist) {
		adjacentNodes.put(n, dist);
	}

	public Node(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() { return row; }
	public int getColumn() { return column; }

	public int getDistance() { return distance; }
	public void setDistance(int dist) { distance = dist; }

	public List<Node> getShortestPath() { return shortestPath; }
	public void setShortestPath(List<Node> shortestPath) { this.shortestPath = shortestPath; }

	public Map<Node, Integer> getAdjacent(int[][] grid) {
		if (adjacentNodes == null) {
			adjacentNodes = new HashMap<>();
			/*
			if (row > 0) {
				Node n = new Node(row - 1, column);
				adjacentNodes.put(n, grid[row - 1][column]);	
			}
			*/
			if (row < grid.length - 1) {
				Node n = new Node(row + 1, column);
				adjacentNodes.put(n, grid[row + 1][column]);
			}
			/*
			if (column > 0) {
				Node n = new Node(row, column - 1);
				adjacentNodes.put(n, grid[row][column - 1]);
			}
			*/
			if (column < grid[row].length - 1) {
				Node n = new Node(row, column + 1);
				adjacentNodes.put(n, grid[row][column + 1]);
			}
		}
		return adjacentNodes;
	}
}
