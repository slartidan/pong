package pong.playground;

import java.util.ArrayList;
import java.util.List;

import pong.model.Axis;
import pong.model.AxisVector;
import pong.model.Item;
import pong.model.ItemDirection;
import pong.model.Player;
import pong.model.SpaceTimeVector;
import pong.model.SpaceVector;
import pong.playground.PlaygroundView.OnUserWantsToMoveListener;


public class PlaygroundPresenter {

	private static final double PLAYER_VELOCITY = 0.001d;
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
		
		SpaceVector currentSpacePosition = playerView.getCurrentSpacePosition();
		SpaceTimeVector start = currentSpacePosition.atTime(AxisVector.ofLength(0d));
		player.positions.add(start);

		if (direction != ItemDirection.STOP) {
			SpaceTimeVector target = calculateTargetSpaceTimeVector(direction, player, currentSpacePosition);
			player.positions.add(target);
		}

		playerView.refresh(player);

	}

	private SpaceTimeVector calculateTargetSpaceTimeVector(ItemDirection direction, Player player, SpaceVector currentSpacePosition) {
		SpaceVector target = new SpaceVector();
		target.set(Axis.X, currentSpacePosition.get(Axis.X));
		double targetY = 1d;
		double velocity = PLAYER_VELOCITY;
		if (direction == ItemDirection.UPWARDS) {
			targetY *= -1d;
			velocity *= -1d;
		}
		target.set(Axis.Y, AxisVector.ofLength(targetY));

		AxisVector distance = target.get(Axis.Y).minus(currentSpacePosition.get(Axis.Y));
		AxisVector duration = distance.dividedBy(AxisVector.ofLength(velocity));

		return target.atTime(duration);
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

	private static SpaceTimeVector rightTopAt5000() {
		return spaceTimePosition(3d, -1d, 5000l);
	}

	private static SpaceTimeVector leftBottomAtStart() {
		return spaceTimePosition(0d, 0d, 0l);
	}

	private static SpaceTimeVector spaceTimePosition(double x, double y, long t) {
		SpaceTimeVector spaceTimePosition = new SpaceTimeVector();
		spaceTimePosition.set(Axis.X, AxisVector.ofLength(x));
		spaceTimePosition.set(Axis.Y, AxisVector.ofLength(y));
		spaceTimePosition.set(Axis.T, AxisVector.ofLength(t));
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
