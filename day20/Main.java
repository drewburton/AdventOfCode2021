package day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static Character[] algorithm;
	public static void main(String[] args) {
		String fileText = "input";
		File file = new File("day20/" + fileText + ".txt");	
		try (Scanner scanner = new Scanner(file)) {
			String[] line = scanner.nextLine().split("|");
			algorithm = new Character[line.length];
			for (int i = 0; i < line.length; i++) {
				algorithm[i] = line[i].charAt(0);
			}
			scanner.nextLine();
			ArrayList<String[]> input = new ArrayList<>();
			while (scanner.hasNextLine()) {
				input.add(scanner.nextLine().split("|"));
			}
			Character[][] image = new Character[300][300];
			for (int i = 0; i < image.length; i++) {
				for (int j = 0; j < image[i].length; j++) {
					image[i][j] = '.';
				}
			}
			for (int i = 0; i < input.size(); i++) {
				for (int j = 0; j < input.get(i).length; j++) {
					image[i + 100][j + 100] = input.get(i)[j].charAt(0);
				}
			}
			for (int i = 0; i < 50; i++) {
				image = applyAlgorithm(image);
				image = flashBorder(image);
			}
			int count = 0;
			for (int i = 0; i < image.length; i++) {
				for (int j = 0; j < image.length; j++) {
					if (image[i][j] == '#') count++;
				}
			}
			System.out.println(count);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");	
		}
	}

	public static Character[][] applyAlgorithm(Character[][] image) {
		Character[][] newImage = new Character[image.length][image.length];
		for (int i = 0; i < newImage.length; i++) {
			for (int j = 0; j < newImage.length; j++) {
				newImage[i][j] = '.';
			}
		}
		for (int topIndex = 0; topIndex < image.length - 2; topIndex++) {
			for (int leftIndex = 0; leftIndex < image.length - 2; leftIndex++) {
				StringBuilder builder = new StringBuilder();
				for (int i = topIndex; i < topIndex + 3; i++) {
					for (int j = leftIndex; j < leftIndex + 3; j++) {
						builder.append(image[i][j]);
					}
				}
				int index = Integer.parseInt(builder.toString().replaceAll("\\.", "0").replaceAll("#", "1"), 2);	
				newImage[topIndex + 1][leftIndex + 1] = algorithm[index];
			}
		}
		return newImage;
	}

	public static Character[][] flashBorder(Character[][] array) {
		if (array[1][1] == '#') {
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array.length; j++) {
					if (i == 0 || i == array.length - 1) {
						array[i][j] = '#';
						continue;
					}
					if (j == 0 || j == array.length -1) {
						array[i][j] = '#';
						continue;
					}
				}
			}
		}
		return array;
	}

	public static void print(Character[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}
}
