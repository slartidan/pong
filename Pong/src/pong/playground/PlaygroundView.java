package pong.playground;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pong.model.ItemDirection;

public class PlaygroundView {

	public interface OnUserWantsToMoveListener {

		void onUserWantsToMove(ItemDirection direction);

	}

	public static class JavafxApplication extends Application {

		public static PlaygroundView view;

		static void launchApplication(PlaygroundView view) {
			JavafxApplication.view = view;
			Application.launch(JavafxApplication.class, new String[0]);
		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			primaryStage.setScene(view.scene);
			primaryStage.show();
		}
	}

	private Pane rootLayout;
	private Scene scene;
	private static final Map<KeyCode, ItemDirection> buttonDirections = new HashMap<>();
	static {
		buttonDirections.put(KeyCode.O, ItemDirection.UPWARDS);
		buttonDirections.put(KeyCode.L, ItemDirection.DOWNWARDS);
	}

	public BallView lookupBall() {
		return new BallView(lookup("#ball"));
	}

	public PlayerView lookupPlayer(int i) {
		return new PlayerView(lookup("#player"+i));
	}

	@SuppressWarnings("unchecked")
	private <T extends Node> T lookup(String id) {
		return (T) scene.lookup(id);
	}

	public void launch() {
		JavafxApplication.launchApplication(this);
	}

	public void createScene() {
		scene = new Scene(rootLayout);
	}

	public void loadLayoutFile() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(PlaygroundView.class.getResource("pong.fxml"));
		try {
			rootLayout = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addOnUserWantsToMoveListener(OnUserWantsToMoveListener listener) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				ItemDirection direction = buttonDirections .get(event.getCode());
				listener.onUserWantsToMove(direction);
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				ItemDirection direction = buttonDirections .get(event.getCode());
				listener.onUserWantsToMove(direction == null ? null : ItemDirection.STOP);
			}
		});
	}
}
