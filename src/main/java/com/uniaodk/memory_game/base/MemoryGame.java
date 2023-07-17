package com.uniaodk.memory_game.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import com.uniaodk.memory_game.Game;
import lombok.Data;

@Data
public class MemoryGame {

	private List<Card> cards;

	private Dificult dificult;

	private int amountCards;

	private int secondsToDo;

	private int maxCardPerRow;

	private Random random;

	public MemoryGame() {
		newGame();
	}

	public void newGame() {
		this.dificult = Dificult.NORMAL;
		this.amountCards = dificult.amountCards;
		this.secondsToDo = dificult.secondsToDo;
		this.maxCardPerRow = dificult.maxCardPerRow;
		this.cards = buildCards();
	}

	public List<Card> buildCards() {
		List<Card> cardsNew = new LinkedList<>();
		File[] cardsFile = getFileCards();
		int indexCard = 0;
		int amountCouples = 0;
		while (cardsNew.size() < amountCards) {
			boolean isUsedAllCards = (indexCard) % cardsFile.length == 0;
			if (isUsedAllCards) {
				indexCard = 0;
				amountCouples++;
			}
			File cardFile = cardsFile[indexCard];
			for (int cardCouple = 0; cardCouple < 2; cardCouple++) {
				cardsNew.add(buildCard(cardFile, amountCouples));
			}
			indexCard++;
		}
		cardsNew.sort((card, nextCard) -> card.getHashToShuffle().compareTo(nextCard.getHashToShuffle()));
		return cardsNew;
	}

	public Card updateCard(Card card, boolean isMatched) {
		card.setFlipped(isMatched);
		card.setMatched(isMatched);
		cards.set(cards.indexOf(card), card);
		return card;
	}

	public Card findCardNotMatchedAndFlipped() {
		return cards.stream().filter(card -> !card.isMatched() && card.isFlipped()).findFirst().orElse(null);
	}

	public boolean hasAlreadyCardNotMatchedAndFlipped() {
		return cards.stream().filter(card -> !card.isMatched() && card.isFlipped()).count() > 1L;
	}

	private Card buildCard(File cardFile, int amountCouples) {
		try {
			String idCard = String.format("%s-%s", cardFile.getName().replace(".png", ""), amountCouples);
			return new Card(idCard, new FileInputStream(cardFile));
		} catch (Exception err) {
			throw new RuntimeException("Can't read the png file!");
		}
	}

	private File[] getFileCards() {
		File[] cardsFile = null;
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			File folder = new File(Game.cardsPath);
			if (!folder.isDirectory()) folder = new File(classLoader.getResource(Game.cardsPath).getPath());
			cardsFile = folder.listFiles(filterOnlyPngFiles());
		} catch (Exception err) {
			File folder = new File(classLoader.getResource(Game.DEFAULT_CARDS_PATH).getPath());
			return folder.listFiles();
		}
		if (Objects.isNull(cardsFile) || cardsFile.length < 2) {
			throw new RuntimeException("The folder must has at leats 2 files .png!");
		}
		return cardsFile;
	}

	private FilenameFilter filterOnlyPngFiles() {
		return new FilenameFilter() {

			public boolean accept(File file, String name) {
				return name.contains(".png");
			};
		};
	}

	@Override
	public String toString() {
		return cards.stream()
				.map(card -> String.format("id: %s - hash: %s - isFlipped: %s - isMatched: %s",
						card.getId(), card.getHashToShuffle(), card.isFlipped(), card.isMatched()))
				.collect(Collectors.joining("\n"));
	}
}
