package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Treasure chest object that can be placed in the world (player needs key to enter).
 */
public class OBJ_Door extends SuperObject {

    /**
     * Loads door image (prints an error if the image fails to load).
     */
    public OBJ_Door() {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    } 
}
