package day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
	public static String[] numbers;
	public static ArrayList<Board> boards;
	public static void main(String[] args) {
		Scanner scanner;
		try {
			//File file = new File("day04/test.txt");
			File file = new File("day04/input.txt");
			scanner = new Scanner(file);
		} catch(FileNotFoundException e) {
			System.out.println("unable to open file");
			return;
		}

		parse(scanner);
		part1();

		scanner.close();
	}

	private static void parse(Scanner scanner) {
		numbers = scanner.nextLine().split(",");
		System.out.println(scanner.nextLine());
		boards = new ArrayList<>();
		int[][] board = new int[5][5];
		int i = 0;
		while (scanner.hasNextLine()) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = scanner.nextInt();
			}
			i++;
			if (i == 5) {
				scanner.nextLine();
				i = 0;
				boards.add(new Board(board));
				board = new int[5][5];
			}
		}
	}

	private static void part1() {
		for (String numString : numbers) {
			int num = Integer.parseInt(numString);
			for (int i = 0; i < boards.size(); i++) {
				boards.get(i).select(num);
				boolean isComplete = boards.get(i).isComplete();
				if (isComplete && boards.size() == 1) {
					System.out.println(boards.get(i).score() * num);
					return;
				}
				else if (isComplete) {
					boards.remove(i);
					i--;
				}
			}
		}
	}
}

class Board {
	private int[][] board;
	private ArrayList<HashSet<Integer>> rows;
	private ArrayList<HashSet<Integer>> columns;

	public Board(int[][] board) {
		this.board = board;
		rows = new ArrayList<>(Arrays.asList(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));
		columns = new ArrayList<>(Arrays.asList(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));
	}

	public void select(int number) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == number) {
					rows.get(i).add(number);
					columns.get(j).add(number);
					board[i][j] = -1;
				}
			}
		}
	}

	public boolean isComplete() {
		for (HashSet<Integer> r : rows) {
			if (r.size() == 5) return true;
		}
		for (HashSet<Integer> c : columns) {
			if (c.size() == 5) return true;
		}
		return false;
	}

	public int score() {
		int sum = 0;
		for (int[] r : board) {
			for (int s : r) {
				if (s != -1) sum += s;
			}
		}
		return sum;
	}
}
