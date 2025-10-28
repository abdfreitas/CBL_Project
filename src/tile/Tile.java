package src.tile;

import java.awt.image.BufferedImage;

/**
 * Basic tile class used for every part of the map.
 * 
 * Each tile has an image (what it looks like)
 * and a collision flag to tell if you can walk through it or not
 */
public class Tile {
    public BufferedImage image;
    public boolean collision = false;
}
