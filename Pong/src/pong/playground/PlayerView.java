package pong.playground;

import javafx.beans.value.WritableValue;
import javafx.scene.shape.Ellipse;
import pong.model.Axis;

public class PlayerView extends ItemView {

	private final Ellipse ellipse;

	public PlayerView(Ellipse ellipse) {
		this.ellipse = ellipse;
	}

	@Override
	protected WritableValue<Number> getProperty(Axis axis) {
		if (axis == Axis.X)
			return ellipse.centerXProperty();
		if (axis == Axis.Y)
			return ellipse.centerYProperty();
		return null;
	}
}
