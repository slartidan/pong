package pong.playground;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.util.Duration;
import pong.model.Axis;
import pong.model.Axis.SpaceAxis;
import pong.model.AxisVector;
import pong.model.Item;
import pong.model.SpaceTimeVector;
import pong.model.SpaceVector;

public abstract class ItemView {
	
	private final Timeline timeline = new Timeline();
	
	public void refresh(Item ball) {
		timeline.stop();
		timeline.getKeyFrames().clear();
		for (SpaceTimeVector spaceTimePosition : ball.positions) {
			final KeyFrame keyFrame = spaceTimePositionToKeyFrame(spaceTimePosition);
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.play();
	}
	
	public SpaceVector getCurrentSpacePosition() {
		SpaceVector spacePosition = new SpaceVector();
		for (SpaceAxis axis : spacePosition.getAxises())
			spacePosition.set(axis, getAxisPositionFromPixels(getProperty(axis).getValue()));
		return spacePosition;
	}
	
	private KeyFrame spaceTimePositionToKeyFrame(SpaceTimeVector spaceTimePosition) {
		List<KeyValue> values = new ArrayList<>();
		for (SpaceAxis axis : SpaceAxis.getAll())
			values.add(new KeyValue(getProperty(axis), getPixelsFromAxisPosition(spaceTimePosition.get(axis))));
		Duration duration = getDurationFromTimePosition(spaceTimePosition.get(Axis.T));
		return new KeyFrame(duration, values.toArray(new KeyValue[0]));
	}

	protected abstract WritableValue<Number> getProperty(Axis axis);

	private Duration getDurationFromTimePosition(AxisVector timePosition) {
		return Duration.millis(timePosition.getLength());
	}

	private AxisVector getAxisPositionFromPixels(Number pixels) {
		return AxisVector.ofLength(pixels.doubleValue() / 100d);
	}

	private double getPixelsFromAxisPosition(AxisVector axisPosition) {
		return (double) (axisPosition.getLength() * 100d);
	}

}
