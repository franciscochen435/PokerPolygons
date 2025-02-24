package cs3500.pokerpolygons.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cs3500.pokerpolygons.model.hw02.Poker;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;

/**
 * Abstract behaviors of a game of poker polygons.
 * assuming the shape can fit inside some triangle or rectangle polygon.
 */
public abstract class PokerAbstract implements PokerPolygons<Poker> {
  private final Random rand;
  protected Poker[][] board;
  private List<Poker> deck;
  private List<Poker> hand;
  private boolean isGameStarted;

  /**
   * create a board for poker polygons game.
   * with zero argument.
   */
  public PokerAbstract() {
    this(new Random());
  }

  /**
   * create a board for poker polygons game.
   * with one argument.
   * @param rand the random object
   * @throws IllegalArgumentException if the random object is null
   */
  public PokerAbstract(Random rand) {
    if (rand == null) {
      throw new IllegalArgumentException("Random number cannot be null");
    }
    this.rand = rand;
    this.isGameStarted = false;
  }

  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    if (!isPositionValid(row, col)) {
      throw new IllegalArgumentException("The position is invalid on the board");
    }
    if (board[row][col] != null) {
      throw new IllegalStateException("The position already has a card");
    }
    if (cardIdx >= this.hand.size() || cardIdx < 0) {
      throw new IllegalArgumentException("The card index out of bounds");
    }

