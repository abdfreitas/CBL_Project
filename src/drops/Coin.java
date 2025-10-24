package src.drops;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class Coin extends DropSupercClass{

    public Coin(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);
        setup();
    }

    public void setup() {
        

        try {
            frame = ImageIO.read(getClass().getResourceAsStream("/res/entities/flower1/Flower 1-1.png.png"));
        
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // public void draw(Graphics2D g2, GamePanel gp) {
    //     // drawing logic for this entity

    //     BufferedImage image = null;
    //     double screenX = worldX - gp.player.worldX + gp.player.screenX;
    //     double screenY = worldY - gp.player.worldY + gp.player.screenY;

    //     double displacementAngle = 0;

    //     image = frame;

    //     int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle 
    //         + displacementAngleOffset)) * 10);
    //     g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);
        
    // }
    
}
