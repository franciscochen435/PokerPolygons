package cs3500.pokerpolygons.view;

import java.io.IOException;
import java.util.List;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Card;

/**
 * A textual view for PokerRectangles.
 * @param <C> the type of card used to play the particular implementation of the game
 */
public class PokerRectanglesTextualView<C extends Card> implements PokerPolygonsTextualView {
  private final PokerPolygons<C> model;

  /**
   * create a textual view for Rectangles board for Poker Polygons game.
   * @param model the model of poker rectangle game
   */
  public PokerRectanglesTextualView(PokerPolygons<C> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  /**
   * Output a textural form of the poker polygons game.
   * @return a string which represents the board, deck(the remain card numbers of the deck),
   *         and cards on the hand.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int width = model.getWidth();
    int height = model.getHeight();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
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
        if (col < width - 1) {
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