    board[row][col] = hand.remove(cardIdx);
    if (!deck.isEmpty()) {
      hand.add(deck.remove(0));
    }
  }

  /**
   * check if the position is valid on the board (in the range).
   * @param row the row of the card
   * @param col the column of the card
   * @return true when the position is valid, otherwise, return false
   */
  protected abstract boolean isPositionValid(int row, int col);

  /**
   * Discards the specified card from the hand, but only if the player can also draw a new card.
   * and there are enough cards between the remaining deck and hand to fill the remaining empty.
   * positions on the board.
   * In the class, I design one more check for when cards on deck and hand.
   * cannot fill the empty positions on the board, it will return an error.
   * @param cardIdx index of the card in hand to discard (0-index based)
   * @throws IllegalStateException if the game has been started
   * @throws IllegalArgumentException if the card index out of the bounds
   * @throws IllegalStateException if the empty positions on the board is more than
   *                               cards of deck and hand
   */
  @Override
  public void discardCard(int cardIdx) {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    if (cardIdx >= this.hand.size() || cardIdx < 0) {
      throw new IllegalArgumentException("The card index out of bounds");
    }

    int empty = getEmptySpace();
    if (empty >= this.hand.size() + this.deck.size()) {
      throw new IllegalStateException("There are not enough cards in the hand and deck");
    }
    hand.remove(cardIdx);
    if (!deck.isEmpty()) {
      hand.add(deck.get(0));
      deck.remove(0);
    }
  }

  /**
   * helper method to get the rest empty space on the board.
   * which is the helper method to check if user can discard the card
   * @return the number of empty space on the board
   */
  protected abstract int getEmptySpace();

  @Override
  public void startGame(List<Poker> deck, boolean shuffle, int handSize) {
    if (isGameStarted) {
      throw new IllegalStateException("Game has already been started");
    }
    if (deck == null || deck.contains(null)) {
      throw new IllegalArgumentException("Deck cannot be empty or contains null elements");
    }
    if (handSize <= 0) {
      throw new IllegalArgumentException("Hand size must be greater than 0");
    }
    if (deck.size() < handSize) {
      throw new IllegalArgumentException("Deck size must be greater than " + handSize);
    }

    int cardsNeeded = handSize + getCardsNeed();
    if (deck.size() < cardsNeeded) {
      throw new IllegalArgumentException(
              "Deck does not contain enough cards to fill the board and hand");
    }

    this.deck = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(this.deck, this.rand);
    }

    this.hand = new ArrayList<>(handSize);
    for (int i = 0; i < handSize; i++) {
      this.hand.add(this.deck.get(0));
      this.deck.remove(0);
    }

    initialBoard();
    this.isGameStarted = true;
  }

  /**
   * helper method to get the number of cards the board can contain.
   * @return the number of cards the board can contain
   */
  protected abstract int getCardsNeed();

  /**
   * helper method to create the initial board.
   */
  protected abstract void initialBoard();

  @Override
  public abstract int getWidth();

  @Override
  public abstract int getHeight();

  @Override
  public Poker getCardAt(int row, int col) {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    if (!isPositionValid(row, col)) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    if (board[row][col] == null) {
      return null;
    }
    return board[row][col];
  }

  @Override
  public List<Poker> getNewDeck() {
    List<Poker> allPoker = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♢", "♡", "♠");
    for (int i = 1; i < 14; i++) {
      for (String suit : allSuits) {
        allPoker.add(new Poker(i, suit));
      }
    }
    return allPoker;
  }

  @Override
  public List<Poker> getHand() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return new ArrayList<>(hand);
  }

  @Override
  public int getScore() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return getScoreHelper();
  }

  /**
   * helper method to get the score.
   * @return the score of the game
   */
  protected abstract int getScoreHelper();

  /**
   * Helper function to evaluate which collection the board has.
   * @param cards the existing card list on the same row, col, or outer diagonal
   *              which length is longer than 5
   * @return a score of one specific card combination
   */
  protected int evaluate(List<Poker> cards) {
    if (isStraightFlush(cards)) {
      return 75;
    } else if (isFourOfAKind(cards)) {
      return 50;
    } else if (isFullHouse(cards)) {
      return 25;
    } else if (isFlush(cards)) {
      return 20;
    } else if (isStraight(cards)) {
      return 15;
    } else if (isThreeOfAKind(cards)) {
      return 10;
    } else if (isTwoPair(cards)) {
      return 5;
    } else if (isPair(cards)) {
      return 2;
    } else {
      return 0;
    }
  }

  // helper method: check if the hand has 5 cards of the same suit and those 5 cards,
  // if sorted, form a run: a sequence of ranks
  // that can be ordered such that each rank increases by 1 in numerical value.
  protected boolean isStraightFlush(List<Poker> cards) {
    return isFlush(cards) && isStraight(cards);
  }

  // helper abstract method: check if the hand has a combination of cards of the same rank
  // The number of the same rank cards can be modified depending on user's requirement.
  protected boolean hasKind(List<Poker> cards, int index) {
    HashMap<Integer, Integer> count = new HashMap<>();
    for (Poker card : cards) {
      int rank = card.getRank();
      if (count.containsKey(rank)) {
        count.put(rank, count.get(rank) + 1);
      } else {
        count.put(rank, 1);
      }
    }
    return count.containsValue(index);
  }

  // helper method: finding at least 4 cards have the same rank.
  protected boolean isFourOfAKind(List<Poker> cards) {
    return hasKind(cards, 4);
  }

  // helper method: check the hand has at least 3 cards of the same rank
  // and 2 cards of a different single rank.
  protected boolean isFullHouse(List<Poker> cards) {
    HashMap<Integer, Integer> rankCount = new HashMap<>();
    int threeCount = 0;
    int pairCount = 0;
    for (Poker card : cards) {
      int rank = card.getRank();
      if (rankCount.containsKey(rank)) {
        rankCount.put(rank, rankCount.get(rank) + 1);
      } else {
        rankCount.put(rank, 1);
      }
    }
    for (int count : rankCount.values()) {
      if (count >= 3) {
        threeCount++;
      } else if (count == 2) {
        pairCount++;
      }
    }
    return (threeCount >= 1 && pairCount >= 1) || threeCount >= 2;
  }

  // helper method: check if the hand has at least 5 cards with the same rank
  protected boolean isFlush(List<Poker> cards) {
    HashMap<String, Integer> suitCount = new HashMap<>();
    for (Poker card : cards) {
      String suit = card.getSuit();
      if (suitCount.containsKey(suit)) {
        suitCount.put(suit, suitCount.get(suit) + 1);
      } else {
        suitCount.put(suit, 1);
      }
    }
    for (int count : suitCount.values()) {
      if (count >= 5) {
        return true;
      }
    }
    return false;
  }

  // helper method: check if the hand has at least 5 cards and those cards,
  // if sorted, form a run: a sequence of ranks
  // that can be ordered such that the numerical value of each card’s rank increases by 1
  protected boolean isStraight(List<Poker> cards) {
    List<Integer> ranks = new ArrayList<>();
    for (Poker card : cards) {
      ranks.add(card.getRank());
    }
    Collections.sort(ranks);

    if (ranks.contains(1) && ranks.contains(10) && ranks.contains(11)
            && ranks.contains(12) && ranks.contains(13)) {
      return true;
    }

    for (int i = 1; i < ranks.size(); i++) {
      if (ranks.get(i) != ranks.get(i - 1) + 1) {
        return false;
      }
    }
    return true;
  }

  // helper method: while finding at least 3 cards have the same rank.
  protected boolean isThreeOfAKind(List<Poker> cards) {
    return hasKind(cards, 3);
  }

  // helper method: check if the hand has at least two different rank pairs.
  protected boolean isTwoPair(List<Poker> cards) {
    int pairCount = 0;
    HashMap<Integer, Integer> count = new HashMap<>();
    for (Poker card : cards) {
      int rank = card.getRank();
      if (count.containsKey(rank)) {
        count.put(rank, count.get(rank) + 1);
      } else {
        count.put(rank, 1);
      }
    }
    for (int value : count.values()) {
      if (value == 2) {
        pairCount++;
      }
    }
    return pairCount >= 2;
  }

  // helper method: while finding at least 2 cards have the same rank.
  protected boolean isPair(List<Poker> cards) {
    return hasKind(cards, 2);
  }

  @Override
  public int getRemainingDeckSize() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return deck.size();
  }

  @Override
  public boolean isGameOver() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return !hasEmpty();
  }

  /**
   * helper method to check if the board has empty position.
   * @return true if there is empty space, otherwise, return false
   */
  protected abstract boolean hasEmpty();

  @Override
  public abstract boolean equals(Object other);

  @Override
  public abstract int hashCode();
}