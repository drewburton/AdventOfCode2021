package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static HashMap<String, List<String>> nodes;
	public static void main(String[] args) {
		//File file = new File("day12/test.txt");
		File file = new File("day12/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			nodes = new HashMap<>();
			while (scanner.hasNextLine()) {
				String[] split = scanner.nextLine().split("-");
				if (nodes.containsKey(split[0])) {
					List<String> children = new ArrayList<>(nodes.get(split[0]));
					children.add(split[1]);
					nodes.put(split[0], children);
				} else {
					nodes.put(split[0], List.of(split[1]));
				}
				if (nodes.containsKey(split[1])) {
					List<String> children = new ArrayList<>(nodes.get(split[1]));
					children.add(split[0]);
					nodes.put(split[1], children);

				} else {
					nodes.put(split[1], List.of(split[0]));
				}
			}

			System.out.println(numPaths("start", "", "start"));
		} catch(FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}	

	private static int numPaths(String node, String smallCaves, String path) {
		if ("end".equals(node)) {
			//System.out.println(path);
			return 1;
		}
		if (!"start".equals(node) && Character.isLowerCase(node.charAt(0))) smallCaves += "->" + node;
		int paths = 0;
		for (String child : nodes.get(node)) {
			if ("start".equals(child)) continue;
			if (!"end".equals(child) && containsTwice(smallCaves, child)) continue;
			paths += numPaths(child, smallCaves, path + "->" + child);
		}
		return paths;
	}

	private static boolean containsTwice(String smallCaves, String node) {
		ArrayList<String> caves = new ArrayList<>(List.of(smallCaves.split("->")));
		while (caves.size() > 0) {
			String cave = caves.remove(0);
			if (caves.contains(cave) && smallCaves.contains(node)) return true;
		}
		return false;
	}
}