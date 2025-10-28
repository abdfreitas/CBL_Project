package src.entity;

import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/**
 * Represents a Snail enemy entity.
 *
 * This class defines a basic snail creature that damages the player on contact.
 * It is animated with two frames and has collision enabled but is not interactable (yet).
 */
public class Snail1 extends Entity {

    /**
     * Creates a new Snail enemy at the given world coordinates.
     *
     * @param gp reference to the GamePanel
     * @param originX starting x position in the world
     * @param originY starting y position in the world
     * @param name entity name 
     */
    public Snail1(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);

        interactable = false;
        collisionOn = true;
        frameNumMax = 2;
        doesDamage = true;

        // Load the two animation frames for the snail sprite
        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/snail1/Snail1-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/snail1/Snail1-2.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}