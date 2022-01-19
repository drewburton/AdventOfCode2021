package com.drewburton.adventofcode;

import java.util.HashMap;

public class ALU {
	private HashMap<Character, Integer> variables;
	private String input;

	ALU(String input) {
		variables = new HashMap<>();
		this.input = input;
	}

	public void executeInstruction(String instruction) throws ArithmeticException {
		String[] split = instruction.split(" ");
		switch(split[0]) {
			case "inp": input(split[1]);
				break;
			case "add": add(split[1], split[2]);
				break;
			case "mul": multiply(split[1], split[2]);
				break;
			case "div": divide(split[1], split[2]);
				break;
			case "mod": mod(split[1], split[2]);
				break;
			case "eql": equals(split[1], split[2]);
				break;
		}
	}

	public boolean isValid() {
		return variables.get('z') == 0;
	}

	public int getVariable(char key) { return variables.get(key); }

	public void input(String var) {
		variables.put(var.charAt(0), Integer.parseInt(input.substring(0, 1)));
		input = input.substring(1, input.length());
	}

	private void add(String var1, String var2) {
		variables.put(var1.charAt(0), getVariableValue(var1) + getVariableValue(var2));
	}

	private void multiply(String var1, String var2) {
		variables.put(var1.charAt(0), getVariableValue(var1) * getVariableValue(var2));
	}

	private void divide(String var1, String var2) throws ArithmeticException {
		variables.put(var1.charAt(0), getVariableValue(var1) / getVariableValue(var2));
	}

	private void mod(String var1, String var2) throws ArithmeticException {
		variables.put(var1.charAt(0), getVariableValue(var1) % getVariableValue(var2));
	}

	private void equals(String var1, String var2) {
		variables.put(var1.charAt(0), getVariableValue(var1) == getVariableValue(var2) ? 1 : 0);
	}

	private int getVariableValue(String var) {
		try {
			return Integer.parseInt(var);
		} catch (NumberFormatException e) {
			if (variables.containsKey(var.charAt(0))) {
				return variables.get(var.charAt(0));
			}
			return 0;
		}
	}
}