package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * House object that can be placed in the world.
 */
public class OBJ_House extends SuperObject {

    /**
     * Loads house image (prints an error if the image fails to load).
     */ 
    public OBJ_House() {
        name = "House";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/House-1.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaleX = 3;
        scaleY = 2;
        collision = true;
    }
}
