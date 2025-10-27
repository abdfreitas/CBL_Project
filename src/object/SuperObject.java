package src.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import src.main.GamePanel;

/** ADD COMMENT. */ 
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

    /** ADD COMMENT. */ 
    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + (gp.tileSize * (scaleX)) > gp.player.worldX - gp.player.screenX 
            && worldX - (gp.tileSize * (scaleX)) < gp.player.worldX + gp.player.screenX     
            && worldY + (gp.tileSize * (scaleY)) > gp.player.worldY - gp.player.screenY 
            && worldY - (gp.tileSize * (scaleY)) < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY,
                gp.tileSize * scaleX, gp.tileSize * scaleY, null);
        } 
    }
}
