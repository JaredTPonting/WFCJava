package com.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* Used to build your tiles, defined in tileDefs. Each entry represents a tile in the tileset. */

public class TileFactory {
    public static Tile createTile(String name, int col, int row, String up, String right, String down, String left) {
        return new Tile(name, new Point(col, row), Map.of(
                Direction.UP, up,
                Direction.RIGHT, right,
                Direction.DOWN, down,
                Direction.LEFT, left
        ));
    }

    public static List<Tile> generateTiles() {
        // Generates a bunch of tiles using the createTile method and the tileDefs array
        String[][] tileDefs = {
                {"Grass1", "0", "0", "BBB", "BAA", "BAA", "BBB"},
                {"Grass2", "0", "1", "BAA", "AAA", "BAA", "BBB"},
                {"Grass3", "0", "2", "BAA", "AAB", "BBB", "BBB"},

                {"Grass4", "1", "0", "BBB", "BAA", "AAA", "BAA"},
                {"Grass5", "1", "1", "AAA", "AAA", "AAA", "AAA"},
                {"Grass6", "1", "2", "AAA", "AAB", "BBB", "AAB"},

                {"Grass7", "2", "0", "BBB", "BBB", "AAB", "BAA"},
                {"Grass8", "2", "1", "AAB", "BBB", "AAB", "AAA"},
                {"Grass9", "2", "2", "AAB", "BBB", "BBB", "AAB"},

                {"Grass10", "3", "0", "BBB", "BBB", "BAB", "BBB"},
                {"Grass11", "3", "1", "BAB", "BBB", "BAB", "BBB"},
                {"Grass13", "3", "2", "BAB", "BBB", "BBB", "BBB"},

                {"Grass14", "4", "0", "BBB", "BAB", "BAB", "BBB"},
                {"Grass15", "4", "1", "BAA", "AAB", "BAB", "BBB"},
                {"Grass16", "4", "2", "ABA", "BAA", "BAA", "BBB"},
                {"Grass16", "4", "3", "ABA", "ABA", "BBB", "BBB"},

                {"Grass17", "5", "0", "BBB", "BAB", "AAB", "BAA"},
                {"Grass18", "5", "1", "AAA", "AAB", "AAB", "AAA"},
                {"Grass19", "5", "2", "AAB", "BAA", "AAA", "AAA"},
                {"Grass20", "5", "3", "AAB", "BAB", "BBB", "AAB"},

                {"Grass20", "6", "0", "BBB", "BAA", "BAA", "BAB"},
                {"Grass21", "6", "1", "AAA", "AAA", "BAA", "AAB"},
                {"Grass22", "6", "2", "BAA", "AAA", "AAA", "BAA"},
                {"Grass23", "6", "3", "BAA", "AAB", "BBB", "BAB"},

                {"Grass24", "7", "0", "BBB", "BBB", "BAB", "BAB"},
                {"Grass25", "7", "1", "AAB", "BBB", "BAB", "AAB"},
                {"Grass26", "7", "2", "BAB", "BBB", "AAB", "BAA"},
                {"Grass27", "7", "3", "BAB", "BBB", "BBB", "BAB"},
                // Replace with your own tiles or use this with example tile sheet
        };

        List<Tile> tiles = new ArrayList<>();
        for (String[] def : tileDefs) {
            tiles.add(createTile(def[0],
                    Integer.parseInt(def[1]),
                    Integer.parseInt(def[2]),
                    def[3], def[4], def[5], def[6]));
        }
        return tiles;
    }
}
