package com.uniaodk.memory_game;

import com.uniaodk.memory_game.layout.GameBoardLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {

	public static String GAME_NAME = "Memory Game";

	public static String GAME_ICON = "/assets/icon.png";

	public static int WINDOW_WIDTH = 1280;

	public static int WINDOW_HEIGTH = 800;

	public static String TEXT_LAYOUT = "-fx-background-color: red";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() throws Exception {
		Game.timerToReveal.cancel();
		Game.timerToReveal.purge();
		super.stop();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(buildRoot(), WINDOW_WIDTH, WINDOW_HEIGTH);
		Image image = new Image(GAME_ICON);
		stage.getIcons().add(image);
		stage.setScene(scene);
		stage.setTitle(GAME_NAME);
		stage.show();
	}

	public HBox buildRoot() {
		HBox hbox = new HBox(new GameBoardLayout());
		return hbox;
	}
}
