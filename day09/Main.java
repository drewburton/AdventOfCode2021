package day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
	private static HashSet<String> basinIndexes;
	public static void main(String[] args) {
		basinIndexes = new HashSet<>();
		try {
			int[][] nums = parse();
			//sumLowPointRisk(nums);
			largestBasins(nums);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}	

	private static int[][] parse() throws FileNotFoundException{
		//File file = new File("day09/test.txt");
		File file = new File("day09/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			ArrayList<int[]> input = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String[] split = scanner.nextLine().split("|");
				int[] line = Arrays.stream(split).mapToInt(s -> Integer.parseInt(s)).toArray();
				input.add(line);
			}	
			int[][] parsed = new int[input.size()][input.get(0).length];
			parsed = input.toArray(parsed);
			return parsed;
		}
	}

	private static void largestBasins(int[][] nums) {
		ArrayList<Integer> basinSizes = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums[i].length; j++) {
				if (!basinIndexes.contains(i + " " + j) && nums[i][j] != 9) {
					basinSizes.add(buildBasin(nums, i, j));
				}
			}
		}
		basinSizes.sort((c1, c2) -> c2.compareTo(c1));
		System.out.println(basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2));
	}

	private static int buildBasin(int[][] nums, int i, int j) {
		if (basinIndexes.contains(i + " " + j)) return 0;
		if (nums[i][j] == 9) return 0;

		basinIndexes.add(i + " " + j);
		int size = 0;
		if (i < nums.length - 1) size += buildBasin(nums, i + 1, j);
		if (j < nums[i].length - 1) size += buildBasin(nums, i, j + 1);
		if (i > 0) size += buildBasin(nums, i - 1, j);
		if (j > 0) size += buildBasin(nums, i, j - 1);

		return size + 1;
	}
	/*
	private static void sumLowPointRisk(int[][] nums) {
		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums[i].length; j++) {
				//checks for lower
				if (i > 0 && nums[i - 1][j] < nums[i][j]) continue;
				else if (i < nums.length - 1 && nums[i + 1][j] < nums[i][j]) continue;
				else if (j > 0 && nums[i][j - 1] < nums[i][j]) continue;
				else if (j < nums[i].length - 1 && nums[i][j + 1] < nums[i][j]) continue;	
				
				//checks for all equal
				if ((i == 0 || nums[i - 1][j] == nums[i][j])
				&& (i == nums.length - 1 || nums[i + 1][j] == nums[i][j])
				&& (j == 0 || nums[i][j - 1] == nums[i][j])
				&& (j == nums[i].length - 1 || nums[i][j + 1] == nums[i][j]))
					continue;

				sum += nums[i][j] + 1;
			}
		}
		System.out.println(sum);
	}
	*/
}
