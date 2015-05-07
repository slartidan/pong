package pong.model;

import pong.model.Axis.SpaceAxis;


public class SpaceVector extends Vector<SpaceAxis, SpaceVector> {
	
	@Override
	public Iterable<SpaceAxis> getAxises() {
		return SpaceAxis.getAll();
	}

	@Override
	protected SpaceVector createNew() {
		return new SpaceVector();
	}

	public SpaceTimeVector atTime(AxisVector time) {
		SpaceTimeVector spaceTimeVector = new SpaceTimeVector();
		for (SpaceAxis axis : getAxises())
			spaceTimeVector.set(axis, get(axis));
		spaceTimeVector.set(Axis.T, time);
		return spaceTimeVector;
	}
	
}
