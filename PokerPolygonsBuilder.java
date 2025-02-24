package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Poker;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;

/**
 * Builder class for constructing instances of PokerPolygons with customizable properties.
 */
public class PokerPolygonsBuilder {

  /**
   * This enumerated type represents the different types of poker polygons game
   * along with a string description associated with each of them.
   */
  public enum GameType { TRI, RECT, LOOSE }

  private GameType type;
  private int sideLength;
  private int width;
  private int height;

  /**
   * Initializes the builder with default values.
   * Default game type is TRI with a side length of 5.
   */
  public PokerPolygonsBuilder() {
    this.type = GameType.TRI;
    this.sideLength = 5;
    this.width = 5;
    this.height = 5;
  }

  /**
   * Sets the type of game.
   * @param type the type of game (TRI, RECT, or LOOSE)
   * @return this builder instance
   */
  public PokerPolygonsBuilder setType(GameType type) {
    this.type = type;
    return this;
  }

  /**
   * Sets the side length for a triangular game.
   * @param sideLength the side length
   * @return this builder instance
   */
  public PokerPolygonsBuilder setSideLength(int sideLength) {
    this.sideLength = sideLength;
    return this;
  }

  /**
   * Sets the width for a rectangular game.
   * @param width the width of the rectangle
   * @return this builder instance
   */
  public PokerPolygonsBuilder setWidth(int width) {
    this.width = width;
    return this;
  }

  /**
   * Sets the height for a rectangular game.
   * @param height the height of the rectangle
   * @return this builder instance
   */
  public PokerPolygonsBuilder setHeight(int height) {
    this.height = height;
    return this;
  }

  /**
   * Builds an instance of PokerPolygons based on the set parameters.
   * @return an instance of a PokerPolygons game
   */
  public PokerPolygons<Poker> build() {
    switch (type) {
      case TRI:
        return new PokerTriangles(sideLength);
      case RECT:
        return new PokerRectangles(height, width);
      case LOOSE:
        return new LoosePokerTriangles(sideLength);
      default:
        throw new IllegalStateException("Invalid game type");
    }
  }
}

