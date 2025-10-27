package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/** ADD COMMENT. */ 
public class OBJ_House extends SuperObject {

    /** ADD COMMENT. */ 
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
