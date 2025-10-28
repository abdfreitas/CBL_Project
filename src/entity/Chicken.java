package src.entity;

import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/** Represents a chicken entity in the game. */
public class Chicken extends Entity {

    /**
     * Creates a new Chicken at the given world coordinates.
     *
     * @param gp reference to the GamePanel
     * @param originX starting x position in the world
     * @param originY starting y position in the world
     * @param name entity name 
    */    
    public Chicken(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);
        interactable = true;
        collisionOn = true;
        frameNumMax = 2; // Chicken has 2 animation frames
        doesDamage = true;

        // Load chicken sprite images
        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/chicken/Chicken-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/chicken/Chicken-2.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}