package src.drops;

import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/**
 * Represents heart drop that restores the players HP when picked up.
 */
public class Heart extends DropSupercClass {

    /**
     * Creates a new heart at a given map position.
     * 
     * @param gp reference to the gamepanel
     * @param originX x position where the coin spawns
     * @param originY y position where the coin spawns
     * @param name name of drop
     */     
    public Heart(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);
        setup(); // load image and mark as pickupable

    }

    /**
     * Loads the hearts image and makes it collectible.
    */
    public void setup() {
        pickupable = true;
    
        try {
            frame = ImageIO.read(getClass().getResourceAsStream("/res/drops/Heart-1.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the heart is picked up by the player.
     * Increases player HP by 10, but not above maximum HP.
     */
    public void pickedUp(GamePanel gp) {
        gp.player.HP = gp.player.HP + 10;
        if (gp.player.HP > gp.player.HPMax) {
            gp.player.HP = gp.player.HPMax;
        }
    }
}
