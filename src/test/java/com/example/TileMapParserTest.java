package com.example;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TileMapParserTest {

    @Test
    void testTileMapParserLoadsAndExtractsTile() throws Exception {
        String path = "/tiles/Grass.png";
        int tileWidth = 16;
        int tileHeight = 16;

        TileMapParser parser = new TileMapParser(path, tileWidth, tileHeight);

        // Check that tilesPerRow and tilesPerColumn are as expected
        assertEquals(11, parser.tilesPerRow, "Tiles per row should be 11");
        assertEquals(7, parser.tilesPerColumn, "Tiles per column should be 7");

        // Extract a tile
        BufferedImage tile = parser.getTile(1, 0);
        assertNotNull(tile);
        assertEquals(tileWidth, tile.getWidth());
        assertEquals(tileHeight, tile.getHeight());

        // Save the tile to disk so you can see it
        File output = new File("resources/test/test_output_tile_1_0.png");
        ImageIO.write(tile, "png", output);
        System.out.println("Saved subimage to: " + output.getAbsolutePath());

        // extract and save different tile
        BufferedImage midTile = parser.getTile(0, 1);
        ImageIO.write(midTile, "png", new File("resources/test/test_output_tile_0_1.png"));
    }
}
