package day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	private static int[][] grid;
	public static void main(String[] args) {
		Scanner scanner;
		try {
			//File file = new File("day05/test.txt");
			File file = new File("day05/input.txt");
			scanner = new Scanner(file);
		} catch(FileNotFoundException e) {
			System.out.println("unable to open file");
			return;
		}

		grid = new int[1000][1000];

		parse(scanner);
		solve();
	}

	private static void parse(Scanner scanner) {
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split("->");
			String[] point1 = line[0].split(",");
			String[] point2 = line[1].split(",");

			int x1 = Integer.parseInt(point1[0].trim());
			int y1 = Integer.parseInt(point1[1].trim());
			int x2 = Integer.parseInt(point2[0].trim());
			int y2 = Integer.parseInt(point2[1].trim());
			addPoints(x1, y1, x2, y2);
		}
	}

	private static void solve() {
		/*
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		*/
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] > 1)
					count++;
			}
		}
		System.out.println(count);
	}

	private static void addPoints(int x1, int y1, int x2, int y2) {
		if (x1 == x2) { // ver
			for (int yi = Math.min(y1, y2); yi <= Math.max(y1, y2); yi++) {
				grid[yi][x1]++;
			}
		} else if (y1 == y2) { // hor
			for (int xi = Math.min(x1, x2); xi <= Math.max(x1, x2); xi++) {
				grid[y1][xi]++;
			}
		} else if ((x1 < x2 && y1 < y2) || (x2 < x1 && y2 < y1)) { // pos
			for (int i = 0; Math.min(x1, x2) + i <= Math.max(x1, x2); i++) {
				grid[Math.min(y1, y2) + i][Math.min(x1, x2) + i]++;
			}
		} else { // neg
			for (int i = 0; Math.min(x1, x2) + i <= Math.max(x1, x2); i++) {
				grid[Math.max(y1, y2) - i][Math.min(x1, x2) + i]++;
			}
		}
	}
}