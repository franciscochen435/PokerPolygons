package cs3500.pokerpolygons;

import cs3500.pokerpolygons.controller.PokerPolygonsTextualController;
import cs3500.pokerpolygons.controller.MockPokerPolygons;
import cs3500.pokerpolygons.model.hw02.Poker;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * test to determine if the controller transmits correct information.
 * and if the controller sends the correct inputs to the PokerPolygons.
 */
public class TestPokerPolygonsController {
  private StringBuilder log;
  private MockPokerPolygons mock;
  private PokerTrianglesTextualView view;
  private PokerPolygonsTextualController<Poker> controller;

  @Before
  public void setUp() {
    log = new StringBuilder();
    mock = new MockPokerPolygons(log);
    view = new PokerTrianglesTextualView(mock);
  }

  @Test
  public void testControllerConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerPolygonsTextualController<>(null, log); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerPolygonsTextualController<>(null, null); } );
    StringReader reader = new StringReader("q\n");
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerPolygonsTextualController<>(reader, null); } );
  }

  @Test
  public void testValidQuirCommand() {
    StringReader reader = new StringReader("q\n");
    controller = new PokerPolygonsTextualController<>(reader, log);
    controller.playGame(mock, view, List.of(), false, 2);

    String expected =
            "startGame\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Enter a command: \n" +
                    "Game quit!\n" +
                    "State of game when quit:\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n";
    assertEquals(expected, log.toString());

  }

  @Test
  public void testValidPlaceCommand() {
    StringReader reader = new StringReader("place 1 1 1\n");
    controller = new PokerPolygonsTextualController<>(reader, log);
    controller.playGame(mock, view, List.of(), false, 2);

    String expected =
            "startGame\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Enter a command: \n" +
                    "place (0, 0, 0)\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Score: \n" +
                    "Game over. Score: 0\n";

    assertEquals(expected, log.toString());
  }

  @Test
  public void testInvalidPlaceCommand() {
    StringReader reader = new StringReader("place -1 -100 -1\n");
    controller = new PokerPolygonsTextualController<>(reader, log);
    controller.playGame(mock, view, List.of(), false, 2);

    String expected =
            "startGame\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Enter a command: \n" +
                    "Invalid input. Play again. Number must be positive.\n" +
                    "Invalid input. Play again. Number must be positive.\n" +
                    "Invalid input. Play again. Number must be positive.\n" +
                    "Invalid move. Play again. No available input.\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Score: \n" +
                    "Game over. Score: 0\n";

    assertEquals(expected, log.toString());
  }

  @Test
  public void testValidDiscardCommand() {
    StringReader reader = new StringReader("discard 1\n");
    controller = new PokerPolygonsTextualController<>(reader, log);

    controller.playGame(mock, view, List.of(), false, 2);
    String expected =
            "startGame\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Enter a command: \n" +
                    "discard (0)\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Score: \n" +
                    "Game over. Score: 0\n";

    assertEquals(expected, log.toString());
  }

  @Test
  public void testInvalidDiscardCommand() {
    StringReader reader = new StringReader("discard -1\n");
    controller = new PokerPolygonsTextualController<>(reader, log);
    controller.playGame(mock, view, List.of(), false, 2);

    String expected =
            "startGame\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Enter a command: \n" +
                    "Invalid input. Play again. Number must be positive.\n" +
                    "Invalid move. Play again. No available input.\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Score: \n" +
                    "Game over. Score: 0\n";

    assertEquals(expected, log.toString());
  }

  @Test
  public void testInvalidCommand() {
    StringReader reader = new StringReader("hello\n");
    controller = new PokerPolygonsTextualController<>(reader, log);
    controller.playGame(mock, view, List.of(), false, 2);

    String expected =
            "startGame\n" +
                    " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: \n" +
                    "Score: \n" +
                    "Score: 0\n" +
                    "Enter a command: \n" +
                    "Invalid move. Play again. Invalid command.\n" +
                    "Score: \n" +
                    "Game over. Score: 0\n";

    assertEquals(expected, log.toString());
  }

  @Test
  public void testRender() throws IOException {
    StringBuilder output = new StringBuilder();
    view.render(output);

    String expected =
            " __\n" +
                    " __  __\n" +
                    " __  __  __\n" +
                    " __  __  __  __\n" +
                    " __  __  __  __  __\n" +
                    "Deck: 0\n" +
                    "Hand: ";

    assertEquals(expected, output.toString());
  }

}