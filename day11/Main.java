package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			int[][] levels = parse();
			int flashed = 0;
			int step = 0;
			while (flashed != 100) {
				flashed = next(levels);
				step++;
			}
			System.out.println(step);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}	

	private static int[][] parse() throws FileNotFoundException {
		//File file = new File("day11/test.txt");
		File file = new File("day11/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			ArrayList<int[]> lines = new ArrayList<>();
			while (scanner.hasNextLine()) {
				lines.add(Arrays.stream(scanner.nextLine().split("|")).mapToInt(s -> Integer.parseInt(s)).toArray());
			}
			int[][] parsed = new int[lines.size()][lines.get(0).length];
			parsed = lines.toArray(parsed);
			return parsed;
		}
	}

	private static int next(int[][] levels) {
		for (int i = 0; i < levels.length; i++) {
			for (int j = 0; j < levels[i].length; j++) {
				levels[i][j]++;
				if (levels[i][j] == 10) {
					flash(levels, i, j);
				}
			}
		} 
		int flashed = 0;
		for (int i = 0; i < levels.length; i++) {
			for (int j = 0; j < levels[i].length; j++) {
				if (levels[i][j] > 9) {
					levels[i][j] = 0;
					flashed++;
				}
			}
		}
		return flashed;
	}

	private static void flash(int[][] levels, int i, int j) {
		if (i > 0) {
			if (j > 0) {
				levels[i - 1][j - 1]++;
				if (levels[i - 1][j - 1] == 10) flash(levels, i - 1, j - 1);
			}
			levels[i - 1][j]++;
			if (levels[i - 1][j] == 10) flash(levels, i - 1, j);
			if (j < levels[i].length - 1) {
				levels[i - 1][j + 1]++;
				if(levels[i - 1][j + 1] == 10) flash(levels, i - 1, j + 1);
			}
		}
		if (j > 0) {
			levels[i][j - 1]++;
			if (levels[i][j - 1] == 10) flash(levels, i, j - 1);
		}
		if (j < levels[i].length - 1) {
			levels[i][j + 1]++;
			if (levels[i][j + 1] == 10) flash(levels, i, j + 1);
		}
		if (i < levels.length - 1) {
			if (j > 0) {
				levels[i + 1][j - 1]++;
				if (levels[i + 1][j - 1] == 10) flash(levels, i + 1, j - 1);
			}
			levels[i + 1][j]++;
			if (levels[i + 1][j] == 10) flash(levels, i + 1, j);
			if (j < levels[i].length - 1) {
				levels[i + 1][j + 1]++;
				if (levels[i + 1][j + 1] == 10) flash(levels, i + 1, j + 1);
			}
		}
	}
}
