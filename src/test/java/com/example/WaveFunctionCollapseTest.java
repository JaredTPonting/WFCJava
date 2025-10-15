package com.example;

import com.example.utils.ImageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;



class WaveFunctionCollapseTest {

    Plane plane;
    TileMapParser parser;
    List<Tile> tiles;

    Plane grassPlane;
    Plane waterPlane;
    TileMapParser grassParser;
    TileMapParser waterParser;
    List<Tile> grassTiles;
    List<Tile> waterTiles;


    @BeforeEach
    void setUp() throws Exception {
        plane = new Plane(50);
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

    @Test
    void testSettingEdgeAndSave() throws Exception {
        WaveFunctionCollapse wfc = new WaveFunctionCollapse(plane, tiles);
        wfc.SetEdge(new Tile(
                "GRASS",
                new Point(1, 1),
                Map.of(
                        Direction.UP, "AAA",
                        Direction.DOWN, "AAA",
                        Direction.LEFT, "AAA",
                        Direction.RIGHT, "AAA"
                )
        ));
        wfc.collapse();

        assertTrue(plane.isCollapsed(), "Plane should be fully collapsed");

        // Render
        BufferedImage image = plane.render(parser);
        assertNotNull(image);
        assertEquals(plane.getSize() * 16, image.getWidth());
        assertEquals(plane.getSize() * 16, image.getHeight());

        // Save
        String path = "resources/test/test_output_set_edge_wfc.png";
        plane.saveAsImage(parser, path);

        File file = new File(path);
        assertTrue(file.exists());
    }

    @Test
    void testCombiningImages() throws Exception {
        grassPlane = new Plane(50);
        grassTiles = TileFactory.generateTiles();
        grassParser = new TileMapParser("/tiles/Grass.png", 16, 16);

        WaveFunctionCollapse grassWFC = new WaveFunctionCollapse(grassPlane, grassTiles);
        grassWFC.SetEdge(new Tile(
                "GRASS",
                new Point(1, 1),
                Map.of(
                        Direction.UP, "AAA",
                        Direction.DOWN, "AAA",
                        Direction.LEFT, "AAA",
                        Direction.RIGHT, "AAA"
                )
        ));
        grassWFC.collapse();
        assertTrue(grassPlane.isCollapsed());

        waterPlane = new Plane(50);
        waterTiles = new ArrayList<>();
        waterTiles.add(new Tile(
                "WATER",
                new Point(0, 0),
                Map.of(
                        Direction.UP, "*",
                        Direction.DOWN, "*",
                        Direction.LEFT, "*",
                        Direction.RIGHT, "*"
                )
                ));
        waterParser = new TileMapParser("/tiles/Water.png", 16, 16);

        WaveFunctionCollapse waterWFC = new WaveFunctionCollapse(waterPlane, waterTiles);
        waterWFC.collapse();
        assertTrue(waterPlane.isCollapsed());

        BufferedImage grassImage = grassPlane.render(grassParser);
        BufferedImage waterImage = waterPlane.render(waterParser);

        BufferedImage finalImage = ImageUtils.overlayImages(waterImage, grassImage);
        ImageUtils.saveImage(finalImage, "resources/test/final_overlay.png");
    }
}
