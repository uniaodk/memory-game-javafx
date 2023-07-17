package com.uniaodk.memory_game.layout;

import java.util.List;
import com.uniaodk.memory_game.Game;
import com.uniaodk.memory_game.base.Card;
import com.uniaodk.memory_game.node.CardNode;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class GameBoardLayout extends GridPane {

	public GameBoardLayout() {
		HBox.setHgrow(this, Priority.ALWAYS);
		this.setAlignment(Pos.CENTER);
		this.getRowConstraints().add(new RowConstraints(USE_COMPUTED_SIZE));
		this.getColumnConstraints().add(new ColumnConstraints(USE_COMPUTED_SIZE));
		add(buildBoardCards(), 0, 0);
	}

	private GridPane buildBoardCards() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		List<Card> cards = Game.memoryGame.getCards();
		int maxCardPerRow = Game.memoryGame.getMaxCardPerRow();
		for (int column = 0; column < maxCardPerRow; column++) {
			gridPane.getColumnConstraints().add(new ColumnConstraints(100));
		}
		int row = 0, column = 0;
		for (int indexCard = 0; indexCard < cards.size(); indexCard++) {
			Card card = cards.get(indexCard);
			gridPane.add(new CardNode(card), column, row);
			column++;
			if (column >= maxCardPerRow) {
				gridPane.getRowConstraints().add(new RowConstraints(100));
				row++;
				column = 0;
			}
		}
		return gridPane;
	}
}
