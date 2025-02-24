package cs3500.pokerpolygons;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import cs3500.pokerpolygons.model.hw04.LoosePokerTriangles;
import cs3500.pokerpolygons.model.hw04.PokerPolygonsBuilder;
import cs3500.pokerpolygons.model.hw04.PokerRectangles;

/**
 * test the builder of Poker Polygons with three different variants.
 */
public class TestPokerPolygonsBuilder {
  @Test
  public void testTriangleBuilder() {
    PokerPolygons tri = new PokerTriangles(5);
    PokerPolygons builder = new PokerPolygonsBuilder()
            .setType(PokerPolygonsBuilder.GameType.TRI)
            .setSideLength(5)
            .build();

    assertEquals(tri, builder);
  }

  @Test
  public void testLooseTriangleBuilder() {
    PokerPolygons loose = new LoosePokerTriangles(10);
    PokerPolygons builder = new PokerPolygonsBuilder()
            .setType(PokerPolygonsBuilder.GameType.LOOSE)
            .setSideLength(10)
            .build();

    assertEquals(loose, builder);
  }

  @Test
  public void testRectangleBuilder() {
    PokerPolygons rect = new PokerRectangles(5, 6);
    PokerPolygons builder = new PokerPolygonsBuilder()
            .setType(PokerPolygonsBuilder.GameType.RECT)
            .setWidth(6)
            .setHeight(5)
            .build();

    assertEquals(rect, builder);
  }
}