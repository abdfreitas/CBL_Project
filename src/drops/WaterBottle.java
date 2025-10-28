package src.drops;

import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/**
 * Represents a water bottle drop in the game.
 * When the player picks it up, it increases their water amount in watercan by 1.
 */
public class WaterBottle extends DropSupercClass {

    /**
     * Creates a new waterbottle at a given map position.
     *
     * @param gp reference to the game panel
     * @param originX x position where the bottle spawns
     * @param originY y position where the bottle spawns
     * @param name name of drop
     */
    public WaterBottle(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);
        setup();
    }

    /**
     * Loads the water bottles image and makes it collectible.
    */
    public void setup() {
        pickupable = true;
    
        try {
            frame = ImageIO.read(getClass().getResourceAsStream("/res/drops/Bottle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the heart is picked up by the player.
     * Increases player water by 1.
     */
    public void pickedUp(GamePanel gp) {
        gp.player.waterAmount++;
    } 
}
