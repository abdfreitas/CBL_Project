package src.drops;

import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/**
 * Represents a coin drop in the game.
 * When the player picks it up, it increases their coin amount in watercan by 1.
 */
public class Coin extends DropSupercClass {

    /**
     * Creates a new coin at a given map position.
     * 
     * @param gp reference to the gamepanel
     * @param originX x position where the coin spawns
     * @param originY y position where the coin spawns
     * @param name name of drop 
     */
    public Coin(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);
        setup(); // load image and make it pickupable
    }

    /**
     * Loads the coins image and makes it collectible.
    */
    public void setup() {
        pickupable = true;

        try {
            frame = ImageIO.read(getClass().getResourceAsStream("/res/drops/Coin-1.png.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Called when the coin is picked up by the player.
     * Increases player coin count by 1.
     */
    public void pickedUp(GamePanel gp) {
        gp.gui.coins = gp.gui.coins + 1; 
    }
}
