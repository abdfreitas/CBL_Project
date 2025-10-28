package src.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/**
 * Display for the game.
 *
 * Loads tutorial sprites (WASD, SPACE, MOUSE),
 * shows coins/wave info, and draws a simple “night” overlay.
 */
public class GUI {

    // Sprite frames used for UI animations
    BufferedImage[] wasd = new BufferedImage[2];
    BufferedImage[] space = new BufferedImage[2];
    BufferedImage[] mouse = new BufferedImage[2];
    BufferedImage[] wateringCan = new BufferedImage[14];
    BufferedImage move;
    BufferedImage attack;
    BufferedImage dodge; 

    // Static labels/icons
    int spriteCounter = 0;
    int spriteCounterMax = 23;
    int frameNum = 1;
    int frameNumMax = 2;
    int displacementAngle = 0;
    int displacementAngleOffset = 60;

    public int coins;

    /** Load all UI images from res. */
    public GUI() {
        try {
            // WASD + MOVE sprite
            wasd[0] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/WASD-1.png.png"));
            wasd[1] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/WASD-2.png.png"));
            move = ImageIO.read(getClass().getResourceAsStream("/res/GUI/MOVE-1.png.png"));
            
            // SPACE + DODGE sprite
            space[0] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/SPACE-1.png"));
            space[1] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/SPACE-2.png"));
            dodge = ImageIO.read(getClass().getResourceAsStream("/res/GUI/DODGE.png"));
            
            // MOUSE + ATTACK sprite
            mouse[0] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/MOUSE-1.png"));
            mouse[1] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/MOUSE-2.png"));
            attack = ImageIO.read(getClass().getResourceAsStream("/res/GUI/ATTACK.png"));

            // Water fill levels (14 frames)
            wateringCan[0]  = ImageIO.read(getClass().getResourceAsStream(
                    "/res/GUI/watercan/WaterCan2-1.png.png"));

            wateringCan[1]  = ImageIO.read(getClass().getResourceAsStream(
                    "/res/GUI/watercan/WaterCan2-2.png.png"));

            wateringCan[2]  = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-3.png.png"));
            
            wateringCan[3]  = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-4.png.png"));
            
            wateringCan[4]  = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-5.png.png"));
            
            wateringCan[5]  = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-6.png.png"));
           
            wateringCan[6]  = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-7.png.png"));
            
            wateringCan[7]  = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-8.png.png"));
            
            wateringCan[8]  = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-9.png.png"));
            
            wateringCan[9]  = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-10.png.png"));
            
            wateringCan[10] = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-11.png.png"));
            
            wateringCan[11] = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-12.png.png"));
           
            wateringCan[12] = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-13.png.png"));
           
            wateringCan[13] = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-14.png.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw the elements on top of the game.
     *
     * @param g2 the graphics context we draw with
     * @param gp the game panel for tileSize and wave/player info
    */
    public void draw(Graphics2D g2, GamePanel gp) {
        // Night settings:
        if (gp.wave > 0) {
            g2.setColor(new Color(30, 20, 70, 50));
            g2.fillRect(0, 0, 800, 600);
        }
        
        // 2 frame animation loop for the sprites
        spriteCounter++;
        if (spriteCounter > spriteCounterMax) {
            spriteCounter = 0;
            frameNum++;
            if (frameNum > frameNumMax) {
                frameNum = 1;
            }
        }

        // int debugOffsetX = 20;
        // int debugOffsetY = 20;

        displacementAngle = displacementAngle + 2;
        int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle)) * 10);

        int displacementY2 = (int) (Math.sin(Math.toRadians(displacementAngle 
            + displacementAngleOffset)) * 10);

        displacementY = 0;
        displacementY2 = 0;

        // MOVE
        g2.drawImage(wasd[frameNum - 1], (int) 20, (int) 450 + displacementY, 
            gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(move, (int) 120, (int) 490 + displacementY, gp.tileSize * 2, 
            gp.tileSize * 2, null);

        // DODGE
        g2.drawImage(space[frameNum - 1], (int) 270, (int) 445 + displacementY2, 
            gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(dodge, (int) 370, (int) 490 + displacementY2, gp.tileSize * 4, 
            gp.tileSize * 2, null);

        // AIM
        g2.drawImage(mouse[frameNum - 1], (int) 520, (int) 445 + displacementY2, 
            gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(attack, (int) 600, (int) 490 + displacementY2, gp.tileSize * 4, 
            gp.tileSize * 2, null);

        // Simple counters
        g2.drawString("Coins: " + coins, 500, 100);
        g2.drawString("Wave: " + gp.wave, 500, 80);

        // Map waterAmount (0..13) to frame index (13..0)
        int waterFrameNum = 13 - gp.player.waterAmount;
        if (waterFrameNum < 0) {
            waterFrameNum = 0; // Clamp so we don’t index negative
        }

        g2.drawImage(wateringCan[waterFrameNum], 600, 50, gp.tileSize * 2, gp.tileSize * 2, null);
    }
}
