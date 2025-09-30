package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    Plane plane;
    Tile dummyTile1;
    Tile dummyTile2;
    TileMapParser parser;

    @BeforeEach
    void setUp() throws Exception {
        plane = new Plane(3); // 3x3 grid for testing
        // create a dummy tile
        dummyTile1 = new Tile("dummy", new Point(0,0), Map.of(
                Direction.UP, "A", Direction.RIGHT, "B",
                Direction.DOWN, "C", Direction.LEFT, "D"
        ));
        dummyTile2 = new Tile("dummy2", new Point(0,0), Map.of(
                Direction.UP, "A", Direction.RIGHT, "B",
                Direction.DOWN, "C", Direction.LEFT, "D"
        ));
        // create a dummy TileMapParser pointing to Grass test
        parser = new TileMapParser("/tiles/Grass.png", 16, 16);
    }

    @Test
    void testSetAndGetTile() {
        plane.setTile(1, 1, dummyTile1);
        Tile retrieved = plane.getTile(1, 1);
        assertEquals(dummyTile1, retrieved);
    }

    @Test
    void testIsCollapsed() {
        assertFalse(plane.isCollapsed());
        // fill all tiles
        for (int y = 0; y < plane.getSize(); y++) {
            for (int x = 0; x < plane.getSize(); x++) {
                plane.setTile(x, y, dummyTile1);
            }
        }
        assertTrue(plane.isCollapsed());
    }

    @Test
    void testGetNeighbors() {
        // Fill corners
        plane.setTile(0,0, dummyTile1);
        plane.setTile(1,0, dummyTile2);
        plane.setTile(0,1, dummyTile1);

        Tile[] neighbors = plane.getNeighbours(1,1);
        assertEquals(dummyTile2, neighbors[0]); // UP
        assertNull(neighbors[1]); // RIGHT
        assertNull(neighbors[2]); // DOWN
        assertEquals(dummyTile1, neighbors[3]); // LEFT
    }

    @Test
    void testRenderAndSave() throws Exception {
        // Fill the grid with dummy tiles
        for (int y = 0; y < plane.getSize(); y++) {
            for (int x = 0; x < plane.getSize(); x++) {
                plane.setTile(x, y, dummyTile1);
            }
        }

        BufferedImage rendered = plane.render(parser);
        assertNotNull(rendered);
        assertEquals(plane.getSize() * 16, rendered.getWidth());
        assertEquals(plane.getSize() * 16, rendered.getHeight());

        // Save the rendered image
        assertTrue(plane.isCollapsed());
        String path = "resources/test/test_output_plane.png";
        plane.saveAsImage(parser, path);
        File file = new File(path);
        assertTrue(file.exists());
    }
}
