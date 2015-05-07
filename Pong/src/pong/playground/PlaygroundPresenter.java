package pong.playground;

import java.util.ArrayList;
import java.util.List;

import pong.model.AxisPosition;
import pong.model.Item;
import pong.model.ItemDirection;
import pong.model.Player;
import pong.model.SpacePosition;
import pong.model.SpaceTimePosition;
import pong.model.TimePosition;
import pong.playground.PlaygroundView.OnUserWantsToMoveListener;


public class PlaygroundPresenter {

	private PlaygroundView view;
	private BallView ballView;
	private final List<PlayerView> playerViews = new ArrayList<>();
	private final List<Player> players = new ArrayList<>();

	public void setView(PlaygroundView view) {
		this.view = view;
		view.loadLayoutFile();
		view.createScene();
		lookupElements(view);

		launchAsynchronously(view);

		addMovementListeners(view);

		ballMovement();
	}

	private void addMovementListeners(PlaygroundView view) {
		view.addOnUserWantsToMoveListener(new OnUserWantsToMoveListener() {

			@Override
			public void onUserWantsToMove(ItemDirection direction) {
				PlaygroundPresenter.this.onUserWantsToMove(direction);
			}
		});
	}

	protected void onUserWantsToMove(ItemDirection direction) {
		Player player = getCurrentPlayer();
		if (!player.directionChange(direction))
			return;

		PlayerView playerView = getCurrentPlayerView();

		player.positions.clear();
		SpaceTimePosition start = new SpaceTimePosition();
		start.space = playerView.getCurrentSpacePosition();
		start.time = new TimePosition();
		player.positions.add(start);

		if (direction == ItemDirection.UPWARDS)
			player.positions.add(topAt1000());
		if (direction == ItemDirection.DOWNWARDS)
			player.positions.add(bottomAt1000());

		playerView.refresh(player);

	}

	protected Player getCurrentPlayer() {
		return players.get(0);
	}

	private PlayerView getCurrentPlayerView() {
		return playerViews.get(0);
	}

	private void ballMovement() {
		Item item = new Item();
		item.positions.add(leftBottomAtStart());
		item.positions.add(rightTopAt5000());

		ballView.refresh(item);
	}

	private static SpaceTimePosition topAt1000() {
		return spaceTimePosition(0d, -1d, 1000l);
	}

	private static SpaceTimePosition bottomAt1000() {
		return spaceTimePosition(0d, 1d, 1000l);
	}

	private static SpaceTimePosition rightTopAt5000() {
		return spaceTimePosition(3d, -1d, 5000l);
	}

	private static SpaceTimePosition leftBottomAtStart() {
		return spaceTimePosition(0d, 0d, 0l);
	}

	private static SpaceTimePosition spaceTimePosition(double x, double y, long t) {
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
		players.add(new Player());
		playerViews.add(view.lookupPlayer(playerNumber));
	}

}
