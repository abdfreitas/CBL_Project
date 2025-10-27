package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/** ADD COMMENT. */ 
public class OBJ_Door extends SuperObject {

    /** ADD COMMENT. */ 
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
