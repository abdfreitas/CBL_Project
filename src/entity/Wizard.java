package src.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/** Represents the Wizard entity in the game. */
public class Wizard extends Entity {

    /**
     * Creates a new Wizard entity.
     *
     * @param gp reference to the GamePanel
     * @param originX starting x position in the world
     * @param originY starting y position in the world
     * @param name entity name 
    */
    public Wizard(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);

        // The wizard uses 8 animation frames
        frame = new BufferedImage[8];
        interactable = true;
        collisionOn = true;
        frameNumMax = 8;
        spriteCounterMax = 12;
        imageScaleX = 1;
        imageScaleY = 2;
        solidArea.height = 64;
        doesDamage = true;

        // Load wizards animation frames
        try {
            for (int i = 0; i < 8; i++) {
                String path = "/res/entities/wizard/WizardNew-" + (i + 1) + ".png.png";
                frame[i] = ImageIO.read(getClass().getResourceAsStream(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}