package cs3500.pokerpolygons.model.hw04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import cs3500.pokerpolygons.model.hw02.Poker;


/**
 * Behaviors of a game of poker polygons, assuming the shape can fit inside some triangle polygon.
 */
public class LoosePokerTriangles extends PokerAbstract {
  private final int side;

  /**
   * create a triangle board for poker polygons game.
   * with one argument.
   * @param side the length of triangle board
   * @throws IllegalArgumentException if the side length is shorter than 5
   */
  public LoosePokerTriangles(int side) {
    this(side, new Random());
  }

  /**
   * create a triangle board for poker polygons game.
   * with two argument.
   * @param side the length of triangle board
   * @param rand the random object
   * @throws IllegalArgumentException if the side length is shorter than 5
   * @throws IllegalArgumentException if the random object is null
   */
  public LoosePokerTriangles(int side, Random rand) {
    super(rand);
    if (side < 5) {
      throw new IllegalArgumentException("Side must be greater than or equal to 5");
    }
    this.side = side;
  }

  @Override
  protected boolean isPositionValid(int row, int col) {
    return row < this.side && col <= row && col >= 0;
  }

  @Override
  protected int getEmptySpace() {
    int empty = 0;
    for (int i = 0; i < this.side; i++) {
      for (int j = 0; j <= i; j++) {
        if (board[i][j] == null) {
          empty++;
        }
      }
    }
    return empty;
  }

  @Override
  protected int getCardsNeed() {
    return this.side * (this.side + 1) / 2;
  }

  @Override
  protected void initialBoard() {
    board = new Poker[side][side];
  }

  @Override
  public int getWidth() {
    return side;
  }

  @Override
  public int getHeight() {
    return side;
  }

  @Override
  protected int getScoreHelper() {
    int totalScore = 0;
    // check if there is a completed row longer than 5
    for (int i = 0; i < side; i++) {
      List<Poker> row = new ArrayList<>();
      for (int j = 0; j <= i; j++) {
        if (board[i][j] != null) {
          row.add(board[i][j]);
        }
      }
      if (row.size() >= 5) {
        totalScore += evaluate(row);
      }
    }
    // check if there is a completed column longer than 5
    for (int j = 0; j < side; j++) {
      List<Poker> col = new ArrayList<>();
      for (int i = j; i < side; i++) {
        if (board[i][j] != null) {
          col.add(board[i][j]);
        }
      }
      if (col.size() >= 5) {
        totalScore += evaluate(col);
      }
    }
    // check if the outer diagonal completed and longer than 5
    List<Poker> diagonal = new ArrayList<>();
    for (int i = 0; i < side; i++) {
      if (board[i][i] != null) {
        diagonal.add(board[i][i]);
      }
    }
    if (diagonal.size() >= 5) {
      totalScore += evaluate(diagonal);
    }
    return totalScore;
  }

  // helper method: check if the hand has at least 5 cards with the same color
  @Override
  protected boolean isFlush(List<Poker> cards) {
    HashMap<String, Integer> suitCount = new HashMap<>();
    for (Poker card : cards) {
      String color = card.getColor();
      if (suitCount.containsKey(color)) {
        suitCount.put(color, suitCount.get(color) + 1);
      } else {
        suitCount.put(color, 1);
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
  @Override
  protected boolean isStraight(List<Poker> cards) {
    List<Integer> ranks = new ArrayList<>();
    for (Poker card : cards) {
      ranks.add(card.getRank());
    }

    Collections.sort(ranks);
    if (isStraightHelper(ranks)) {
      return true;
    }

    if (ranks.contains(1)) {
      List<Integer> altRanks = new ArrayList<>(ranks);
      altRanks.remove(Integer.valueOf(1));
      while (altRanks.size() < ranks.size()) {
        altRanks.add(14);
      }
      Collections.sort(altRanks);
      return isStraightHelper(altRanks);
    }
    return false;
  }

  /**
   * Checks if a sorted list of ranks forms a flexible straight,
   * where consecutive numbers can differ by 1 or 2.
   */
  private boolean isStraightHelper(List<Integer> ranks) {
    for (int i = 1; i < ranks.size(); i++) {
      int diff = ranks.get(i) - ranks.get(i - 1);
      if (diff != 1 && diff != 2) {
        return false;
      }
    }
    return true;
  }


  @Override
  protected boolean hasEmpty() {
    for (int i = 0; i < this.side; i++) {
      for (int j = 0; j <= i; j++) {
        if (board[i][j] == null) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof LoosePokerTriangles)) {
      return false;
    }

    LoosePokerTriangles otherPoker = (LoosePokerTriangles) other;
    return side == otherPoker.side;
  }

  @Override
  public int hashCode() {
    return Objects.hash(side);
  }
}