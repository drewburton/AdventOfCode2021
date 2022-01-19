package com.drewburton.adventofcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        File file = new File("src/main/java/com/drewburton/adventofcode/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			ArrayList<String> instructions = new ArrayList<>();	
			while(scanner.hasNextLine()) {
				instructions.add(scanner.nextLine());
			}

			for (long i = 99999999999999l; i > 0; i--) {
				boolean end = false;
				if ((i + "").contains("0")) continue;
				ALU alu = new ALU(i + "");
				for (String s : instructions) {
					try {
						alu.executeInstruction(s);
					} catch (ArithmeticException e) {
						end = true;
						break;
					}
				}
				if (!end && alu.isValid()) {
					System.out.println(i);
					return;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
    }
}
