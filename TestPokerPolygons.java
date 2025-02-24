package cs3500.pokerpolygons;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.Poker;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import cs3500.pokerpolygons.model.hw04.LoosePokerTriangles;
import cs3500.pokerpolygons.model.hw04.PokerAbstract;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;

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
 * Test constructors of Poker, PokerTriangles and LoosePokerTriangles.
 * And any methods defined in all classes.
 */
public class TestPokerPolygons {
  private Card c1;
  private Card c2;
  private Card c3;
  private Card c4;
  private PokerPolygons<Poker> pp1;
  private PokerPolygons<Poker> pp2;
  private PokerPolygons<Poker> startPP;
  private PokerAbstract loose1;
  private PokerAbstract loose2;
  private PokerPolygonsTextualView view;
  private PokerPolygonsTextualView view2;

  @Before
  public void setUp() {
    this.c1 = new Poker(1, "♣");
    this.c2 = new Poker(13, "♡");
    this.c3 = new Poker(4, "♢");
    this.c4 = new Poker(10, "♠");

    this.pp1 = new PokerTriangles(5);
    this.pp2 = new PokerTriangles(7, new Random());
    this.startPP = new PokerTriangles(6);
    this.loose1 = new LoosePokerTriangles(5);
    this.loose2 = new LoosePokerTriangles(5, new Random());
    List<Poker> allPokers = this.startPP.getNewDeck();
    this.startPP.startGame(allPokers, false, 5);
    this.loose1.startGame(allPokers, false, 5);
    this.view = new PokerTrianglesTextualView<>(this.startPP);
    this.view2 = new PokerTrianglesTextualView<>(this.loose1);
  }

