package cs3500.pokerpolygons;

import cs3500.pokerpolygons.model.hw02.Poker;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw04.LoosePokerTriangles;
import cs3500.pokerpolygons.model.hw04.PokerRectangles;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import cs3500.pokerpolygons.view.PokerRectanglesTextualView;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;

/**
 * The main class for the Poker Polygons game. This class handles command-line arguments
 * to configure the game variant, hand size, and board dimensions.
 */
public final class PokerPolygonsGame {

  /** run the Poker Polygons game.
   * @param args inputs as command-line arguments
   */
  public static void main(String[] args) {
    // Default values
    String variant = "tri";
    int h = 1;
    int l = 5;
    int w = 5;

    if (args.length > 0) {
      variant = args[0].toLowerCase();
      if (!variant.equals("tri") && !variant.equals("loose") && !variant.equals("rectangle")) {
        throw new IllegalArgumentException("Invalid game variant.");
      }
    } else {
      throw new IllegalArgumentException("No command line arguments.");
    }

    if (args.length > 1) {
      h = Integer.parseInt(args[1]);
    }

    if (args.length > 2) {
      l = Integer.parseInt(args[2]);
    }

    if (variant.equals("rectangle") && args.length > 3) {
      w = Integer.parseInt(args[3]);
    }

    PokerPolygons<Poker> model;
    PokerPolygonsTextualView view;
    switch (variant) {
      case "tri":
        model = new PokerTriangles(l);
        model.startGame(model.getNewDeck(), false, h);
        view = new PokerTrianglesTextualView<>(model);
        break;
      case "loose":
        model = new LoosePokerTriangles(l);
        model.startGame(model.getNewDeck(), false, h);
        view = new PokerTrianglesTextualView<>(model);
        break;
      case "rectangle":
        model = new PokerRectangles(w, l);
        model.startGame(model.getNewDeck(), false, h);
        view = new PokerRectanglesTextualView<>(model);
        break;
      default:
        throw new IllegalArgumentException("Invalid game variant.");
    }

    System.out.println(view.toString());
  }
}