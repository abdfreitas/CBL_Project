package src.drops;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import src.entity.Player;
import src.lib.Random;
import src.main.GamePanel;

/**
 * Base class for all dropped items.
 * 
 * Handles basic physics, floating animation, and pickup.
 */
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

    public Rectangle solidArea; // hitbox for pickup detection
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    double dX;
    double dY;
    double pullForce = 200; // how strong the magnet pull is
    double pullRange = 100; // how close player needs to be to attract it
    double distance;
    double speedAngle;
    double targetAngle;
    double screenX;
    double screenY;

    public boolean pickedUp = false;
    public boolean interactable = false;

    /**
     * Creates a new drop at a certain location.
     * Gives it a random direction and speed for when it spawns.
     */
    public DropSupercClass(GamePanel gp, int originX, int originY, String name) {

        this.displacementAngleOffset = Random.randomDouble(0, 2 * Math.PI);

        // Give each drop a random small push when it spawns
        //System.out.println(displacementAngleOffset);
        speed = Random.randomDouble(1, 5);
        speedX = speed * Math.cos(displacementAngle);
        speedY = speed * Math.sin(displacementAngle);

        // Give each drop a random small push when it spawns
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;
    }

    /**
     * Draws the drop on screen.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        // Make the drop bounce up and down a bit
        displacementAngle = displacementAngle + 2;

        // This line shouldnt be here but somehow everything breaks if removed
        speedX = Math.cos(displacementAngleOffset) * speed;
        speedY = Math.sin(displacementAngleOffset) * speed;

        BufferedImage image = null;
        // Convert world position to screen position (camera system)
        double screenX = worldX - gp.player.worldX + gp.player.screenX;
        double screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Calculate the float offset for the bouncing animation
        int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle) 
            + displacementAngleOffset) * 6);

        image = frame;
        g2.drawImage(image, (int) screenX, (int) screenY + displacementY,
             gp.tileSize, gp.tileSize, null);
        
    }

    /**
     * Updates the drops movement and attraction toward the player.
     */
    public void update(GamePanel gp, Player player) {
        // If already picked up, trigger pickup behavior once and exit
        if (pickedUp) {
            pickedUp(gp);
            return;
        }

        // Apply friction (slow down bit by bit)
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

        // Calculate distance and direction to the player
        dX = player.worldXDouble + gp.tileSize / 2 - worldXDouble;
        dY = player.worldYDouble + gp.tileSize / 2 - worldYDouble;

        distance = Math.sqrt(dX * dX + dY * dY);
        targetAngle = Math.atan2(dY, dX);

        // If player is close make the item go towards them 
        if (distance < pullRange) {
            speedX = speedX + pullForce *  Math.cos(targetAngle) / distance;
            speedY = speedY + pullForce *  Math.sin(targetAngle) / distance;  
        }

        worldX = (int) ((double) worldX + speedX);
        worldY = (int) ((double) worldY + speedY);
        worldXDouble = (double) worldX;
        worldYDouble = (double) worldY;
    }

    public void setup() {
        
    }

    public void pickedUp(GamePanel gp) {

    }

    /**
     * Checks if player has interacted with this drop.
     */
    public boolean interactedWith(GamePanel gp) {
        return gp.keyH.interactPressed && underCursor(gp);
    }

    /**
     * Checks if the mouse is hovering over the drop (for interactions).
    */
    public boolean underCursor(GamePanel gp) {

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        return gp.mIn.mouseX > screenX + solidArea.x 
                && gp.mIn.mouseX < screenX + solidArea.x + solidArea.width
                && gp.mIn.mouseY > screenY + solidArea.y 
                && gp.mIn.mouseY < screenY + solidArea.y + solidArea.height;
    }
}
