package day18;

public class Element {
	private Element e1;
	private Element e2;
	private int value;

	public Element(Element e1, Element e2) {
		this.e1 = e1;
		this.e2 = e2;
		value = -1;
	}

	public Element(int value) {
		this.value = value;
		e1 = null;
		e2 = null;
	}

	public Element(int v1, int v2) {
		e1 = new Element(v1);
		e2 = new Element(v2);
	}

	public Element getLeftElement() { return e1; }
	public void setLeftElement(Element e) { e1 = e; }

	public Element getRightElement() { return e2; }
	public void setRightElement(Element e) { e2 = e; }

	public int getValue() { return value; }
	public void setValue(int value) { this.value = value; }

	public boolean isNum() { return (e1 == null) && (e2 == null); }

	@Override
	public String toString() {
		if (isNum()) return value + "";
		return "[" + e1 + "," + e2 + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		if (isNum()) {
			return new Element(value);
		}
		return new Element((Element) e1.clone(), (Element) e2.clone());
	}
}
