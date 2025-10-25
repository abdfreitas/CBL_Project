package src.drops;

import java.io.IOException;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class Heart extends DropSupercClass{

    public Heart(GamePanel gp, int originX, int originY, String name){
        super(gp, originX, originY, name);
        setup();

    }

    public void setup() {

        pickupable = true;
        

        try {
            frame = ImageIO.read(getClass().getResourceAsStream("/res/drops/Heart-1.png.png"));
        
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void pickedUp(GamePanel gp) {
        gp.player.HP = gp.player.HP + 10;
        if (gp.player.HP > gp.player.HPMax) {
            gp.player.HP = gp.player.HPMax;
        }
    }
    
}
