package com.uniaodk.memory_game.base;

import java.io.InputStream;
import java.util.UUID;
import javafx.scene.image.Image;
import lombok.Data;

@Data
public class Card {

	private String id;

	private String hashToShuffle;

	private boolean flipped;

	private Image image;

	public Card(String id, InputStream inputStream) {
		this.id = id;
		this.hashToShuffle = UUID.randomUUID().toString();
		this.flipped = false;
		this.image = new Image(inputStream);
	}
}
