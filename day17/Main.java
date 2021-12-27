package day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Point;

public class Main {
	public static void main(String[] args) {
		//File file = new File("day17/test.txt");
		File file = new File("day17/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			String line = scanner.nextLine();
			Pattern pattern = Pattern.compile("target area: x=(?<x1>-?\\d+)..(?<x2>-?\\d+), y=(?<y1>-?\\d+)..(?<y2>-?\\d+)");
			Matcher matcher = pattern.matcher(line);
			Target target;
			if (matcher.find()) {
				int x1 = Integer.parseInt(matcher.group("x1"));
				int x2 = Integer.parseInt(matcher.group("x2"));
				int y1 = Integer.parseInt(matcher.group("y1"));
				int y2 = Integer.parseInt(matcher.group("y2"));
				target = new Target(x1, x2, y1, y2);
				
				int hitCount = 0;
				for (int x = 0; x < 1000; x++) {
					for (int y = -1000; y < 1000; y++) {
						Probe probe = new Probe(x, y);
						while (!target.hit(probe.getX(), probe.getY()) && 
							!target.passed(probe.getX(), probe.getY())) {
							probe.next();
						}
						if (target.hit(probe.getX(), probe.getY())) {
							hitCount++;
						}
					}
				}
				System.out.println(hitCount);
			}
		} catch (FileNotFoundException e) {
			System.out.println("unable to open file");
		}
	}
}

class Target {
	private int x1;
	private int x2;
	private int y1;
	private int y2;

	public Target(int x1, int x2, int y1, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public boolean hit(int x, int y) {
		return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
	}

	public boolean passed(int x, int y) {
		return (x > x2 || y < y1);
	}
}

class Probe {
	private int x;
	private int y;
	private int vx;
	private int vy;

	public Probe(int vx, int vy) {
		x = 0;
		y = 0;
		this.vx = vx;
		this.vy = vy;
	}

	public void next() {
		x += vx;
		y += vy;
		if (vx > 0) vx -= 1;
		else if (vx < 0) vx += 1;
		vy--;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public int getVx() { return vx; }
	public int getVy() { return vy; }
}
