package src.drops;

import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/**
 * Coin drop that the player can pick up.
 * 
 * When collected, it increases the players coin count in the GUI.
 */
public class Coin extends DropSupercClass {

    /**
     * Creates a new coin at a given map position.
     * 
     * @param gp reference to the main GamePanel
     * @param originX X position where the coin spawns
     * @param originY Y position where the coin spawns
     * @param name name of drop 
     */
    public Coin(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);
        setup(); // load image and make it pickupable
    }

    /**
     * Loads the coin image and is called automatically when coin is created.
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
     * Adds +1 to the player's coin counter on the GUI when player picks up coin.
    */
    public void pickedUp(GamePanel gp) {
        // Add one coin to the player's total
        gp.gui.coins = gp.gui.coins + 1; 
    }
}
