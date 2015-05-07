package pong.playground;

import javafx.beans.value.WritableValue;
import javafx.scene.shape.Circle;
import pong.model.Axis;

public class BallView extends ItemView {

	private final Circle circle;

	public BallView(Circle circle) {
		this.circle = circle;
	}

	@Override
	protected WritableValue<Number> getProperty(Axis axis) {
		if (axis == Axis.X)
			return circle.centerXProperty();
		if (axis == Axis.Y)
			return circle.centerYProperty();
		return null;
	}
}
