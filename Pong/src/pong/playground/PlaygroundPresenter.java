package pong.playground;

import java.util.ArrayList;
import java.util.Collection;

import pong.model.AxisPosition;
import pong.model.Ball;
import pong.model.SpacePosition;
import pong.model.SpaceTimePosition;
import pong.model.TimePosition;


public class PlaygroundPresenter {
	
	private PlaygroundView view;
	private BallView ballView;
	private final Collection<PlayerView> playerViews = new ArrayList<PlayerView>();

	public void setView(PlaygroundView view) {
		this.view = view;
		view.loadLayoutFile();
		view.createScene();
		lookupElements(view);
		
		launchAsynchronously(view);
		
		Ball ball = new Ball();
		ball.positions.add(leftBottomAtStart());
		ball.positions.add(rightTopAt5000());
		
		ballView.refresh(ball);
	}

	private SpaceTimePosition rightTopAt5000() {
		return spaceTimePosition(3d, -1d, 5000l);
	}

	private SpaceTimePosition leftBottomAtStart() {
		return spaceTimePosition(0d, 0d, 0l);
	}

	private SpaceTimePosition spaceTimePosition(double x, double y, long t) {
		SpaceTimePosition spaceTimePosition = new SpaceTimePosition();
		spaceTimePosition.space = new SpacePosition();
		spaceTimePosition.space.x = new AxisPosition();
		spaceTimePosition.space.x.position = x;
		spaceTimePosition.space.y = new AxisPosition();
		spaceTimePosition.space.y.position = y;
		spaceTimePosition.time = new TimePosition();
		spaceTimePosition.time.time = t;
		return spaceTimePosition;
	}

	private void lookupElements(PlaygroundView view) {
		ballView = view.lookupBall();
		getPlayer(1);
		getPlayer(2);
	}

	private void launchAsynchronously(PlaygroundView view) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				view.launch();
			}
			
		}).start();
	}

	private void getPlayer(int playerNumber) {
		playerViews.add(view.lookupPlayer(playerNumber));
	}

}
