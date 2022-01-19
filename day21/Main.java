package day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	private static HashMap<String, Long> player1Paths = new HashMap<>();
	private static HashMap<String, Long> player2Paths = new HashMap<>();

	private static Map<Integer, Integer> rollOutcomes = Stream.of(new Object[][] {
		{3, 1},
		{4, 3},
		{5, 6},
		{6, 7},
		{7, 6},
		{8, 3},
		{9, 1}
	}).collect(Collectors.toMap(data -> (int) data[0], data -> (int) data[1]));
	public static void main(String[] args) {
		File file = new File("day21/test.txt");
		//File file = new File("day21/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			Pattern pattern = Pattern.compile("Player \\d starting position: (?<num1>\\d+)Player \\d starting position: (?<num2>\\d+)");
			String players = scanner.nextLine() + scanner.nextLine();
			Matcher matcher = pattern.matcher(players);

			int player1Position = 0;
			int player2Position = 0;
			if(matcher.find()) {
				player1Position = Integer.parseInt(matcher.group("num1"));
				player2Position = Integer.parseInt(matcher.group("num2"));
			}

			player1Paths.put(player1Position + "", 1l);
			player2Paths.put(player2Position + "", 1l);

			while(hasNext(player1Paths)) {
				player1Paths = next(player1Paths);
			}

			while(hasNext(player2Paths)) {
				player2Paths = next(player2Paths);
			}

			long player1Victories = 0;
			long player2Victories = 0;
			for (var entry1 : player1Paths.entrySet()) {
				int path1Length = entry1.getKey().split("->").length;
				for (var entry2 : player2Paths.entrySet()) {
					int path2Length = entry2.getKey().split("->").length;
					if (path2Length > path1Length) {
						player2Victories += entry2.getValue() * entry1.getValue();
					} else {
						player1Victories += entry1.getValue() * entry2.getValue();
					}
				}
			}
			System.out.println("player1: " + player1Victories);
			System.out.println("player2: " + player2Victories);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}

	private static int pathSum(String path) {
		return Arrays.stream(path.split("->")).mapToInt(s -> Integer.parseInt(s)).sum();
	}

	private static boolean hasNext(HashMap<String, Long> paths) {
		for(String path : paths.keySet()) {
			if (pathSum(path) < 21)
				return true;
		}
		return false;
	}

	private static HashMap<String, Long> next(HashMap<String, Long> paths) {
		HashMap<String, Long> newPaths = new HashMap<>();
		for (var entry : paths.entrySet()) {
			if (pathSum(entry.getKey()) >= 21) {
				newPaths.put(entry.getKey(), entry.getValue());
				continue;
			}
			String[] positions = entry.getKey().split("->");
			int lastPosition = Integer.parseInt(positions[positions.length - 1]);
			for (var roll : rollOutcomes.entrySet()) {
				int newPos = lastPosition + roll.getKey();
				if (newPos > 10) newPos -= 10;
				newPaths.put(entry.getKey() + "->" + newPos, roll.getValue() * entry.getValue());
			}
		}
		return newPaths;
	}
	
}
