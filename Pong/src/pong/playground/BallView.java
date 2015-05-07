package pong.playground;

import javafx.beans.value.WritableValue;
import javafx.scene.shape.Circle;

public class BallView extends ItemView {

	private final Circle circle;

	public BallView(Circle circle) {
		this.circle = circle;
	}

	@Override
	protected WritableValue<Number> getXProperty() {
		return circle.centerXProperty();
	}

	@Override
	protected WritableValue<Number> getYProperty() {
		return circle.centerYProperty();
	}

}
