package com.uniaodk.memory_game.util;

import java.util.Objects;

public abstract class Strings {

	public static boolean isEmpty(String value) {
		return Objects.isNull(value) || value.isEmpty();
	}
}
