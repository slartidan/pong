package pong.playground;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PlaygroundView {

	public interface OnUserWantsToStopListener {

		void onUserWantsToStop();

	}

	private static final KeyCode MOVE_UP_KEY = KeyCode.O;

	public interface OnUserWantsToMoveUpListener {

		void onUserWantsToMoveUp();

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

	public void addOnUserWantsToMoveUpListener(OnUserWantsToMoveUpListener listener) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (MOVE_UP_KEY.equals(event.getCode()))
					listener.onUserWantsToMoveUp();
			}
		});
	}

	public void addOnUserWantsToStopListener(OnUserWantsToStopListener listener) {
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (MOVE_UP_KEY.equals(event.getCode()))
					listener.onUserWantsToStop();
			}
		});
	}
}
