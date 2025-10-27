package src.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/** ADD COMMENT. */ 
public class OBJ_Boots extends SuperObject {

    /** ADD COMMENT. */ 
    public OBJ_Boots() {
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
