package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



class WaveFunctionCollapseTest {

    Plane plane;
    TileMapParser parser;
    List<Tile> tiles;


    @BeforeEach
    void setUp() throws Exception {
        plane = new Plane(10);
        tiles = TileFactory.generateTiles();
        parser = new TileMapParser("/tiles/Grass.png", 16, 16);
    }

    @Test
    void testCollapse() {
        WaveFunctionCollapse wfc = new WaveFunctionCollapse(plane, tiles);

        wfc.collapse();

        // Check that the plane is fully collapsed
        assertTrue(plane.isCollapsed(), "Plane should be fully collapsed");

        // Optional: check neighbor edge compatibility using WFC logic
        int size = plane.getSize();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Tile tile = plane.getTile(x, y);
                Tile[] neighbors = plane.getNeighbours(x, y);

                if (neighbors[0] != null)
                    assertTrue(matches(tile.getEdge(Direction.UP), neighbors[0].getEdge(Direction.DOWN)));
                if (neighbors[1] != null)
                    assertTrue(matches(tile.getEdge(Direction.RIGHT), neighbors[1].getEdge(Direction.LEFT)));
                if (neighbors[2] != null)
                    assertTrue(matches(tile.getEdge(Direction.DOWN), neighbors[2].getEdge(Direction.UP)));
                if (neighbors[3] != null)
                    assertTrue(matches(tile.getEdge(Direction.LEFT), neighbors[3].getEdge(Direction.RIGHT)));
            }
        }
    }

    /** Helper to respect wildcard * */
    private boolean matches(String edgeA, String edgeB) {
        return "*".equals(edgeA) || "*".equals(edgeB) || edgeA.equals(edgeB);
    }

    @Test
    void testRenderAndSaveFullPlane() throws Exception {
        WaveFunctionCollapse wfc = new WaveFunctionCollapse(plane, tiles);
        wfc.collapse();

        assertTrue(plane.isCollapsed(), "Plane should be fully collapsed");

        // Render
        BufferedImage image = plane.render(parser);
        assertNotNull(image);
        assertEquals(plane.getSize() * 16, image.getWidth());
        assertEquals(plane.getSize() * 16, image.getHeight());

        // Save
        String path = "resources/test/test_output_wfc.png";
        plane.saveAsImage(parser, path);

        File file = new File(path);
        assertTrue(file.exists());
//        file.delete(); // clean up, Comemnted out so I can see the image
    }
}
