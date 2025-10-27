package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/** ADD COMMENT. */ 
public class OBJ_Chest extends SuperObject {
    
    /** ADD COMMENT. */ 
    public OBJ_Chest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
