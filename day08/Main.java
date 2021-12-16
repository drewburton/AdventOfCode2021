package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		try {
			ArrayList<String> list = parse();
			int count = 0;
			for (String s : list) {
				int num = determineNum(s);
				if (num != -1) count++;
			}
			System.out.println(count);
		} catch (FileNotFoundException e) { System.out.println("unable to open file"); }
	}

	private static ArrayList<String> parse() throws FileNotFoundException {
		//File file = new File("day08/test.txt");
		File file = new File("day08/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			ArrayList<String> list = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String[] split = scanner.nextLine().split("\\|");
				String[] signal = split[0].trim().split(" ");
				String[] output = split[1].trim().split(" ");
				//for (String s : signal) list.add(s);
				for (String s : output) list.add(s);
			}
			return list;
		}
	}

	private static int determineNum(String s) {
		switch(s.length()) {
			case 2: return 1;
			case 3: return 7;
			case 4: return 4;
			case 7: return 8;
			default: return -1;
		}
	}
}
