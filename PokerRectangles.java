package cs3500.pokerpolygons.model.hw04;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import cs3500.pokerpolygons.model.hw02.Poker;

/**
 * Behaviors of a game of poker polygons, assuming the shape can fit inside some rectangle polygon.
 */
public class PokerRectangles extends PokerAbstract {
  private final int height;
  private final int width;

  /**
   * create a rectangle board for poker polygons game.
   * with two arguments.
   * @param height the height of rectangle board
   * @param width the width of rectangle board
   * @throws IllegalArgumentException if the width or height is shorter than 5
   */
  public PokerRectangles(int height, int width) {
    this(height, width, new Random());
  }

  /**
   * create a rectangle board for poker polygons game.
   * with three arguments.
   * @param height the height of rectangle board
   * @param width the width of height board
   * @param rand the random object
   * @throws IllegalArgumentException if the side length is shorter than 5
   * @throws IllegalArgumentException if the width and height are the same
   */
  public PokerRectangles(int height, int width, Random rand) {
    super(rand);
    if (width < 5 || height < 5) {
      throw new IllegalArgumentException("Width and height must be at least 5");
    }

    this.height = height;
    this.width = width;
  }

  @Override
  protected boolean isPositionValid(int row, int col) {
    return row < this.height && col < this.width && col >= 0 && row >= 0;
  }

  @Override
  protected int getCardsNeed() {
    return this.width * this.height;
  }

  @Override
  protected void initialBoard() {
    board = new Poker[height][width];
  }

  @Override
  protected int getEmptySpace() {
    int empty = 0;
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (board[i][j] == null) {
          empty++;
        }
      }
    }
    return empty;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  protected int getScoreHelper() {
    int totalScore = 0;
    // check if there is a completed row longer than 5
    for (int i = 0; i < height; i++) {
      List<Poker> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        if (board[i][j] != null) {
          row.add(board[i][j]);
        }
      }
      if (row.size() >= 5) {
        totalScore += evaluate(row);
      }
    }
    // check if there is a completed column longer than 5
    for (int j = 0; j < width; j++) {
      List<Poker> col = new ArrayList<>();
      for (int i = 0; i < height; i++) {
        if (board[i][j] != null) {
          col.add(board[i][j]);
        }
      }
      if (col.size() >= 5) {
        totalScore += evaluate(col);
      }
    }
    return totalScore;
  }

  @Override
  protected boolean hasEmpty() {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
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

    if (!(other instanceof PokerRectangles)) {
      return false;
    }

    PokerRectangles otherPoker = (PokerRectangles) other;
    return width == otherPoker.width && height == otherPoker.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height);
  }
}