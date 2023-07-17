package com.uniaodk.memory_game;

import java.util.Timer;
import com.uniaodk.memory_game.base.MemoryGame;

public abstract class Game {

	public static String DEFAULT_CARDS_PATH = "assets/cards";

	public static String UPSIDEDOWN_CARD = "assets/logo.png";

	public static String cardsPath = DEFAULT_CARDS_PATH;

	public static Timer timerToReveal = new Timer();

	public static MemoryGame memoryGame;
	static {
		memoryGame = new MemoryGame();
	}
}
