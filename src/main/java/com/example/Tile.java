package com.example;

import java.awt.Point;
import java.util.Map;

public class Tile {
    private final String name;
    private final Point tilemapLocation; //column, row in the tilemap PNG
    private final Map<Direction, String> edges; // UP, DOWN, LEFT, RIGHT

    public Tile(String name, Point tilemapLocation, Map<Direction, String> edges) {
        this.name = name;
        this.tilemapLocation = tilemapLocation;
        this.edges = edges;
    }

    public String getName() {
        return this.name;
    }
    public Point getTilemapLocation() {
        return this.tilemapLocation;
    }
    public String getEdge(Direction dir) {
        return edges.get(dir);
    }

    @Override
    public String toString() {
        return "Tile{name='" + name + "', tilemapLocation=" + tilemapLocation + ", edges=" + edges + "}";
    }
}
