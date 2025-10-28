package src.fx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.entity.Entity;
import src.main.GamePanel;

/**
 * Draws the small health bar above entities (like monsters or the player).
 * 
 * The bar only appears when the entity’s HP isn’t full.
 */
public class HealthBar {
    double worldXDouble;
    double worldYDouble;

    // Array holding all health bar images (different levels)
    BufferedImage[] frame = new BufferedImage[6]; 

    /**
     * Loads the health bar sprites from the resources folder.
     * Each frame shows a slightly lower HP fill level
    */
    public HealthBar() {
        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/fx/HealthBar/Health Bar-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/fx/HealthBar/Health Bar-2.png.png"));
            frame[2] = ImageIO.read(getClass().getResourceAsStream(
                "/res/fx/HealthBar/Health Bar-3.png.png"));
            frame[3] = ImageIO.read(getClass().getResourceAsStream(
                "/res/fx/HealthBar/Health Bar-4.png.png"));
            frame[4] = ImageIO.read(getClass().getResourceAsStream(
                "/res/fx/HealthBar/Health Bar-5.png.png"));
            frame[5] = ImageIO.read(getClass().getResourceAsStream(
                "/res/fx/HealthBar/Health Bar-6.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Draws the correct health bar image above the entity.
     *
     * @param g2 graphics object used to draw
     * @param gp reference to the game panel
     * @param entity the entity whose HP bar we are drawing
    */
    public void draw(Graphics2D g2, GamePanel gp, Entity entity) {
        BufferedImage image = null;
        int i = (entity.hp) * 110 / entity.hpMax / 16;

        if (i > 0 && i < 7 && entity.hp != entity.hpMax) {
            image = frame[6 - i];

            worldXDouble = entity.worldXDouble;
            worldYDouble = entity.worldYDouble;

            int worldX = (int) worldXDouble;
            int worldY = (int) worldYDouble;

            double screenX = worldX - gp.player.worldX + gp.player.screenX;
            double screenY = worldY - gp.player.worldY + gp.player.screenY - 12;

            g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);

        }

    }
    
}
