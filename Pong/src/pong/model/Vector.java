package pong.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Vector<T extends Axis, V extends Vector<T, V>> {
	
	private final Map<T, AxisVector> axisPositions = new HashMap<>();
	
	public V minus(V otherSpacePosition) {
		V distance = createNew();
		for (T axis : getAxises()) {
			AxisVector thisValue = this.get(axis);
			AxisVector otherValue = otherSpacePosition.get(axis);
			AxisVector axisDistance = thisValue.minus(otherValue);
			distance.set(axis, axisDistance);
		}
		return distance;
	}

	protected abstract V createNew();

	protected abstract Iterable<T> getAxises();

	public AxisVector get(T axis) {
		return axisPositions.get(axis);
	}

	public void set(T axis, AxisVector vector) {
		axisPositions.put(axis, vector);
	}  

}
