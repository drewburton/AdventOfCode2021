package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		//String fileText = "test";
		String fileText = "input";
		File file = new File("day15/" + fileText + ".txt");	
		try (Scanner scanner = new Scanner(file)) {
			int size = "test".equals(fileText) ? 10 : 100;
			int[][] grid = new int[size][size];
			int i = 0;
			while (scanner.hasNextLine()) {
				int[] row = Arrays.stream(scanner.nextLine().split("|")).mapToInt(s -> Integer.parseInt(s)).toArray();
				grid[i] = row;
				i++;
			}

			int[][] massiveGrid = new int[grid.length * 5][grid.length * 5];
			for (i = 0; i < massiveGrid.length; i++) {
				for (int j = 0; j < massiveGrid.length; j++) {
					massiveGrid[i][j] = grid[i%grid.length][j%grid.length] + (i / grid.length) + (j / grid.length);
					if (massiveGrid[i][j] > 9) massiveGrid[i][j] = (massiveGrid[i][j] % 10) + 1;
				}
			}

			System.out.println(evaluateRisk(massiveGrid));
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}

	private static int evaluateRisk(int[][] risk) {
		int[][] graph = new int[risk.length][risk.length];
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				graph[i][j] = 1_000_000;
			}
		}
		graph[graph.length - 1][graph.length - 1] = 0;

		boolean changesMade = true;
		while(changesMade) {
			changesMade = false;
			int i = 0, j = 0, min = 0;
			for (i = graph.length - 1; i >= 0; i--) {
				for (j = graph.length - 1; j >= 0; j--) {
					min = Integer.MAX_VALUE;
					if (i > 0) {
						min = Integer.min(min, graph[i - 1][j] + risk[i - 1][j]);
					}
					if (i < graph.length - 1) {
						min = Integer.min(min, graph[i + 1][j] + risk[i + 1][j]);
					}
					if (j > 0) {
						min = Integer.min(min, graph[i][j - 1] + risk[i][j - 1]);
					}
					if (j < graph.length - 1) {
						min = Integer.min(min, graph[i][j + 1] + risk[i][j + 1]);
					}
					int previousRisk = graph[i][j];
					graph[i][j] = Integer.min(min, graph[i][j]);
					if (graph[i][j] != previousRisk) {
						changesMade = true;
					}
				}
			}
			
		}
		/*
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.println();
		}
		*/
		return graph[0][0];
	}
}
