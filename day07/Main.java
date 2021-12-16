package day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		int[] nums;
		try { nums = parse(); }
		catch (FileNotFoundException e) { System.out.println("unable to open file"); return; }

		Arrays.sort(nums);

		double mean = 0;
		for (int num : nums) mean += num;
		mean /= nums.length + 0.0;
		int center = (int) Math.ceil(mean);

		/*
		int median;
		if (nums.length % 2 == 0) median = (nums[nums.length / 2] + nums[(nums.length / 2) - 1]) / 2;
		else median = nums[nums.length / 2];
		*/

		System.out.println(cost(nums, center));
	}

	private static int[] parse() throws FileNotFoundException {
		//File file = new File("day07/test.txt");
		File file = new File("day07/input.txt");
		String[] list;
		try (Scanner scanner = new Scanner(file)) {
			list = scanner.nextLine().split(",");
		} 

		int[] nums = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			nums[i] = Integer.parseInt(list[i]);
		}
		return nums;
	}

	private static int cost(int[] nums, int center) {
		int sum = 0;
		for (int num : nums) {
			int diff = Math.abs(num - center);
			sum += ((diff + 1) * diff) / 2;
		}
		return sum;
	}
}
