package cs3500.pokerpolygons.model.hw02;

import java.util.List;
import java.util.Objects;
import java.util.Arrays;

/**
 * Represents a playing card in a standard deck used in Poker Polygons.
 * Each card has a rank (1-13) and a suit (♣, ♢, ♡, ♠).
 */
public class Poker implements Card {
  private final int rank;
  private final String suit;
  private final String color;

  /**
   * Create a poker with rank and suit.
   * I designed A here which rank is 1.
   * @param rank the rank number from 1 to 13 on the poker.
   *             (A could represent 14 in some special cases)
   * @param suit ths suit on the poker, one of "♣", "♢", "♡", "♠"
   * @throws IllegalArgumentException if the rank is not between 1 and 13
   * @throws IllegalArgumentException if the suit is not one of "♣", "♢", "♡", "♠"
   */
  public Poker(int rank, String suit) {
    if (rank < 1 || rank > 13) {
      throw new IllegalArgumentException("Rank must be between 1 and 13");
    }

    List<String> allSuits = Arrays.asList("♣", "♢", "♡", "♠");
    if (!allSuits.contains(suit)) {
      throw new IllegalArgumentException("Suit is invalid");
    }
    this.rank = rank;
    this.suit = suit;

    if (suit.equals("♣") || suit.equals("♠")) {
      this.color = "black";
    } else {
      this.color = "red";
    }
  }

  /**
   * helper method: get the rank of one poker.
   * @return the rank number of the poker
   */
  public int getRank() {
    return this.rank;
  }

  /**
   * helper method: get the suit of one poker.
   * @return the suit string of the poker
   */
  public String getSuit() {
    return this.suit;
  }

  /**
   * helper method: get the color of one poker.
   * @return
   */
  public String getColor() {
    return this.color;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Poker)) {
      return false;
    }

    Poker otherPoker = (Poker) other;
    return rank == otherPoker.rank && suit.equals(otherPoker.suit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rank, suit);
  }

  @Override
  public String toString() {
    String rankStr = "";
    if (rank == 1) {
      rankStr = "A";
    } else if (rank == 11) {
      rankStr = "J";
    } else if (rank == 12) {
      rankStr = "Q";
    } else if (rank == 13) {
      rankStr = "K";
    } else {
      rankStr = "" + rank;
    }
    return rankStr + suit;
  }
}
