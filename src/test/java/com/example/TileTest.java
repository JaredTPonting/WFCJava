package com.example;

import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    void testTileStoresNameLocationAndEdges() {
        // Setup edges
        Map<Direction, String> edges = new EnumMap<>(Direction.class);
        edges.put(Direction.UP, "AAA");
        edges.put(Direction.RIGHT, "ABA");
        edges.put(Direction.DOWN, "AAA");
        edges.put(Direction.LEFT, "BBB");

        // Create tile
        Tile tile = new Tile("water_left", new Point(2, 1), edges);

        // Test name
        assertEquals("water_left", tile.getName());

        // Test tilemap location
        assertEquals(new Point(2, 1), tile.getTilemapLocation());

        // Test edges
        assertEquals("AAA", tile.getEdge(Direction.UP));
        assertEquals("ABA", tile.getEdge(Direction.RIGHT));
        assertEquals("AAA", tile.getEdge(Direction.DOWN));
        assertEquals("BBB", tile.getEdge(Direction.LEFT));
    }

    @Test
    void testToStringContainsNameAndEdges() {
        Map<Direction, String> edges = new EnumMap<>(Direction.class);
        edges.put(Direction.UP, "AAA");
        edges.put(Direction.RIGHT, "ABA");
        edges.put(Direction.DOWN, "AAA");
        edges.put(Direction.LEFT, "BBB");

        Tile tile = new Tile("water_left", new Point(2, 1), edges);

        String str = tile.toString();
        assertTrue(str.contains("water_left"));
        assertTrue(str.contains("AAA"));
        assertTrue(str.contains("ABA"));
        assertTrue(str.contains("BBB"));
    }
}
