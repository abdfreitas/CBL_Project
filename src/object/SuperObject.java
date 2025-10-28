package src.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import src.main.GamePanel;

/**
 * Base class for all ingame objects (like keys, chests, doors)
 * 
 * Each object has a position in the map, image, and hitbox.
 * Basically, anything that can be drawn on the map or interacted with
 * extends this class.
 */
public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX;
    public int worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public int scaleX = 1;
    public int scaleY = 1;

    /**
     * Draws the object on the screen based on where the player is.
     * 
     * The world is bigger than the screen, so this figures out
     * where the object should appear relative to the player camera position
     */
    public void draw(Graphics2D g2, GamePanel gp) {

        // Object world position into screen position (camera following player)
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw the object if it's within the visible screen area
        if (worldX + (gp.tileSize * (scaleX)) > gp.player.worldX - gp.player.screenX 
            && worldX - (gp.tileSize * (scaleX)) < gp.player.worldX + gp.player.screenX     
            && worldY + (gp.tileSize * (scaleY)) > gp.player.worldY - gp.player.screenY 
            && worldY - (gp.tileSize * (scaleY)) < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY,
                gp.tileSize * scaleX, gp.tileSize * scaleY, null);
        } 
    }
}
