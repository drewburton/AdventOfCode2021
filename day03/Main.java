package day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class Main {
	public static void main(String[] args) {
		Scanner scanner;
		try {
			//File file = new File("day03/test.txt");
			File file = new File("day03/input.txt");
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
			return;
		}
		ArrayList<String> report = new ArrayList<>();
		while (scanner.hasNextLine()) {
			report.add(scanner.nextLine());
		}
		part2(report);
		scanner.close();
	}
	/*	
	private static void part1(ArrayList<String> report) {
		String gammaRate = "";
		String epsilonRate = "";
		HashMap<Integer, Integer> commonalities = new HashMap<>();
		for (String number : report) {
			for (int i = 0; i < number.length(); i++) {
				commonalities.putIfAbsent(i, 0);
				if (number.charAt(i) == '1')
					commonalities.put(i, commonalities.get(i) + 1);
			}	
		}
		for (int i = 0; i < report.get(0).length(); i++) {
			if (commonalities.get(i) > (report.size() / 2.0)) {
				gammaRate += "1";
				epsilonRate += "0";
			} else {
				gammaRate += "0";
				epsilonRate += "1";
			}
		}
		System.out.println(Integer.parseInt(gammaRate, 2) * Integer.parseInt(epsilonRate, 2));
	}
	*/
	private static void part2(ArrayList<String> report) {
		Collections.sort(report);
		System.out.println(ogr(report, 0) * co2sr(report, 0));
	}

	private static int ogr(List<String> n, int index) {
		if (n.size() == 1) {
			return Integer.parseInt(n.get(0), 2);
		}
		int i = 0;
		while (i < n.size() && n.get(i).charAt(index) != '1')
			i++;
		if (i <= n.size() / 2) {
			return ogr(n.subList(i, n.size()), index + 1);
		}
		return ogr(n.subList(0, i), index + 1);
	}

	private static int co2sr(List<String> n, int index) {
		if (n.size() == 1) {
			return Integer.parseInt(n.get(0), 2);
		}
		int i = 0;
		while (i < n.size() && n.get(i).charAt(index) != '1')
			i++;
		if (i <= n.size() / 2) {
			return co2sr(n.subList(0, i), index + 1);
		}
		return co2sr(n.subList(i, n.size()), index + 1);
	}
}
