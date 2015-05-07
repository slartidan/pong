package pong.playground;

import java.util.HashMap;
import java.util.Map;

import pong.model.Axis;
import pong.model.AxisVector;
import pong.model.Item;
import pong.model.ItemDirection;
import pong.model.Player;
import pong.model.PlayerIdentifier;
import pong.model.SpaceTimeVector;
import pong.model.SpaceVector;
import pong.playground.PlaygroundView.OnUserWantsToMoveListener;


public class PlaygroundPresenter {

	private static final double PLAYER_VELOCITY = 0.001d;
	private PlaygroundView view;
	private BallView ballView;
	private final Map<PlayerIdentifier, PlayerView> playerViews = new HashMap<>();
	private final Map<PlayerIdentifier, Player> players = new HashMap<>();

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
			public void onUserWantsToMove(PlayerIdentifier player, ItemDirection direction) {
				PlaygroundPresenter.this.onUserWantsToMove(player, direction);
			}
		});
	}

	protected void onUserWantsToMove(PlayerIdentifier playerIdentifier, ItemDirection direction) {
		Player player = players.get(playerIdentifier);
		if (!player.directionChange(direction))
			return;

		PlayerView playerView = playerViews.get(playerIdentifier);

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
		getPlayer(PlayerIdentifier.LEFT);
		getPlayer(PlayerIdentifier.RIGHT);
	}

	private void launchAsynchronously(PlaygroundView view) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				view.launch();
			}

		}).start();
	}

	private void getPlayer(PlayerIdentifier playerIdentifier) {
		players.put(playerIdentifier, new Player());
		playerViews.put(playerIdentifier, view.lookupPlayer(playerIdentifier));
	}

}
