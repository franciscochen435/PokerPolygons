package cs3500.pokerpolygons.model.hw02;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import cs3500.pokerpolygons.model.hw04.PokerAbstract;

/**
 * Behaviors of a game of poker polygons, assuming the shape can fit inside some triangle polygon.
 */
public class PokerTriangles extends PokerAbstract {
  private final int side;

  /**
   * create a triangle board for poker polygons game.
   * with one argument.
   * @param side the length of triangle board
   * @throws IllegalArgumentException if the side length is shorter than 5
   */
  public PokerTriangles(int side) {
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
  public PokerTriangles(int side, Random rand) {
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

    if (!(other instanceof PokerTriangles)) {
      return false;
    }

    PokerTriangles otherPoker = (PokerTriangles) other;
    return side == otherPoker.side;
  }

  @Override
  public int hashCode() {
    return Objects.hash(side);
  }
}