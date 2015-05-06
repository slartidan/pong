package pong.playground;

import java.util.ArrayList;
import java.util.Collection;


public class PlaygroundPresenter {
	
	private PlaygroundView view;
	private BallView ball;
	private final Collection<PlayerView> players = new ArrayList<PlayerView>();

	public void setView(PlaygroundView view) {
		this.view = view;
		view.loadLayoutFile();
		view.createScene();
		lookupElements(view);
		
		launchAsynchronously(view);
	}

	private void lookupElements(PlaygroundView view) {
		ball = view.lookupBall();
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
		players.add(view.lookupPlayer(playerNumber));
	}

}
