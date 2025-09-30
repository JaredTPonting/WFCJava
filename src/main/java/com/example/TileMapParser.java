package com.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/* Made this to help read tilempa and extract a uniqe tile from it based on coordinates given. (0,0) will be top left */

public class TileMapParser {
    private final BufferedImage tilemapImage;
    final int tileWidth; // Width of each tile
    final int tileHeight; // Height of each tile (example grass are 16x16)
    public final int tilesPerRow; // how many tiles in a row
    public final int tilesPerColumn; // how many tiles in a column

    public TileMapParser(String tilemapResourcePath, int tileWidth, int tileHeight) throws IOException {
        // Load from classpath, works in IDE and JAR
        if (tilemapResourcePath == null){
            throw new IllegalArgumentException("Tilemap resourrce path can not be null!!!");
        }
        this.tilemapImage = ImageIO.read(getClass().getResourceAsStream(tilemapResourcePath));
        if (tilemapImage == null) {
            throw new IOException("Resource not found: " + tilemapResourcePath);
        }
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tilesPerRow = tilemapImage.getWidth() / tileWidth;
        this.tilesPerColumn = tilemapImage.getHeight() / tileHeight;
    }

    public BufferedImage getTile(int col, int row) {
        return tilemapImage.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
    }
}
