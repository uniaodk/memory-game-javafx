package com.uniaodk.memory_game.base;

import java.io.InputStream;
import java.util.UUID;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id" })
public class Card {

	private String id;

	private String hashToShuffle;

	private boolean flipped;

	private boolean matched;

	private Image image;

	public Card(String id, InputStream inputStream) {
		this.id = id;
		this.hashToShuffle = UUID.randomUUID().toString() + id;
		this.flipped = false;
		this.matched = false;
		this.image = new Image(inputStream);
	}

	public boolean isSameHash(Card card) {
		return this.hashToShuffle.equals(card.hashToShuffle);
	}
}
