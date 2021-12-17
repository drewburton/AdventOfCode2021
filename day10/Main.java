package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String[] args) {
		//File file = new File("day10/test.txt");
		File file = new File("day10/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			ArrayList<Long> scores = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!isCorrupted(line)) {
					scores.add(parseIncomplete(line));
				}
			}
			scores.sort((c1, c2) -> c1.compareTo(c2));
			System.out.println(scores.get(scores.size() / 2));
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}	

	private static long parseIncomplete(String s) {
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < s.length(); i++) {
			switch(s.charAt(i)) {
				case '{':
				case '(':
				case '[':
				case '<':
					stack.add(s.charAt(i));
					break;
				case '}':
				case ')':
				case ']':
				case '>':
					stack.pop();
					break;
			}
		}
		long score = 0;
		while (stack.size() > 0) {
			char c = stack.pop();
			switch(c) {
				case '{': score *= 5;
					score += 3;
					break;
				case '(': score *= 5;
					score += 1;
					break;
				case '[': score *= 5;
					score += 2;
					break;
				case '<': score *= 5;
					score += 4;
					break;
			}
		}
		return score;
	}

	private static boolean isCorrupted(String s) {
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < s.length(); i++) {
			switch(s.charAt(i)) {
				case '{':
				case '(':
				case '[':
				case '<':
					stack.add(s.charAt(i));
					break;
				case '}':
					if (!stack.pop().equals('{')) return true;
					break;
				case ')':
					if (!stack.pop().equals('(')) return true;
					break;
				case ']':
					if (!stack.pop().equals('[')) return true;
					break;
				case '>':
					if (!stack.pop().equals('<')) return true;
					break;
			}
		}
		return false;
	}
}
