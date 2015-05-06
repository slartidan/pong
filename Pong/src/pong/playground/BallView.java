package pong.playground;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import pong.model.AxisPosition;
import pong.model.Ball;
import pong.model.SpaceTimePosition;
import pong.model.TimePosition;

public class BallView {

	private final Circle circle;
	private final Timeline timeline = new Timeline();

	public BallView(Circle circle) {
		this.circle = circle;
	}

	public void refresh(Ball ball) {
		timeline.stop();
		for (SpaceTimePosition spaceTimePosition : ball.positions) {
			final KeyValue x = new KeyValue(circle.centerXProperty(), getPixelsFromAxisPosition(spaceTimePosition.space.x));
			final KeyValue y = new KeyValue(circle.centerYProperty(), getPixelsFromAxisPosition(spaceTimePosition.space.y));
			final KeyFrame kf = new KeyFrame(getDurationFromTimePosition(spaceTimePosition.time), x, y);
			timeline.getKeyFrames().add(kf);
		}
		timeline.play();
	}

	private Duration getDurationFromTimePosition(TimePosition timePosition) {
		return Duration.millis(timePosition.time);
	}

	private double getPixelsFromAxisPosition(AxisPosition axisPosition) {
		return (double) (axisPosition.position * 100d);
	}

}
