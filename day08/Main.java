package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		try {
			ArrayList<String> segs = parse();
			int sum = 0;
			for (String s : segs) {
				HashMap<Integer, Set<Character>> signals = determineSignal(s);
				sum += determineOutput(s, signals);
			}
			System.out.println(sum);
		} catch (FileNotFoundException e) { System.out.println("unable to open file"); }
	}

	private static ArrayList<String> parse() throws FileNotFoundException {
		//File file = new File("day08/test.txt");
		File file = new File("day08/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			ArrayList<String> segs = new ArrayList<>();
			while (scanner.hasNextLine()) {
				segs.add(scanner.nextLine());
			}
			return segs;
		}
	}

	private static HashMap<Integer, Set<Character>> determineSignal(String seg) {
		String[] split = seg.split("\\|");
		String[] signal = split[0].trim().split(" ");

		HashMap<Integer, Set<Character>> map = new HashMap<>();

		// uniques
		map.put(1, set(getSegmentsOfLength(signal, 2).get(0)));
		map.put(4, set(getSegmentsOfLength(signal, 4).get(0)));
		map.put(7, set(getSegmentsOfLength(signal, 3).get(0)));	

		List<String> fives = getSegmentsOfLength(signal, 5);
		for (String lenFive : fives) {
			Set<Character> set1 = set(lenFive);
			Set<Character> set2 = set(lenFive);
			set1.retainAll(map.get(1));
			set2.retainAll(map.get(4));

			if (set1.size() == 2) {
				map.put(3, set(lenFive));
			} else if (set2.size() == 3) {
				map.put(5, set(lenFive));
			} else {
				map.put(2, set(lenFive));
			}
		}

		List<String> sixes = getSegmentsOfLength(signal, 6);
		for (String lenSix : sixes) {
			Set<Character> set1 = set(lenSix);
			Set<Character> set2 = set(lenSix);
			set1.retainAll(map.get(4));
			set2.retainAll(map.get(7));

			if (set1.size() == 4) {
				map.put(9, set(lenSix));
			} else if (set2.size() == 3) {
				map.put(0, set(lenSix));
			} else {
				map.put(6, set(lenSix));
			}
		}

		map.put(8, set("abcdefg"));
		return map;
	}

	private static int determineOutput(String seg, HashMap<Integer, Set<Character>> signals) {
		String[] output = seg.split("\\|")[1].trim().split(" ");
		String result = "";
		for (String s : output) {
			Set<Character> set = set(s);
			for (Entry<Integer, Set<Character>> e : signals.entrySet()) {
				if (e.getValue().equals(set)) {
					result += e.getKey() + "";
				}
			}
		}
		return Integer.parseInt(result);
	}

	private static List<String> getSegmentsOfLength(String[] signal, int length) {
		return Stream.of(signal).filter(s -> s.length() == length).collect(Collectors.toList());
	}

	private static Set<Character> set(String s) {
		Set<Character> set = new HashSet<>();
		for (int i = 0; i < s.length(); i++) {
			set.add(s.charAt(i));
		}
		return set;
	}
}
