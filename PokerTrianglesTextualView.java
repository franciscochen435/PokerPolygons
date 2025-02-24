package cs3500.pokerpolygons.view;

import java.io.IOException;
import java.util.List;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Card;

/**
 * A textual view for PokerTriangles.
 * @param <C> the type of card used to play the particular implementation of the game
 */
public class PokerTrianglesTextualView<C extends Card> implements PokerPolygonsTextualView {
  private final PokerPolygons<C> model;

  /**
   * create a textual view for Triangles board for Poker Polygons game.
   * @param model the model of poker triangles game
   */
  public PokerTrianglesTextualView(PokerPolygons<C> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int height = model.getHeight();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col <= row; col++) {
        C card = model.getCardAt(row, col);
        if (card != null) {
          String cardStr = card.toString();
          if (cardStr.length() == 2) {
            sb.append(" ");
            sb.append(cardStr);
          } else {
            sb.append(cardStr);
          }
        } else {
          sb.append(" __");
        }
        if (col < row) {
          sb.append(" ");
        }
      }
      sb.append("\n");
    }
    sb.append("Deck: ").append(model.getRemainingDeckSize()).append("\n");
    sb.append("Hand: ");
    List<C> hand = model.getHand();
    for (int i = 0; i < hand.size(); i++) {
      String cardStr = hand.get(i).toString();
      sb.append(cardStr);
      if (i < hand.size() - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  @Override
  public void render(Appendable out) throws IOException {
    if (out == null) {
      throw new IllegalArgumentException("Output cannot be null.");
    }
    try {
      out.append(this.toString());
    } catch (IOException e) {
      throw new IOException("Failed to render the game state: " + e.getMessage(), e);
    }
  }
}