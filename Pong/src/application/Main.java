package application;

import pong.playground.PlaygroundPresenter;
import pong.playground.PlaygroundView;


public class Main {

	public static void main(String[] args) {
		PlaygroundPresenter playgroundPresenter = new PlaygroundPresenter();
		PlaygroundView playgroundView = new PlaygroundView();
		playgroundPresenter.setView(playgroundView);
	}
}
