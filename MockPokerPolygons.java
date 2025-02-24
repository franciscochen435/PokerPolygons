package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A mock implementation of the PokerPolygons interface for testing.
 * With an StringBuilder input log.
 */
public class MockPokerPolygons implements PokerPolygons {
  private final StringBuilder log;
  protected boolean isGameOver = false;

  /**
   * create a mock of PokerPolygons with given log.
   *
   * @param log the String builder to log methods
   */
  public MockPokerPolygons(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    log.append(String.format("place (%d, %d, %d)\n", cardIdx, row, col));
  }

  @Override
  public void discardCard(int cardIdx) {
    log.append(String.format("discard (%d)\n", cardIdx));
  }

  @Override
  public void startGame(List deck, boolean shuffle, int handSize) {
    log.append("startGame\n");
  }

  @Override
  public int getWidth() {
    return 5;
  }

  @Override
  public int getHeight() {
    return 5;
  }

  @Override
  public List getNewDeck() {
    return new ArrayList<>();
  }

  @Override
  public Card getCardAt(int row, int col) {
    return null;
  }

  @Override
  public List getHand() {
    return new ArrayList<>();
  }

  @Override
  public int getScore() {
    log.append("Score: \n");
    return 0;
  }

  @Override
  public int getRemainingDeckSize() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    if (isGameOver) {
      log.append("Game Over!\n");
      return true;
    }
    return false;
  }
}
