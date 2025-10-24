package src.drops;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.lib.Random;
import src.main.GamePanel;

public class DropSupercClass {
    
    public String name;

    private int displacementAngle = 0;
    public double displacementAngleOffset = 0;

    public int worldX;
    public int worldY;

    BufferedImage image;
    BufferedImage frame;
    double speed;
    double speedX;
    double speedY;
    double acceleration = 0.1;

    public DropSupercClass(GamePanel gp, int originX, int originY, String name) {

        this.displacementAngleOffset = Random.randomDouble(0, 2 * Math.PI);
        speed = Random.randomDouble(0, 5);
        speedX = speed * Math.cos(displacementAngle);
        speedY = speed * Math.sin(displacementAngle);
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        // drawing logic for this entity

        displacementAngle = displacementAngle + 2;

        BufferedImage image = null;
        double screenX = worldX - gp.player.worldX + gp.player.screenX;
        double screenY = worldY - gp.player.worldY + gp.player.screenY;

        int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle 
            + displacementAngleOffset)) * 6);

        image = frame;
        g2.drawImage(image, (int) screenX, (int) screenY + displacementY, gp.tileSize, gp.tileSize, null);
        
    }

    public void update(GamePanel gp) {
        // drawing logic for this entity

        speed = Math.sqrt(speedX * speedX + speedY * speedY);
        if (speed < acceleration) {
            speed = 0;
        } else if (speed > 0) {
            speed = speed - acceleration;
        } else if (speed < 0) {
            speed = speed + acceleration;
        }

        speedX = Math.cos(displacementAngleOffset) * speed;
        speedY = Math.sin(displacementAngleOffset) * speed;
        

        System.out.println(speedX);

        worldX = (int) ((double) worldX + speedX);
        worldY = (int) ((double) worldY + speedY);
    }

    public void setup() {
        
    }
    
}
