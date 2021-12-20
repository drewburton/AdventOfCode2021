package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static int i = 0;
	public static void main(String[] args) {
		//File file = new File("day16/test.txt");
		File file = new File("day16/input.txt");
		try (Scanner scanner = new Scanner(file)) {
			String[] split = scanner.nextLine().split("|");
			String line = "";
			for (int i = 0; i < split.length; i++) {
				String temp = "";
				temp = Integer.toBinaryString(Integer.parseInt(split[i], 16));
				while (temp.length() < 4) {
					temp = "0" + temp;
				}
				line += temp;
			}
			List<SubPacket> packets = parsePackets(line, 1);
			System.out.println(getVersionSum(packets));
		} catch (FileNotFoundException e) {
			System.out.println("unable to to open file");
		}
	}
	
	private static int getVersionSum(List<SubPacket> packets) {
		int versionSum = 0;
		for (SubPacket s : packets) {
			versionSum += s.versionSum();
		}
		return versionSum;
	}

	private static List<SubPacket> parsePackets(String line, int start, int end) {
		List<SubPacket> packets = new ArrayList<>();
		while (i < end) {
			if (i > end) System.out.println("BAD THING HAPPENED");
			packets.add(parsePacket(line));
		}
		return packets;
	}

	private static List<SubPacket> parsePackets(String line, int num) {
		List<SubPacket> packets = new ArrayList<>();
		while (packets.size() < num) {
			packets.add(parsePacket(line));	
		}
		return packets;
	}

	private static SubPacket parsePacket(String line) {
		int version = getVersion(line);
		int typeID = getTypeID(line);
		if (typeID == 4) {
			return getLiteral(line, version, typeID);
		} else {
			return getOperator(line, version, typeID);
		}
	}

	private static int getVersion(String line) {
		int version = Integer.parseInt(line.substring(i, i + 3), 2);
		i += 3;
		return version;
	}

	private static int getTypeID(String line) {
		int typeID = Integer.parseInt(line.substring(i, i + 3), 2);
		i += 3;
		return typeID;
	}

	private static LiteralPacket getLiteral(String line, int version, int typeID) {
		StringBuilder value = new StringBuilder();
		char startChar;
		do {
			startChar = line.charAt(i);
			i++;
			value.append(line.substring(i, i + 4));
			i += 4;
		} while (startChar != '0');
		return new LiteralPacket(version, typeID, Long.parseLong(value.toString(), 2));
	}

	private static OperatorPacket getOperator(String line, int version, int typeID) {
		int lengthTypeID = Integer.parseInt(line.substring(i, i + 1));
		i++;
		List<SubPacket> subPackets;
		if (lengthTypeID == 0) {
			// 15 bits tells length of subpackets
			int lengthSub = Integer.parseInt(line.substring(i, i + 15),2);
			i += 15;
			subPackets = parsePackets(line, i, i + lengthSub);
		} else {
			// 11 bits tells number of subpackets
			int numSub = Integer.parseInt(line.substring(i, i + 11), 2);
			i += 11;
			subPackets = parsePackets(line, numSub);
		}
		return new OperatorPacket(version, typeID, subPackets);
	}
}

abstract class SubPacket {
	protected int version;
	protected int typeID;

	SubPacket(int version, int typeID) {
		this.version = version;
		this.typeID = typeID;
	}

	public abstract int versionSum();
}

class OperatorPacket extends SubPacket {
	private List<SubPacket> containedPackets;

	public OperatorPacket(int version, int typeID, List<SubPacket> containedPackets) {
		super(version, typeID);
		this.containedPackets = containedPackets;	
	}

	@Override
	public int versionSum() {
		int versionSum = version;
		for (SubPacket s : containedPackets) {
			versionSum += s.versionSum();
		}
		return versionSum;
	}
}

class LiteralPacket extends SubPacket {
	private long value;

	public LiteralPacket(int version, int typeID, long value) {
		super(version, typeID);
		this.value = value;
	}

	@Override
	public int versionSum() {
		return version;
	}
}
