package pong.model;


public class SpaceTimeVector extends Vector<Axis, SpaceTimeVector> {
	
	@Override
	public Iterable<Axis> getAxises() {
		return Axis.getAll();
	}

	@Override
	protected SpaceTimeVector createNew() {
		return new SpaceTimeVector();
	}
}
