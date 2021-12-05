package day02;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner;
		try {
			//File file = new File("day02/test.txt");
			File file = new File("day02/input.txt");
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
			return;
		}

		ArrayList<String> instructions = new ArrayList<>();
		while (scanner.hasNextLine()) {
			instructions.add(scanner.nextLine());
		}

		part1(instructions);

		scanner.close();
	}
	
	private static void part1(ArrayList<String> instructions) {
		int horizontal = 0, depth = 0, aim = 0;
		for (String instruction : instructions) {
			String[] split = instruction.split(" ");
			String direction = split[0];
			int amount = Integer.parseInt(split[1]);
			switch(direction) {
				case "forward":
					horizontal += amount;
					depth += aim * amount;
					break;
				case "down":
					aim += amount;
					break;
				case "up":
					aim -= amount;
					break;
			}
		}
		System.out.println(horizontal * depth);
	}
}
