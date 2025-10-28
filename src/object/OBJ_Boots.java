package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Collectible boots item placed in the world. (speed boost)
 */
public class OBJ_Boots extends SuperObject {

    /**
     * Loads boot image (prints an error if the image fails to load).
     */
    public OBJ_Boots() {
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
