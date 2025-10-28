package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Key object that can be placed in the world (player can pick up to open doors).
 */ 
public class OBJ_Key extends SuperObject {
    
    /**
     * Loads key image (prints an error if the image fails to load).
     */
    public OBJ_Key() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
