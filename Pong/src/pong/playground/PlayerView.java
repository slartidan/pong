package pong.playground;

import javafx.beans.value.WritableValue;
import javafx.scene.shape.Ellipse;

public class PlayerView extends ItemView {

	private final Ellipse ellipse;

	public PlayerView(Ellipse ellipse) {
		this.ellipse = ellipse;
	}

	@Override
	protected WritableValue<Number> getXProperty() {
		return ellipse.centerXProperty();
	}

	@Override
	protected WritableValue<Number> getYProperty() {
		return ellipse.centerYProperty();
	}

}
