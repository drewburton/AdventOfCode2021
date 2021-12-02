package day01;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner;
		try {
			//File file = new File("day01/test.txt");
			File file = new File("day01/input.txt");
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return;
		}

		ArrayList<Integer> input = new ArrayList<>();
		while (scanner.hasNextInt()) {
			input.add(scanner.nextInt());
		}	
		ArrayList<Integer> triSum = part2(input);
		part1(triSum);
		scanner.close();
	
	}

	private static void part1(ArrayList<Integer> input) {
		int count = 0;
		for (int i = 1; i < input.size(); i++) {
			if (input.get(i) > input.get(i - 1))
				count++;
		}
		System.out.println(count);
	}

	private static ArrayList<Integer> part2(ArrayList<Integer> input) {
		ArrayList<Integer> triSum = new ArrayList<>();
		for (int i = 0; i < input.size() - 2; i++) {
			int sum = 0;
			for (int j = i; j < i + 3; j++) {
				sum += input.get(j);
			}
			triSum.add(sum);
		}
		return triSum;
	}
}