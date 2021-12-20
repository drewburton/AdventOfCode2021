package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		//String fileText = "test";
		String fileText = "input";
		File file = new File("day13/" + fileText + ".txt");
		try (Scanner scanner = new Scanner(file)) {
			int size = "test".equals(fileText) ? 15 : 1500;
			Character[][] points = new Character[size][size];
			for (int i = 0; i < points.length; i++) {
				for (int j = 0; j < points[i].length; j++) {
					points[i][j] = ' ';
				}
			}
			String line = scanner.nextLine();
			while (!"".equals(line)) {
				String[] split = line.split(",");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				points[y][x] = '#';
				line = scanner.nextLine();
			}
			ArrayList<String> folds = new ArrayList<>();
			while (scanner.hasNextLine()) {
				folds.add(scanner.nextLine().split(" ")[2]);
			}
			for (String fold : folds) {
				fold(points, fold);
			}
			print(points);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}	

	private static void fold(Character[][] points, String fold) {
		String[] split = fold.split("=");
		int line = Integer.parseInt(split[1]);
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				if ("x".equals(split[0]) && j > line && points[i][j] == '#') {
					points[i][2 * line - j] = '#';
					points[i][j] = ' ';
				} else if ("y".equals(split[0]) && i > line && points[i][j] == '#') {
					points[2 * line - i][j] = '#';
					points[i][j] = ' ';
				}
			}
		}
	}

	private static void print(Character[][] points) {
		int maxX = 0;
		int maxY = 0;
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				if (points[i][j] == '#' && i > maxY) maxY = i;
				if (points[i][j] == '#' && j > maxX) maxX = j;
			}
		}

		for (int i = 0; i <= maxY; i++) {
			for (int j = 0; j <= maxX; j++) {
				System.out.print(points[i][j]);
			}
			System.out.println();
		}
	}
}
