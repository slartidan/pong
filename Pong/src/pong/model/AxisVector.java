package pong.model;

public class AxisVector {
	
	private double length;
	
	private AxisVector() {
	}

	public AxisVector minus(AxisVector otherValue) {
		return AxisVector.ofLength(this.length - otherValue.length);
	}

	public AxisVector dividedBy(AxisVector otherValue) {
		return AxisVector.ofLength(this.length / otherValue.length);
	}
	
	public static AxisVector ofLength(double length) {
		AxisVector axisVector = new AxisVector();
		axisVector.length = length;
		return axisVector;
	}
	
	public double getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "AxisVector [length=" + length + "]";
	}
}
