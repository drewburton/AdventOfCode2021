package com.drewburton.adventofcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ALUTest {
	private String input = "62";
	private ALU alu = new ALU(input);

	@Test
	public void inputTest() {
		alu.executeInstruction("inp x");
		assertEquals(alu.getVariable('x'), Integer.parseInt(input.substring(0, 1)));
	}

	@Test
	public void addTest() {
		alu.executeInstruction("add t 3");
		assertEquals(alu.getVariable('t'), 3);
	}

	@Test
	public void multiplyTest() {
		alu.executeInstruction("mul q 0");
		assertEquals(alu.getVariable('q'), 0);
	}

	@Test
	public void divideTest() {
		alu.executeInstruction("inp d");
		alu.executeInstruction("inp b");
		alu.executeInstruction("div d b");
		assertEquals(alu.getVariable('d'), 
		Integer.parseInt(input.substring(0, 1)) / Integer.parseInt(input.substring(1, 2)));
	}

	@Test
	public void divideByZeroTest() {
		try {
			alu.executeInstruction("div x 0");
			throw new AssertionError();
		} catch (ArithmeticException e) {
			return;
		}
	}
}
