package day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String[] args) {
		//File file = new File("day18/test.txt");
		File file = new File("day18/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			List<SnailfishNumber> numbers = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Stack<Element> numStack = new Stack<>();
				for(int i = 0; i < line.length(); i++) {
					if (Character.isDigit(line.charAt(i))) {
						numStack.add(new Element(Integer.parseInt(line.substring(i, i + 1))));
					}
					else if (line.charAt(i) == ']') {
						Element e1 = numStack.pop();
						Element e2 = numStack.pop();
						numStack.add(new Element(e2, e1));
					}
				}
				numbers.add(new SnailfishNumber(numStack.pop()));
			}
			BigInteger largestMagnitude = BigInteger.ZERO;
			for (int i = 0; i < numbers.size() - 1; i++) {
				for (int j = i + 1; j < numbers.size(); j++) {
					SnailfishNumber n1 = SnailfishNumber.add(numbers.get(i), numbers.get(j));
					SnailfishNumber n2 = SnailfishNumber.add(numbers.get(j), numbers.get(i));
					BigInteger m1 = n1.magnitude(n1.getTree());
					BigInteger m2 = n2.magnitude(n2.getTree());
					if (m1.compareTo(largestMagnitude) > 0) largestMagnitude = m1;
					if (m2.compareTo(largestMagnitude) > 0) largestMagnitude = m2;
				}
			}
			System.out.println(largestMagnitude);
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}	
}
