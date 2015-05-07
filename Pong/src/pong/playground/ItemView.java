package pong.playground;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import pong.model.Axis;
import pong.model.Axis.SpaceAxis;
import pong.model.AxisVector;
import pong.model.Item;
import pong.model.SpaceTimeVector;
import pong.model.SpaceVector;

public abstract class ItemView {
	
	private static final double PIXELS_PER_SPACE_AXIS_LENGTH = 100d;
	private final Timeline timeline = new Timeline();
	
	public void refresh(Item ball) {
		timeline.stop();
		updateKeyFrames(ball, timeline.getKeyFrames());
		timeline.play();
	}

	private void updateKeyFrames(Item ball, ObservableList<KeyFrame> keyFrames) {
		keyFrames.clear();
		for (SpaceTimeVector spaceTimePosition : ball.positions)
			keyFrames.add(toKeyFrame(spaceTimePosition));
	}
	
	public SpaceVector getCurrentSpacePosition() {
		SpaceVector spacePosition = new SpaceVector();
		for (SpaceAxis axis : spacePosition.getAxises())
			spacePosition.set(axis, getAxisPositionFromPixels(getProperty(axis).getValue()));
		return spacePosition;
	}
	
	private KeyFrame toKeyFrame(SpaceTimeVector spaceTimePosition) {
		List<KeyValue> targetPositions = new ArrayList<>();
		for (SpaceAxis axis : SpaceAxis.getAll())
			targetPositions.add(new KeyValue(getProperty(axis), getPixelsFromAxisPosition(spaceTimePosition.get(axis))));
		
		Duration tagetTime = getDurationFromTimePosition(spaceTimePosition.get(Axis.T));
		return new KeyFrame(tagetTime, targetPositions.toArray(new KeyValue[0]));
	}

	protected abstract WritableValue<Number> getProperty(Axis axis);

	private Duration getDurationFromTimePosition(AxisVector timePosition) {
		return Duration.millis(timePosition.getLength());
	}

	private AxisVector getAxisPositionFromPixels(Number pixels) {
		return AxisVector.ofLength(pixels.doubleValue() / PIXELS_PER_SPACE_AXIS_LENGTH);
	}

	private double getPixelsFromAxisPosition(AxisVector axisPosition) {
		return (double) (axisPosition.getLength() * PIXELS_PER_SPACE_AXIS_LENGTH);
	}

}
