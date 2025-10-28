package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Treasure chest object that can be placed in the world.
 */
public class OBJ_Chest extends SuperObject {
    
    /**
     * Loads chest image (prints an error if the image fails to load).
     */
    public OBJ_Chest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
