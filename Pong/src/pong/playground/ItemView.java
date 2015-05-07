package pong.playground;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.util.Duration;
import pong.model.AxisPosition;
import pong.model.Item;
import pong.model.SpacePosition;
import pong.model.SpaceTimePosition;
import pong.model.TimePosition;

public abstract class ItemView {
	
	private final Timeline timeline = new Timeline();
	
	public void refresh(Item ball) {
		timeline.stop();
		timeline.getKeyFrames().clear();
		for (SpaceTimePosition spaceTimePosition : ball.positions) {
			final KeyFrame keyFrame = spaceTimePositionToKeyFrame(spaceTimePosition);
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.play();
	}
	
	public SpacePosition getCurrentSpacePosition() {
		SpacePosition spacePosition = new SpacePosition();
		spacePosition.x = getAxisPositionFromPixels(getXProperty().getValue());
		spacePosition.y = getAxisPositionFromPixels(getYProperty().getValue());
		return spacePosition;
	}
	
	private KeyFrame spaceTimePositionToKeyFrame(SpaceTimePosition spaceTimePosition) {
		final KeyValue x = new KeyValue(getXProperty(), getPixelsFromAxisPosition(spaceTimePosition.space.x));
		final KeyValue y = new KeyValue(getYProperty(), getPixelsFromAxisPosition(spaceTimePosition.space.y));
		final KeyFrame kf = new KeyFrame(getDurationFromTimePosition(spaceTimePosition.time), x, y);
		return kf;
	}

	protected abstract WritableValue<Number> getXProperty();
	protected abstract WritableValue<Number> getYProperty();

	private Duration getDurationFromTimePosition(TimePosition timePosition) {
		return Duration.millis(timePosition.time);
	}

	private AxisPosition getAxisPositionFromPixels(Number pixels) {
		AxisPosition axisPosition = new AxisPosition();
		axisPosition.position = pixels.doubleValue() / 100d;
		return axisPosition;
	}

	private double getPixelsFromAxisPosition(AxisPosition axisPosition) {
		return (double) (axisPosition.position * 100d);
	}

}
