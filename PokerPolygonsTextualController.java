package cs3500.pokerpolygons.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;

/**
 * This class represents the controller of an interactive PokerPolygons game.
 * This controller offers a simple text interface in which the user can.
 * type instructions to manipulate a spreadsheet.
 * @param <C> the type of card used to play the particular implementation of the game
 */
public class PokerPolygonsTextualController<C extends Card> implements PokerPolygonsController {
  private final Readable rd;
  private final Appendable ap;

  /**
   * Create a controller to work with.
   * readable (to take inputs) and appendable (to transmit output).
   * @param rd the Readable object for inputs
   * @param ap the Appendable objects to transmit any output
   * @throws IllegalArgumentException if the readable or appendable is null
   */
  public PokerPolygonsTextualController(Readable rd, Appendable ap)
          throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable or appendable is null");
    }
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public <C extends Card> void playGame(PokerPolygons<C> model, PokerPolygonsTextualView view,
                                        List<C> deck, boolean shuffle, int handSize) {
    if (model == null || view == null || deck == null || handSize < 0) {
      throw new IllegalArgumentException("The input is invalid");
    }

    // start the game firstly and transmit the initial game state
    try {
      model.startGame(deck, shuffle, handSize);
      writeMessage(view.toString());
      writeMessage("Score: " + model.getScore());
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new IllegalStateException("Fail to start game: " + e.getMessage());
    }

    Scanner scan = new Scanner(this.rd);

    // after start the game, there is a loop
    // until quit or end game
    while (!model.isGameOver() && scan.hasNext()) {
      writeMessage("Enter a command: ");
      String command = scan.next();

      if (command.equals("q") || command.equals("Q")) {
        writeMessage("Game quit!");
        writeMessage("State of game when quit:");
        writeMessage(view.toString());
        writeMessage("Score: " + model.getScore());
        return;
      }

      if (command.equals("place") || command.equals("discard")) {
        try {
          int cardIdx = getValidInput(scan);
          if (command.equals("place")) {
            int row = getValidInput(scan);
            int col = getValidInput(scan);
            model.placeCardInPosition(cardIdx - 1, row - 1, col - 1);
          } else {
            model.discardCard(cardIdx - 1);
          }

          writeMessage(view.toString());
          writeMessage("Score: " + model.getScore());
        } catch (IllegalArgumentException | IllegalStateException e) {
          writeMessage("Invalid move. Play again. " + e.getMessage());
          writeMessage(view.toString());
          writeMessage("Score: " + model.getScore());

        }
      } else {
        writeMessage("Invalid move. Play again. Invalid command.");
      }
    }
    // Game Over
    writeMessage("Game over. Score: " + model.getScore());
  }

  /**
   * Reads valid numerical input or handles quitting.
   * Write some specific messages according to specific cases
   * @throws IllegalStateException if the input is unavailable
   */
  private int getValidInput(Scanner scan) {
    while (true) {
      if (!scan.hasNext()) {
        throw new IllegalStateException("No available input.");
      }
      if (scan.hasNextInt()) {
        int number = scan.nextInt();
        if (number > 0) {
          return number;
        }
        writeMessage("Invalid input. Play again. Number must be positive.");
        continue;
      }
      String input = scan.next();
      if (input.equals("q") || input.equals("Q")) {
        throw new IllegalStateException("Quit the game.");
      }
      writeMessage("Invalid input. Play again.");
    }
  }

  /**
   * Appends the message to appendable.
   * @throws IllegalStateException fail to append
   */
  private void writeMessage(String message) {
    try {
      ap.append(message).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error: " + e.getMessage());
    }
  }
}