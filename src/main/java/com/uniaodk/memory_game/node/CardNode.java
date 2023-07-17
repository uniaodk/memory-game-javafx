package com.uniaodk.memory_game.node;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import com.uniaodk.memory_game.Game;
import com.uniaodk.memory_game.base.Card;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class CardNode extends GridPane {

	public static String STYLES = "-fx-background-radius: 10;-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-cursor: hand;";

	private Card card;

	private Card cardFlippedNotMatched;

	private ImageView imageView;

	public CardNode(Card card) {
		this.card = card;
		this.imageView = new ImageView();
		updateImageView();
		setOnMouseClicked((mouseEvent) -> onClickCard());
		setAlignment(Pos.CENTER);
		setPrefSize(100, 100);
		add(this.imageView, 0, 0);
	}

	private void onClickCard() {
		if (card.isMatched() || Game.memoryGame.hasAlreadyCardNotMatchedAndFlipped()) return;
		cardFlippedNotMatched = Game.memoryGame.findCardNotMatchedAndFlipped();
		this.card.setFlipped(true);
		updateImageView();
		if (Objects.nonNull(cardFlippedNotMatched)) {
			timerToCheckMatch();
		}
	}

	private void timerToCheckMatch() {
		Game.timerToReveal.cancel();
		Game.timerToReveal = new Timer();
		boolean isMatched = cardFlippedNotMatched.equals(card) && !cardFlippedNotMatched.isSameHash(card);
		if (isMatched) updateCards(isMatched);
		Game.timerToReveal.schedule(new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> updateCards(isMatched));
			}
		}, 1000);
	}

	private void updateCards(boolean isMatched) {
		updateCardNode(cardFlippedNotMatched, isMatched);
		updateCardNode(card, isMatched);
		Game.timerToReveal.purge();
	}

	private void updateCardNode(Card card, boolean isMatched) {
		CardNode cardNode = getCardNode(card);
		card.setFlipped(isMatched);
		card.setMatched(isMatched);
		cardNode.card = card;
		cardNode.updateImageView();
	}

	private List<CardNode> listCardNodes() {
		GridPane parent = (GridPane) getParent();
		ObservableList<Node> children = parent.getChildren();
		return Arrays.asList(children.toArray(new Node[children.size()]))
				.stream()
				.filter(node -> node instanceof CardNode)
				.map(node -> (CardNode) node)
				.toList();
	}

	private CardNode getCardNode(Card card) {
		if (this.card.isSameHash(card)) return this;
		for (CardNode cardNode : listCardNodes()) {
			if (cardNode.card.isSameHash(card)) return cardNode;
		}
		return null;
	}

	public void updateImageView() {
		this.imageView.setImage(card.isFlipped() ? this.card.getImage() : new Image(Game.UPSIDEDOWN_CARD));
		this.imageView.setPreserveRatio(true);
		this.imageView.setFitWidth(80);
		this.imageView.setFitHeight(80);
		this.setStyle(String.format("%s;-fx-background-color: %s", STYLES, card.isFlipped() ? "white" : "#e6f0f4"));
	}
}
