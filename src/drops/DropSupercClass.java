package src.drops;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.entity.Player;
import src.lib.Random;
import src.main.GamePanel;

public class DropSupercClass {
    
    public String name;

    public boolean pickupable = false;

    private int displacementAngle = 0;
    public double displacementAngleOffset = 0;

    public int worldX;
    public int worldY;
    public double worldXDouble;
    public double worldYDouble;

    BufferedImage image;
    BufferedImage frame;
    double speed;
    double speedX;
    double speedY;
    double acceleration = 0.1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    double dX;
    double dY;
    double pullForce = 200;
    double pullRange = 100;
    double distance;
    double speedAngle;
    double targetAngle;

    

    public DropSupercClass(GamePanel gp, int originX, int originY, String name) {

        this.displacementAngleOffset = Random.randomDouble(0, 2 * Math.PI);
        speed = Random.randomDouble(1, 5);
        speedX = speed * Math.cos(displacementAngle);
        speedY = speed * Math.sin(displacementAngle);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        // drawing logic for this entity

        displacementAngle = displacementAngle + 2;

        speedX = Math.cos(displacementAngleOffset) * speed;
        speedY = Math.sin(displacementAngleOffset) * speed;

        BufferedImage image = null;
        double screenX = worldX - gp.player.worldX + gp.player.screenX;
        double screenY = worldY - gp.player.worldY + gp.player.screenY;

        int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle 
            + displacementAngleOffset)) * 6);

        image = frame;
        g2.drawImage(image, (int) screenX, (int) screenY + displacementY, gp.tileSize, gp.tileSize, null);
        
    }

    public void update(GamePanel gp, Player player) {
        // drawing logic for this entity

        speed = Math.sqrt(speedX * speedX + speedY * speedY);
        speedAngle = Math.atan2(speedY, speedX);

        if (speed < acceleration) {
            speed = 0;
        } else if (speed > 0) {
            speed = speed - acceleration;
        } else if (speed < 0) {
            speed = speed + acceleration;
        }

        
        speedX = speed * Math.cos(speedAngle);
        speedY = speed * Math.sin(speedAngle);
        

        dX = player.worldXDouble + gp.tileSize / 2 - worldXDouble;
        dY = player.worldYDouble + gp.tileSize / 2 - worldYDouble;

        distance = Math.sqrt(dX * dX + dY * dY);
        targetAngle = Math.atan2(dY, dX);

        if (distance < pullRange) {

            speedX = speedX + pullForce *  Math.cos(targetAngle) / distance;
            speedY = speedY + pullForce *  Math.sin(targetAngle) / distance;
            
            
        }

        

        

        
        

        System.out.println(pullForce *  Math.cos(speedAngle) / distance);

        worldX = (int) ((double) worldX + speedX);
        worldY = (int) ((double) worldY + speedY);
        worldXDouble = (double) worldX;
        worldYDouble = (double) worldY;
    }

    public void setup() {
        
    }
    
}
