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
			long result = 0;
			for (SubPacket s : packets) {
				result += s.value;
			}
			System.out.println(result);
		} catch (FileNotFoundException e) {
			System.out.println("unable to to open file");
		}
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
		return new OperatorPacket(version, typeID, OperatorPacket.getValue(typeID, subPackets));
	}
}

abstract class SubPacket {
	protected int version;
	protected int typeID;
	protected long value;

	SubPacket(int version, int typeID, long value) {
		this.version = version;
		this.typeID = typeID;
		this.value = value;
	}
}

class OperatorPacket extends SubPacket {
	public OperatorPacket(int version, int typeID, long value) {
		super(version, typeID, value);
	}

	public static long getValue(int id, List<SubPacket> contained) {
		switch(id) {
			case 0:
				long result = 0;
				for (SubPacket s : contained) result += s.value;
				return result;
			case 1:
				result = 1;
				for (SubPacket s : contained) result *= s.value;
				return result;
			case 2:
				result = Long.MAX_VALUE;
				for (int i = 0; i < contained.size(); i++) result = Long.min(contained.get(i).value, result);
				return result;
			case 3:
				result = Long.MIN_VALUE;
				for (int i = 0; i < contained.size(); i++) result = Long.max(contained.get(i).value, result);
				return result;
			case 5:
				return contained.get(0).value > contained.get(1).value ? 1 : 0;
			case 6:
				return contained.get(0).value < contained.get(1).value ? 1 : 0;
			case 7:
				return contained.get(0).value == contained.get(1).value ? 1 : 0;
		}
		return 0;
	}
}

class LiteralPacket extends SubPacket {
	public LiteralPacket(int version, int typeID, long value) {
		super(version, typeID, value);
	}
}
