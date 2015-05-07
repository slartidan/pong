package pong.model;

import java.util.Arrays;

public interface Axis {
	
	static Iterable<Axis> getAll() {
		return list(X,Y,T);
	}
	
	public interface SpaceAxis extends Axis {
		static Iterable<SpaceAxis> getAll() {
			return list(X,Y);
		}
	}
	class XAxis implements SpaceAxis {
		private XAxis() {}
	}
	class YAxis implements SpaceAxis {
		private YAxis() {}
	}
	public class TimeAxis implements Axis {
		private TimeAxis() {}
	}
	
	SpaceAxis X = new XAxis();
	SpaceAxis Y = new YAxis();
	TimeAxis T = new TimeAxis();
	
	static <T extends Axis> Iterable<T> list(@SuppressWarnings("unchecked") T... axises) {
		return Arrays.asList(axises);
	}
}
