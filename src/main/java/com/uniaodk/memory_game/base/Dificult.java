package com.uniaodk.memory_game.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Dificult {

	EASY(12, 3, 40),
	NORMAL(30, 5, 90),
	HARD(48, 8, 180),
	EXTREME(70, 10, 240);

	protected int amountCards;

	protected int maxCardPerRow;

	protected int secondsToDo;
}