  @Test
  public void testInvalidPokerConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Poker(0, "♣"); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new Poker(-1, "♣"); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new Poker(14, "♣"); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new Poker(4, ""); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new Poker(1, "heart"); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new Poker(1, null); } );
  }

  @Test
  public void testPokerEquals() {
    assertEquals(new Poker(1, "♣"), this.c1);
    assertEquals(new Poker(13, "♡"), this.c2);
    assertFalse(this.c1.equals(this.c3));
    assertFalse(this.c4.equals(new Poker(13, "♡")));
  }

  @Test
  public void testPokerHashCode() {
    Card c11 = new Poker(1, "♣");
    assertEquals(this.c1.hashCode(), c11.hashCode());
    assertFalse(this.c1.hashCode() == this.c2.hashCode());
    assertFalse(this.c3.hashCode() == this.c4.hashCode());
  }

  @Test
  public void testPokerToString() {
    assertEquals("A♣", this.c1.toString());
    assertEquals("K♡", this.c2.toString());
    assertEquals("4♢", this.c3.toString());
    assertEquals("10♠", this.c4.toString());
  }

  @Test
  public void testValidPokerTrianglesConstructor() {
    assertEquals(5, this.pp1.getHeight());
    assertEquals(7, this.pp2.getWidth());
    assertEquals(6, this.startPP.getHeight());
    assertEquals(5, this.loose1.getHeight());
  }

  @Test
  public void testInvalidPokerTrianglesConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerTriangles(-1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new LoosePokerTriangles(-1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerTriangles(4); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new LoosePokerTriangles(4); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerTriangles(3, null); } );
    assertThrows(IllegalArgumentException.class, () -> {
      new LoosePokerTriangles(3, null); } );
  }

  @Test
  public void testPokerPolygonsStartGame() {
    List<Poker> allPokers = this.startPP.getNewDeck();
    assertThrows(IllegalStateException.class, () -> {
      this.startPP.startGame(allPokers, false, 5); } );
    assertThrows(IllegalStateException.class, () -> {
      this.loose1.startGame(allPokers, false, 5); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.pp1.startGame(null, false, 5); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.pp2.startGame(allPokers, false, 0); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.pp1.startGame(allPokers, false, -1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.pp1.startGame(allPokers, false, 55); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.loose2.startGame(null, false, 0); } );
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
    this.loose2.startGame(allPokers, true, 10);
    assertEquals(0, this.loose2.getScore());
    assertEquals(42, this.loose2.getRemainingDeckSize());
  }

  @Test
  public void testPokerPolygonsPlaceCard() {
    List<Poker> allPokers = this.startPP.getNewDeck();
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.placeCardInPosition(1, 1, 1); } );
    this.startPP.placeCardInPosition(0, 1, 1);
    assertEquals(allPokers.get(0), this.startPP.getCardAt(1, 1));
    assertEquals(46, this.startPP.getRemainingDeckSize());
    this.startPP.placeCardInPosition(0, 2, 2);
    assertEquals(allPokers.get(1), this.startPP.getCardAt(2, 2));

    List<Poker> allPokers2 = this.loose1.getNewDeck();
    this.loose1.placeCardInPosition(0, 1, 1);
    assertEquals(allPokers.get(0), this.loose1.getCardAt(1, 1));

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
      this.startPP.placeCardInPosition(0, 1, 3); } );

    assertThrows(IllegalStateException.class, () -> {
      this.loose1.placeCardInPosition(0, 1, 1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.loose1.placeCardInPosition(6, 0, 0); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.loose1.placeCardInPosition(0, 6, 6); } );
  }

  @Test
  public void testPokerTrianglesDiscardCard() {
    this.startPP.discardCard(0);
    assertEquals("2♢", this.startPP.getHand().get(4).toString());
    assertEquals("A♢", this.startPP.getHand().get(0).toString());
    this.startPP.discardCard(4);
    assertEquals("2♡", this.startPP.getHand().get(4).toString());
    assertEquals("A♢", this.startPP.getHand().get(0).toString());

    this.loose1.discardCard(0);
    assertEquals("2♢", this.loose1.getHand().get(4).toString());
    assertEquals("A♢", this.loose1.getHand().get(0).toString());
    this.loose1.discardCard(4);
    assertEquals("2♡", this.loose1.getHand().get(4).toString());
    assertEquals("A♢", this.loose1.getHand().get(0).toString());

    assertThrows(IllegalStateException.class, () -> {
      this.pp2.discardCard(1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.discardCard(-1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.discardCard(8); } );

    assertThrows(IllegalStateException.class, () -> {
      this.loose2.discardCard(1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.loose1.discardCard(-1); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.loose1.discardCard(8); } );
  }

  @Test
  public void testPokerPolygonsGetSide() {
    assertEquals(5, this.pp1.getHeight());
    assertEquals(7, this.pp2.getWidth());
    assertEquals(6, this.startPP.getHeight());
    assertEquals(5, this.loose1.getWidth());
  }

  @Test
  public void testPokerTrianglesGetDeck() {
    List<Poker> allPokers = this.startPP.getNewDeck();
    assertEquals(52, allPokers.size());
    assertEquals(47, this.startPP.getRemainingDeckSize());

    List<Poker> allPokers2 = this.loose1.getNewDeck();
    assertEquals(52, allPokers2.size());
    assertEquals(47, this.loose1.getRemainingDeckSize());
  }

  @Test
  public void testPokerPolygonsGetCardAt() {
    assertEquals(null, this.startPP.getCardAt(1, 1));
    this.startPP.placeCardInPosition(1, 1, 1);
    assertEquals("A♢", this.startPP.getCardAt(1, 1).toString());
    this.startPP.placeCardInPosition(0, 0, 0);
    assertEquals("A♣", this.startPP.getCardAt(0, 0).toString());

    assertEquals(null, this.loose1.getCardAt(1, 1));
    this.loose1.placeCardInPosition(1, 1, 1);
    assertEquals("A♢", this.loose1.getCardAt(1, 1).toString());

    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.getCardAt(6, 5); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.getCardAt(5, 6); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.getCardAt(-1, 2); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.startPP.getCardAt(2, -2); } );

    assertThrows(IllegalArgumentException.class, () -> {
      this.loose1.getCardAt(6, 6); } );
    assertThrows(IllegalArgumentException.class, () -> {
      this.loose1.getCardAt(-2, -2); } );
  }

  @Test
  public void testPokerPolygonsGetHand() {
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.getHand(); } );

    assertEquals("A♣", this.startPP.getHand().get(0).toString());
    assertEquals("A♢", this.startPP.getHand().get(1).toString());
    assertEquals("A♡", this.startPP.getHand().get(2).toString());
    assertEquals("A♠", this.startPP.getHand().get(3).toString());
    assertEquals("2♣", this.startPP.getHand().get(4).toString());

    assertEquals("A♣", this.loose1.getHand().get(0).toString());
  }

  @Test
  public void testPokerTrianglesGetScore() {
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.getScore(); } );
    assertEquals(0, this.startPP.getScore());

    assertThrows(IllegalStateException.class, () -> {
      this.loose2.getScore(); } );
    assertEquals(0, this.loose1.getScore());
  }

  @Test
  public void testStraightFlush() {
    List<Poker> deck = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♢", "♡", "♠");
    for (String suit : allSuits) {
      for (int rank = 1; rank < 6; rank++) {
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

    // case: A♣, A♢, A♡, A♠, 2♣ in first col
    this.loose1.placeCardInPosition(0, 0, 0);
    this.loose1.placeCardInPosition(0, 1,0);
    this.loose1.placeCardInPosition(0, 2, 0);
    this.loose1.placeCardInPosition(0, 3, 0);
    this.loose1.placeCardInPosition(0, 4, 0);
    assertEquals(50, this.loose1.getScore());
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

    // case: A♣, A♢, A♡, 2♣, 2♢ in last second row
    this.loose1.placeCardInPosition(0, 4, 4);
    this.loose1.placeCardInPosition(0, 4, 2);
    this.loose1.placeCardInPosition(0, 4, 3);
    this.loose1.placeCardInPosition(1, 4, 1);
    this.loose1.placeCardInPosition(1, 4, 0);
    assertEquals(25, this.loose1.getScore());
  }

  @Test
  public void testFlush() {
    List<Poker> deck = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♣", "♣", "♣");
    for (String suit : allSuits) {
      for (int rank = 1; rank < 5; rank++) {
        deck.add(new Poker(rank, suit));
      }
    }
    this.pp1.startGame(deck, false, 1);
    assertEquals(0, this.pp1.getScore());

    // case: A♣, 2♣, 3♣, 4♣, A♣ in outer diagonal
    this.pp1.placeCardInPosition(0, 0, 0);
    this.pp1.placeCardInPosition(0, 1, 1);
    this.pp1.placeCardInPosition(0, 2, 2);
    this.pp1.placeCardInPosition(0, 3, 3);
    this.pp1.placeCardInPosition(0, 4, 4);
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
    // sepcial case: K, A, Q, J, 10 in outer diagonal
    this.pp1.placeCardInPosition(12, 1, 1);
    this.pp1.placeCardInPosition(12, 0, 0);
    this.pp1.placeCardInPosition(11, 2, 2);
    this.pp1.placeCardInPosition(10, 4, 4);
    this.pp1.placeCardInPosition(9, 3, 3);
    assertEquals(15, this.pp1.getScore());
    // case in first col: A, 3, 2, 5, 4
    this.pp1.placeCardInPosition(1, 2,0);
    this.pp1.placeCardInPosition(1, 1, 0);
    this.pp1.placeCardInPosition(1, 4, 0);
    this.pp1.placeCardInPosition(1, 3, 0);
    assertEquals(30, this.pp1.getScore());
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

    this.loose1.placeCardInPosition(0, 0, 0);
    this.loose1.placeCardInPosition(0, 1, 0);
    this.loose1.placeCardInPosition(0, 2, 0);
    this.loose1.placeCardInPosition(1, 3, 0);
    this.loose1.discardCard(4);
    this.loose1.discardCard(4);
    this.loose1.placeCardInPosition(4, 4, 0);
    assertEquals(10, this.loose1.getScore());
  }

  @Test
  public void testTwoPairs() {
    // case: A♢, 2♣, 3♣, 2♢, A♣
    this.startPP.placeCardInPosition(0, 4, 4);
    this.startPP.placeCardInPosition(0, 4, 0);
    this.startPP.placeCardInPosition(2, 4, 1);
    this.startPP.placeCardInPosition(2, 4, 3);
    this.startPP.placeCardInPosition(4, 4, 2);
    assertEquals(5, this.startPP.getScore());

    // case: A♢, 2♣, 3♣, 2♢, A♣
    this.loose1.placeCardInPosition(0, 4, 4);
    this.loose1.placeCardInPosition(0, 4, 0);
    this.loose1.placeCardInPosition(2, 4, 1);
    this.loose1.placeCardInPosition(2, 4, 3);
    this.loose1.placeCardInPosition(4, 4, 2);
    assertEquals(5, this.loose1.getScore());
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

    this.loose1.placeCardInPosition(0, 4, 4);
    this.loose1.placeCardInPosition(0, 4, 3);
    this.loose1.placeCardInPosition(2, 4, 2);
    this.loose1.discardCard(4);
    this.loose1.placeCardInPosition(4, 4, 1);
    this.loose1.discardCard(4);
    this.loose1.discardCard(4);
    this.loose1.discardCard(4);
    this.loose1.placeCardInPosition(4, 4, 0);
    assertEquals(2, this.loose1.getScore());
  }

  @Test
  public void testPokerPolygonsGetRemainingDeckSize() {
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.getRemainingDeckSize(); } );
    assertEquals(47, this.startPP.getRemainingDeckSize());

    assertThrows(IllegalStateException.class, () -> {
      this.loose2.getRemainingDeckSize(); } );
    assertEquals(47, this.loose1.getRemainingDeckSize());
  }

  @Test
  public void testPokerPolygonsIsGameOver() {
    assertThrows(IllegalStateException.class, () -> {
      this.pp1.isGameOver(); } );
    assertFalse(this.startPP.isGameOver());

    for (int i = 0; i < this.startPP.getWidth(); i++) {
      for (int j = 0; j <= i; j++) {
        this.startPP.placeCardInPosition(0, i, j);
      }
    }
    assertTrue(this.startPP.isGameOver());

    assertThrows(IllegalStateException.class, () -> {
      this.loose2.isGameOver(); } );
    assertFalse(this.loose1.isGameOver());

    for (int i = 0; i < this.loose1.getWidth(); i++) {
      for (int j = 0; j <= i; j++) {
        this.loose1.placeCardInPosition(0, i, j);
      }
    }
    assertTrue(this.loose1.isGameOver());
  }

  @Test
  public void testPokerTrianglesEquals() {
    assertEquals(new PokerTriangles(6), this.startPP);
    assertEquals(new PokerTriangles(5), this.pp1);
    assertFalse(this.pp1.equals(this.startPP));

    assertEquals(new LoosePokerTriangles(5), this.loose2);
  }

  @Test
  public void testPokerTrianglesHashCode() {
    PokerTriangles p1 = new PokerTriangles(5);
    LoosePokerTriangles p2 = new LoosePokerTriangles(5);
    assertEquals(this.pp1.hashCode(), p1.hashCode());
    assertEquals(this.loose2.hashCode(), p2.hashCode());
    assertFalse(this.pp1.hashCode() == this.pp2.hashCode());
    assertFalse(this.pp1.hashCode() == this.startPP.hashCode());
  }

  @Test
  public void testViewConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new PokerTrianglesTextualView<>(null); } );
  }

  @Test
  public void testViewToString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" __\n");
    sb.append(" __  __\n");
    sb.append(" __  __  __\n");
    sb.append(" __  __  __  __\n");
    sb.append(" __  __  __  __  __\n");
    sb.append(" __  __  __  __  __  __\n");
    sb.append("Deck: 47\n");
    sb.append("Hand: A♣, A♢, A♡, A♠, 2♣");
    assertEquals(sb.toString(), this.view.toString());

    StringBuilder sbl = new StringBuilder();
    sbl.append(" __\n");
    sbl.append(" __  __\n");
    sbl.append(" __  __  __\n");
    sbl.append(" __  __  __  __\n");
    sbl.append(" __  __  __  __  __\n");
    sbl.append("Deck: 47\n");
    sbl.append("Hand: A♣, A♢, A♡, A♠, 2♣");
    assertEquals(sbl.toString(), this.view2.toString());

    StringBuilder sb1 = new StringBuilder();
    sb1.append(" A♣\n");
    sb1.append(" __  __\n");
    sb1.append(" __  A♢  __\n");
    sb1.append(" __  __  __  __\n");
    sb1.append(" __  __  __  A♡  __\n");
    sb1.append(" A♠  __  __  __  __  __\n");
    sb1.append("Deck: 43\n");
    sb1.append("Hand: 2♣, 2♢, 2♡, 2♠, 3♣");
    this.startPP.placeCardInPosition(0,0, 0);
    this.startPP.placeCardInPosition(0,2, 1);
    this.startPP.placeCardInPosition(0,4, 3);
    this.startPP.placeCardInPosition(0,5, 0);
    assertEquals(sb1.toString(), this.view.toString());

    StringBuilder sbl1 = new StringBuilder();
    sbl1.append(" A♣\n");
    sbl1.append(" __  __\n");
    sbl1.append(" __  A♢  __\n");
    sbl1.append(" __  __  __  __\n");
    sbl1.append(" __  __  __  A♡  __\n");
    sbl1.append("Deck: 44\n");
    sbl1.append("Hand: A♠, 2♣, 2♢, 2♡, 2♠");
    this.loose1.placeCardInPosition(0,0, 0);
    this.loose1.placeCardInPosition(0,2, 1);
    this.loose1.placeCardInPosition(0,4, 3);
    assertEquals(sbl1.toString(), this.view2.toString());

    for (int i = 0; i < 28; i++) {
      this.startPP.discardCard(4);
    }
    this.startPP.placeCardInPosition(4, 5, 5);

    StringBuilder sb2 = new StringBuilder();
    sb2.append(" A♣\n");
    sb2.append(" __  __\n");
    sb2.append(" __  A♢  __\n");
    sb2.append(" __  __  __  __\n");
    sb2.append(" __  __  __  A♡  __\n");
    sb2.append(" A♠  __  __  __  __ 10♣\n");
    sb2.append("Deck: 14\n");
    sb2.append("Hand: 2♣, 2♢, 2♡, 2♠, 10♢");
    assertEquals(sb2.toString(), this.view.toString());
  }

  // The above part is to test methods which work differently in LoosePokerTriangles
  // I focused on the Score functions, including isFlush, isStraight, isStraightFlush
  @Test
  public void testLoosePokerRectanglesFlush() {
    List<Poker> deck = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♠", "♣", "♠");
    for (String suit : allSuits) {
      for (int rank = 1; rank < 5; rank++) {
        deck.add(new Poker(rank, suit));
      }
    }
    this.loose2.startGame(deck, false, 1);
    assertEquals(0, this.loose2.getScore());

    // case: A♣, 2♠, 3♣, 4♠, A♣ in outer diagonal (all black)
    this.loose2.placeCardInPosition(0, 0, 0);
    this.loose2.placeCardInPosition(0, 1, 1);
    this.loose2.placeCardInPosition(0, 2, 2);
    this.loose2.placeCardInPosition(0, 3, 3);
    this.loose2.placeCardInPosition(0, 4, 4);
    assertEquals(20, this.loose2.getScore());
  }

  @Test
  public void testLoosePokerTrianglesStraight() {
    List<Poker> deck = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♢", "♡", "♠");
    for (String suit : allSuits) {
      for (int rank = 1; rank < 14; rank++) {
        deck.add(new Poker(rank, suit));
      }
    }
    this.loose2.startGame(deck, false, 13);
    assertEquals(0, this.loose2.getScore());
    // sepcial case: K, A, J, 9, 8 in outer diagonal
    this.loose2.placeCardInPosition(12, 1, 1);
    this.loose2.placeCardInPosition(12, 0, 0);
    this.loose2.placeCardInPosition(10, 2, 2);
    this.loose2.placeCardInPosition(8, 4, 4);
    this.loose2.placeCardInPosition(7, 3, 3);
    assertEquals(15, this.loose2.getScore());
    // case in first col: A, 3, 5, 6, 7
    this.loose2.placeCardInPosition(2, 1,0);
    this.loose2.placeCardInPosition(3, 2, 0);
    this.loose2.placeCardInPosition(3, 3, 0);
    this.loose2.placeCardInPosition(3, 4, 0);
    assertEquals(30, this.loose2.getScore());
  }

  @Test
  public void testLoosePokerTrianglesStraightFlush() {
    List<Poker> deck = new ArrayList<>();
    List<String> allSuits = Arrays.asList("♣", "♠", "♢", "♡");
    for (String suit : allSuits) {
      for (int rank = 1; rank < 8; rank++) {
        deck.add(new Poker(rank, suit));
      }
    }
    this.loose2.startGame(deck, false, 5);
    assertEquals(0, this.loose2.getScore());

    // case: A♣, 5♣, 7♣, 3♠, 6♣ in first col
    this.loose2.placeCardInPosition(0, 0, 0);
    this.loose2.placeCardInPosition(3, 1, 0);
    this.loose2.placeCardInPosition(4, 2, 0);
    this.loose2.discardCard(4);
    this.loose2.discardCard(4);
    this.loose2.placeCardInPosition(4, 3, 0);
    this.loose2.placeCardInPosition(3, 4, 0);
    assertEquals(75, this.loose2.getScore());
  }

}