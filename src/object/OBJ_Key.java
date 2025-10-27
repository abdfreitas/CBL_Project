package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/** ADD COMMENT. */ 
public class OBJ_Key extends SuperObject {
    
    /** ADD COMMENT. */ 
    public OBJ_Key() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
