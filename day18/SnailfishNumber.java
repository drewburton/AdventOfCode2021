package day18;

import java.math.BigInteger;

public class SnailfishNumber {
	private Element tree;
	
	public SnailfishNumber(Element tree) {
		this.tree = tree;
	}

	public static SnailfishNumber add(SnailfishNumber n1, SnailfishNumber n2) {
		try {
			Element tree = (Element) (new Element(n1.tree, n2.tree).clone());
			int depth = checkExplode(tree);
			boolean split = checkSplit(tree);
			while (depth > 4 || split) {
				if (depth > 4) {
					explode(tree, 0);
				} else if (split) {
					split(tree);
				}
				depth = checkExplode(tree);
				split = checkSplit(tree);
			}
			return new SnailfishNumber(tree);
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	private static int checkExplode(Element e) {
		if (e.isNum()) return 0;
		return Math.max(checkExplode(e.getLeftElement()), checkExplode(e.getRightElement())) + 1;
	}

	private static boolean checkSplit(Element e) {
		if (e.isNum()) return e.getValue() > 9;
		return checkSplit(e.getLeftElement()) || checkSplit(e.getRightElement());
	}

	public static Element explode(Element e, int depth) {
		if (depth <= 4 && e.isNum()) return null;
		if (depth < 4 && e.getLeftElement().isNum() && e.getRightElement().isNum()) return null;
		else if (depth == 4 && e.getLeftElement().isNum() && e.getRightElement().isNum()) return e;

		Element explode = explode(e.getLeftElement(), depth + 1);
		if (explode != null && depth == 3) {
			e.setLeftElement(new Element(0));
		}
		if (explode != null && (explode.getLeftElement().getValue() != 0 || explode.getRightElement().getValue() != 0)) {
			if (e.getRightElement() != null) {
				updateRight(e.getRightElement(), explode.getRightElement().getValue());
				explode.setRightElement(new Element(0));
			}
		} else if (explode == null) {
			explode = explode(e.getRightElement(), depth + 1);
			if (explode != null && depth == 3) {
				e.setRightElement(new Element(0));
			}
			if (explode != null && (explode.getLeftElement().getValue() != 0 || explode.getRightElement().getValue() != 0)) {
				updateLeft(e.getLeftElement(), explode.getLeftElement().getValue());
				explode.setLeftElement(new Element(0));
			}
		}
		return explode;
	}

	private static void updateRight(Element e, int explodeValue) {
		while (e.getLeftElement() != null) {
			e = e.getLeftElement();
		}
		e.setValue(e.getValue() + explodeValue);
	}

	private static void updateLeft(Element e, int explodeValue) {
		while (e.getRightElement() != null) {
			e = e.getRightElement();
		}
		e.setValue(e.getValue() + explodeValue);
	}

	public static boolean split(Element e) {
		if (e.isNum() && e.getValue() > 9) {
			e.setLeftElement(new Element(e.getValue() / 2));
			e.setRightElement(new Element((int) Math.ceil(e.getValue() / 2.0)));
			e.setValue(-1);
			return true;
		}
		if (e.getLeftElement() != null && split(e.getLeftElement())) return true;
		return e.getRightElement() != null && split(e.getRightElement());
	}

	public BigInteger magnitude(Element e) {
		Element left = e.getLeftElement();
		Element right = e.getRightElement();
		if (left.isNum() && right.isNum()) {
			return BigInteger.valueOf(3)
				.multiply(BigInteger.valueOf(left.getValue()))
				.add(BigInteger.TWO.multiply(BigInteger.valueOf(right.getValue())));
		} else if (left.isNum()) {
			return BigInteger.valueOf(3)
				.multiply(BigInteger.valueOf(left.getValue()))
				.add(BigInteger.TWO.multiply(magnitude(right)));
		} else if (right.isNum()) {
			return BigInteger.valueOf(3)
				.multiply(magnitude(left))
				.add(BigInteger.TWO.multiply(BigInteger.valueOf(right.getValue())));
		} else {
			return BigInteger.valueOf(3)
				.multiply(magnitude(left))
				.add(BigInteger.TWO.multiply(magnitude(right)));
		}
	}

	public Element getTree() { return tree; }
}


