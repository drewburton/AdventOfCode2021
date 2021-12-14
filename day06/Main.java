package day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner;
		try {
			//File file = new File("day06/test.txt");
			File file = new File("day06/input.txt");
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
			return;
		}

		HashMap<Integer, BigInteger> buckets = new HashMap<>();
		for (int i = 0; i < 9; i++) {
			buckets.put(i, BigInteger.ZERO);
		}

		String[] split = scanner.nextLine().split(",");
		for (String s : split) {
			int time = Integer.parseInt(s);
			buckets.put(time, buckets.get(time).add(BigInteger.ONE));
		}

		for (int time = 0; time < 256; time++) {
			shift(buckets);
		}
		BigInteger sum = BigInteger.ZERO;
		for (BigInteger amount : buckets.values()) {
			sum = sum.add(amount);
		}
		System.out.println(sum);
		scanner.close();
	}	

	public static void shift(HashMap<Integer, BigInteger> buckets) {
		BigInteger temp = buckets.get(0);
		for (int i = 0; i < 8; i++) {
			buckets.put(i, buckets.get(i + 1));
		}
		buckets.put(6, buckets.get(6).add(temp));
		buckets.put(8, temp);
	}

	/*
	public static void next(ArrayList<Integer> fish) {
		int fishToAdd = 0;
		for (int i = 0; i < fish.size(); i++) {
			if (fish.get(i) == 0) {
				fish.set(i, 6);
				fishToAdd++;
			} else {
				fish.set(i, fish.get(i) - 1);
			}
		}
		while (fishToAdd > 0) {
			fish.add(8);
			fishToAdd--;
		}
	}
	*/
}
