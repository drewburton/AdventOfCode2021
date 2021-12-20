package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	private static HashMap<String, String> mappings;
	public static void main(String[] args) {
		//File file = new File("day14/test.txt");
		File file = new File("day14/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			String template = scanner.nextLine();
			HashMap<String, Long> polymer = parseTemplate(template);
			scanner.nextLine();
			mappings = new HashMap<>();
			while (scanner.hasNextLine()) {
				String[] mapping = scanner.nextLine().replaceAll(" ", "").split("->");
				mappings.put(mapping[0], mapping[1]);
			}
			for (int i = 0; i < 40; i++) {
				polymer = step(polymer);
			}

			HashMap<Character, Long> counts = new HashMap<>();
			counts.put(template.charAt(template.length() - 1), 1l);
			for (var entry : polymer.entrySet()) {
				char key = entry.getKey().charAt(0);
				if (counts.containsKey(key)) {
					counts.put(key, counts.get(key) + entry.getValue());
				} else {
					counts.put(key, entry.getValue());
				}
			}
			long most = 0;
			long least = Long.MAX_VALUE;
			for (long value : counts.values()) {
				if (value > most) most = value;
				if (value < least) least = value;
			}
			System.out.println(most - least);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}

	private static HashMap<String, Long> parseTemplate(String template) {
		HashMap<String, Long> polymer = new HashMap<>();
		for(int i = 0; i < template.length() - 1; i++) {
			String newPolymer = template.substring(i, i + 2);
			if (polymer.containsKey(newPolymer)) {
				polymer.put(newPolymer, polymer.get(newPolymer) + 1);
			} else {
				polymer.put(newPolymer, 1l);
			}
		}
		return polymer;
	}

	private static HashMap<String, Long> step(HashMap<String, Long> polymer) {
		HashMap<String, Long> next = new HashMap<>();
		for (var entry : polymer.entrySet()) {
			String s = entry.getKey();
			char c = mappings.get(s).charAt(0);
			String s1 = s.substring(0, 1) + c;
			String s2 = c + s.substring(1, 2);
			if (next.containsKey(s1)) next.put(s1, next.get(s1) + entry.getValue());
			else next.put(s1, entry.getValue());
			if (next.containsKey(s2)) next.put(s2, next.get(s2) + entry.getValue());
			else next.put(s2, entry.getValue());
		}
		return next;
	}
}
