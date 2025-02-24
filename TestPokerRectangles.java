package cs3500.pokerpolygons;

import cs3500.pokerpolygons.model.hw02.Poker;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw04.PokerAbstract;
import cs3500.pokerpolygons.model.hw04.PokerRectangles;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import cs3500.pokerpolygons.view.PokerRectanglesTextualView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test constructors PokerRectangles and PokerRectanglesTextualView .
 * And any methods defined in all classes.
 */
public class TestPokerRectangles {
  private PokerPolygons<Poker> pp1;
  private PokerPolygons<Poker> pp2;
  private PokerAbstract startPP;
  private PokerPolygonsTextualView view;

  @Before
  public void setUp() {
    this.pp1 = new PokerRectangles(5, 6);
    this.pp2 = new PokerRectangles(7,5, new Random());
    this.startPP = new PokerRectangles(6, 5);
    List<Poker> allPokers = this.startPP.getNewDeck();
    this.startPP.startGame(allPokers, false, 5);
    this.view = new PokerRectanglesTextualView<>(this.startPP);
  }

  @Test
  public void testInvalidPokerRectanglesConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerRectangles(5, -1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerRectangles(3, 5); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerRectangles(5, 6, null); } );
  }

  @Test
  public void testPokerRectanglesStartGame() {
    List<Poker> allPokers = this.startPP.getNewDeck();
    assertThrows(IllegalStateException.class, () -> {
      this.startPP.startGame(allPokers, false, 5); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.pp1.startGame(null, false, 5); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.pp2.startGame(allPokers, false, 0); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.pp1.startGame(allPokers, false, -1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.pp1.startGame(allPokers, false, 55); } );
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.getScore(); } );
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.getRemainingDeckSize(); } );

    this.pp1.startGame(allPokers, false, 5);
    assertEquals(0, this.pp1.getScore());
    assertEquals(47, this.pp1.getRemainingDeckSize());
    this.pp2.startGame(allPokers, true, 10);
    assertEquals(0, this.pp2.getScore());
    assertEquals(42, this.pp2.getRemainingDeckSize());
  }

  @Test
  public void testPokerRectanglesPlaceCard() {
    List<Poker> allPokers = this.startPP.getNewDeck();
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.placeCardInPosition(1, 1, 1); } );
    this.startPP.placeCardInPosition(0, 1, 1);
    assertEquals(allPokers.get(0), this.startPP.getCardAt(1, 1));
    assertEquals(46, this.startPP.getRemainingDeckSize());
    this.startPP.placeCardInPosition(0, 2, 2);
    assertEquals(allPokers.get(1), this.startPP.getCardAt(2, 2));

    assertThrows(IllegalStateException.class, () -> {
      this.startPP.placeCardInPosition(2, 1, 1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.placeCardInPosition(6, 2, 1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.placeCardInPosition(5, 3, 3); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.placeCardInPosition(-1, 4, 4); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.placeCardInPosition(0, -1, 2); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.placeCardInPosition(0, 6, 6); } );
  }

  @Test
  public void testPokerRectangleDiscardCards() {
    this.startPP.discardCard(0);
    assertEquals("2♢", this.startPP.getHand().get(4).toString());
    assertEquals("A♢", this.startPP.getHand().get(0).toString());
    this.startPP.discardCard(4);
    assertEquals("2♡", this.startPP.getHand().get(4).toString());
    assertEquals("A♢", this.startPP.getHand().get(0).toString());
  }

  @Test
  public void testPokerRectangleGetSide() {
    assertEquals(5, this.pp1.getHeight());
    assertEquals(5, this.pp2.getWidth());
    assertEquals(6, this.startPP.getHeight());
  }

  @Test
  public void testPokerRectangleGetDeck() {
    List<Poker> allPokers = this.startPP.getNewDeck();
    assertEquals(52, allPokers.size());
    assertEquals(47, this.startPP.getRemainingDeckSize());
  }

  @Test
  public void testPokerRectangleGetCardAt() {
    assertEquals(null, this.startPP.getCardAt(1, 1));
    this.startPP.placeCardInPosition(1, 1, 1);
    assertEquals("A♢", this.startPP.getCardAt(1, 1).toString());
    this.startPP.placeCardInPosition(0, 0, 0);
    assertEquals("A♣", this.startPP.getCardAt(0, 0).toString());

    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.getCardAt(6, 6); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.getCardAt(5, 6); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.getCardAt(-1, 2); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.getCardAt(2, -2); } );
  }

  @Test
  public void testPokerRectangleGetHand() {
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.getHand(); } );

    assertEquals("A♣", this.startPP.getHand().get(0).toString());
    assertEquals("A♢", this.startPP.getHand().get(1).toString());
    assertEquals("A♡", this.startPP.getHand().get(2).toString());
    assertEquals("A♠", this.startPP.getHand().get(3).toString());
    assertEquals("2♣", this.startPP.getHand().get(4).toString());
  }

  @Test
  public void testPokerRectanglesGetScore() {
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.getScore();
    });
    assertEquals(0, this.startPP.getScore());
  }

  @Test
  public void testStraightFlush() {
    List<Poker> deck = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♢", "♡", "♠");
    for (String suit : allSuits) {
      for (int rank = 1; rank < 13; rank++) {
        deck.add(new Poker(rank, suit));
      }
    }
    this.pp1.startGame(deck, false, 5);
    assertEquals(0, this.pp1.getScore());

    // case: A♣, 2♣, 3♣, 4♣, 5♣ in first col
    this.pp1.placeCardInPosition(0, 0, 0);
    this.pp1.placeCardInPosition(0, 1, 0);
    this.pp1.placeCardInPosition(0, 2, 0);
    this.pp1.placeCardInPosition(0, 3, 0);
    this.pp1.placeCardInPosition(0, 4, 0);
    assertEquals(75, this.pp1.getScore());
  }

  @Test
  public void testFourOfAKind() {
    // case: A♣, A♢, A♡, A♠, 2♣, 2♢ in first col
    this.startPP.placeCardInPosition(0, 0, 0);
    this.startPP.placeCardInPosition(0, 1,0);
    this.startPP.placeCardInPosition(0, 2, 0);
    this.startPP.placeCardInPosition(0, 3, 0);
    this.startPP.placeCardInPosition(0, 4, 0);
    this.startPP.placeCardInPosition(0, 5, 0);
    assertEquals(50, this.startPP.getScore());
  }

  @Test
  public void testFullHouse() {
    // case: A♣, A♢, A♡, 2♣, 2♢ in last second row
    this.startPP.placeCardInPosition(0, 4, 4);
    this.startPP.placeCardInPosition(0, 4, 2);
    this.startPP.placeCardInPosition(0, 4, 3);
    this.startPP.placeCardInPosition(1, 4, 1);
    this.startPP.placeCardInPosition(1, 4, 0);
    assertEquals(25, this.startPP.getScore());
  }

  @Test
  public void testFlush() {
    List<Poker> deck = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♣", "♣", "♣");
    for (String suit : allSuits) {
      for (int rank = 1; rank < 13; rank++) {
        deck.add(new Poker(rank, suit));
      }
    }
    this.pp1.startGame(deck, false, 1);
    assertEquals(0, this.pp1.getScore());
    // case: A♣, 2♣, 3♣, 4♣, A♣
    this.pp1.placeCardInPosition(0, 0, 0);
    this.pp1.placeCardInPosition(0, 0, 1);
    this.pp1.placeCardInPosition(0, 0, 2);
    this.pp1.placeCardInPosition(0, 0, 3);
    this.pp1.discardCard(0);
    this.pp1.discardCard(0);
    this.pp1.discardCard(0);
    this.pp1.placeCardInPosition(0, 0, 4);
    this.pp1.placeCardInPosition(0, 0, 5);
    assertEquals(20, this.pp1.getScore());
  }

  @Test
  public void testStraight() {
    List<Poker> deck = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♢", "♡", "♠");
    for (String suit : allSuits) {
      for (int rank = 1; rank < 14; rank++) {
        deck.add(new Poker(rank, suit));
      }
    }
    this.pp1.startGame(deck, false, 13);
    assertEquals(0, this.pp1.getScore());
    // K, A, Q, J, 10
    this.pp1.placeCardInPosition(12, 1, 1);
    this.pp1.placeCardInPosition(12, 1, 0);
    this.pp1.placeCardInPosition(11, 1, 2);
    this.pp1.placeCardInPosition(10, 1, 4);
    this.pp1.placeCardInPosition(9, 1, 3);
    this.pp1.placeCardInPosition(11, 1, 5);
    assertEquals(15, this.pp1.getScore());
  }

  @Test
  public void testThreeOfAKind() {
    // case: A♣, A♢, A♡, 2♣, 3♣
    this.startPP.placeCardInPosition(0, 5, 1);
    this.startPP.placeCardInPosition(0, 1, 1);
    this.startPP.placeCardInPosition(0, 2, 1);
    this.startPP.placeCardInPosition(1, 3, 1);
    this.startPP.discardCard(4);
    this.startPP.discardCard(4);
    this.startPP.placeCardInPosition(4, 4, 1);
    assertEquals(10, this.startPP.getScore());
  }

  @Test
  public void testTwoPairs() {
    // case: A♢, 2♣, 3♣, 2♢, A♣
    this.startPP.placeCardInPosition(0, 1, 4);
    this.startPP.placeCardInPosition(0, 2, 4);
    this.startPP.placeCardInPosition(2, 3, 4);
    this.startPP.placeCardInPosition(2, 4, 4);
    this.startPP.placeCardInPosition(4, 0, 4);
    assertEquals(5, this.startPP.getScore());
  }

  @Test
  public void testAPair() {
    // case: A♣, A♢, 2♣, 3♣, 4♣
    this.startPP.placeCardInPosition(0, 4, 4);
    this.startPP.placeCardInPosition(0, 4, 3);
    this.startPP.placeCardInPosition(2, 4, 2);
    this.startPP.discardCard(4);
    this.startPP.placeCardInPosition(4, 4, 1);
    this.startPP.discardCard(4);
    this.startPP.discardCard(4);
    this.startPP.discardCard(4);
    this.startPP.placeCardInPosition(4, 4, 0);
    assertEquals(2, this.startPP.getScore());
  }

  @Test
  public void testPokerRectanglesGetRemainingDeckSize() {
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.getRemainingDeckSize(); } );
    assertEquals(47, this.startPP.getRemainingDeckSize());
  }

  @Test
  public void testPokerPolygonsIsGameOver() {
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.isGameOver(); } );
    assertFalse(this.startPP.isGameOver());

    for (int i = 0; i < this.startPP.getHeight(); i++) {
      for (int j = 0; j < this.startPP.getWidth(); j++) {
        this.startPP.placeCardInPosition(0, i, j);
      }
    }
    assertTrue(this.startPP.isGameOver());
  }

  @Test
  public void testPokerRectanglesToString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" __  __  __  __  __\n");
    sb.append(" __  __  __  __  __\n");
    sb.append(" __  __  __  __  __\n");
    sb.append(" __  __  __  __  __\n");
    sb.append(" __  __  __  __  __\n");
    sb.append(" __  __  __  __  __\n");
    sb.append("Deck: 47\n");
    sb.append("Hand: A♣, A♢, A♡, A♠, 2♣");
    assertEquals(sb.toString(), this.view.toString());

    StringBuilder sb1 = new StringBuilder();
    sb1.append(" __  __  __  __  __\n");
    sb1.append(" __  A♣  __  __  __\n");
    sb1.append(" __  __  __  __  __\n");
    sb1.append(" __  __  __  __  __\n");
    sb1.append(" __  __  __  __  __\n");
    sb1.append(" __  __  __  __  A♢\n");
    sb1.append("Deck: 44\n");
    sb1.append("Hand: A♡, A♠, 2♣, 2♢, 2♠");
    this.startPP.placeCardInPosition(0, 1, 1);
    this.startPP.placeCardInPosition(0, 5, 4);
    this.startPP.discardCard(4);
    assertEquals(sb1.toString(), this.view.toString());
  }

  @Test
  public void testPokerRectanglesEqual() {
    assertEquals(new PokerRectangles(6,5), this.startPP);
    assertEquals(new PokerRectangles(5, 6), this.pp1);
    assertFalse(this.pp1.equals(this.startPP));
  }

  @Test
  public void testPokerRectanglesHasCode() {
    PokerRectangles p1 = new PokerRectangles(6,5);
    assertEquals(p1, this.startPP);
    assertFalse(this.pp1.hashCode() == this.startPP.hashCode());
  }
}
