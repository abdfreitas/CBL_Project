package src.entity;

import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/**
 * Represents the ThroneBearer enemy entity.
 *
 * This is a large boss-like creature that occupies more space than
 * normal enemies. It has collision enabled, deals damage, and uses
 * two frames for animation
 */
public class ThroneBearer extends Entity {

    /**
     * Creates a new ThroneBearer entity.
     *
     * @param gp reference to the GamePanel
     * @param originX starting x position in the world
     * @param originY starting y position in the world
     * @param name entity name 
    */
    public ThroneBearer(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);

        interactable = false;
        collisionOn = true;
        frameNumMax = 2;
        imageScaleX = 5;
        imageScaleY = 4;
        
        // Define collision hitbox (larger than a normal entity)
        solidArea.x = 8;
        solidArea.y = 80;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 60 * 3;
        solidArea.height = 29 * 3;
        doesDamage = true;

        // Load the animation frames for the thronebearer sprite
        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/ThroneBearer/The Thronebearer-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/ThroneBearer/The Thronebearer-2.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}