package src.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;
import src.main.KeyHandler;

/**
* A flower that starts the first wave when watered.
*
* - Player can interact with it (hover + interact key).
* - If the player has enough water, the flower is “watered”, drops hearts,
*   and (if the game hasn’t started) sets wave = 1.
* - Shows a floating water-can when recently watered, and a “hint” text until watered once.
*/
public class Flower2 extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    //BufferedImage[] frame = new BufferedImage[2];
    BufferedImage waterCan;

    int waterTimer; // Timer to keep the watered icon visible for a bit
    int waterTimerMax = 200;
    boolean hasBeenWatered = false;
    BufferedImage text;
    
    /**
     * Create a Flower2 at a world coordinates.
    */
    public Flower2(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);

        frameNumMax = 2; // Small 2-frame idle animation

        worldX = originX;
        worldY = originY;

        collisionOn = true;
        interactable = true;
        doesDamage = true;

        this.gp = gp;
        this.keyH = gp.keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        
        // solidArea = new Rectangle();
        // solidArea.x = 8;
        // solidArea.y = 16;
        // solidAreaDefaultX = solidArea.x;
        // solidAreaDefaultY = solidArea.y;
        // solidArea.width = 30;
        // solidArea.height = 30;

        worldXDouble = (double) worldX;
        worldYDouble = (double) worldY;

        try {
            // Base flower idle frames
            frame[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/flower2/Flower 2-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/flower2/Flower 2-2.png.png"));

            // Overlays
            waterCan = ImageIO.read(getClass().getResourceAsStream(
                "/res/GUI/watercan/WaterCan2-1.png.png"));
            text = ImageIO.read(getClass().getResourceAsStream(
                "/res/texts/Text1-1.png.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Per entity logic:
     * - If the player interacts and has enough water, consume it, drop hearts,
     *   and start wave 1 if not already started.
     * - Count down the “recently watered” timer.
    */
    public void updateSub() {

        // Interaction uses the base helper: cursor over + interact key
        if (interactedWith()) {
            if (gp.player.waterAmount >= 13) {
                gp.player.waterAmount = 0; // Spend all water (full can)
                hasBeenWatered = true;
                
                // Kick off the game if we’re still at 0
                if (gp.wave == 0) {
                    gp.wave = 1;
                }
                
                // Show the water can for a short while
                waterTimer = waterTimerMax;

                // Drop a bunch of hearts around the flower
                for (int i = 0; i < 8; i++) {
                    gp.dropSetter.dropHeart(gp, worldX, worldY, "heart");
                }

            }
            
        }

        if (waterTimer > 0) {
            waterTimer--;
        }

    }

    /**
     * Extra visuals drawn on top of the base entity sprite:
     * - A floating water can while the flower is recently watered.
     * - A hint text bubble until the flower has been watered once.
    */
    public void drawSub(Graphics2D g2) {
        // Soft bobbing so the icons feel alive
        int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle 
                + displacementAngleOffset)) * 6);

        if (waterTimer > 0) {
            image = waterCan;
            g2.drawImage(image, (int) screenX + 30, (int) screenY - 60 + displacementY, 
                gp.tileSize * 2, gp.tileSize * 2, null);
        }
        if (!hasBeenWatered) {
            image = text;
            g2.drawImage(image, (int) screenX + -170, (int) screenY - 120 + displacementY, 
                gp.tileSize * 4, gp.tileSize * 3, null);
        }
        
    }

}