package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Collectible boots item placed in the world. (speed boost)
 */
public class OBJ_Coffee extends SuperObject {

    /**
     * Loads boot image (prints an error if the image fails to load).
     */
    public OBJ_Coffee() {
        name = "Coffee";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/coffee.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
