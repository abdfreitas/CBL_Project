package src.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.lib.Random;
import src.main.GamePanel;
import src.main.KeyHandler;
import src.user.ConfigManager;

/**
*  Monster entity  for the basic enemy type in the game.
*
* It automatically moves toward the closest "friendly" (player or plants),
* slightly randomized with acceleration and deceleration. It can be stunned,
* take damage, and briefly turn white (invincible flicker) when hit.
*/
public class Monster extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int hasKey = 0;
    BufferedImage[] frame = new BufferedImage[4];

    int spriteCounter = 0;
    int spriteCounterMax = 23;
    int frameNum = 1;
    int frameNumMax = 2;

    double acceleration;
    double accelerationFactorRandomOffset;
    double decelerationFactor = 0.01;
    double deceleration;

    double distanceAngle;
    public double speedDouble;
    public double speedAngle;
    double speedX;
    double speedY;
    double speedMax = 5;

    /**
     * Creates a monster at the given world position.
    */
    public Monster(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);

        speedX = 0;
        speedY = 0;
        speedDouble = 0;
        // Initialize random speed offset so monsters don’t move identically
        accelerationFactorRandomOffset = Random.randomDouble(-0.5, 0.5);
        doesDamage = true;

        // Stats are loaded from config.txt
        hpMax = ConfigManager.getInt("monster.hpMax", 50);
        hp = hpMax;
        invincibleCounterMax = ConfigManager.getInt("monster.invincibleCounterMax", 20);
        stunCounterMax = ConfigManager.getInt("monster.stunCounterMax", 20);

        this.gp = gp;
        
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Define monster’s collision box
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;
        speedDouble = 0;

        // Load sprites
        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/monster/ghost.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/monster/ghost-2.png"));
            frame[2] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/monster/ghost-3.png"));
            frame[3] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/monster/ghost-4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles all movement and interaction.
     * The monster:
     * - Moves toward the nearest friendly entity
     * - Gets stunned and repelled when hit
     * - Flickers while invincible
     */
    public void updateSub() {
        int index = 999; // Closest entity index
        double distance = 0;
        double distanceTemp = 0;
        double dX = 0;
        double dY = 0;
        double dXTemp;
        double dYTemp;
        double distancePrioritized = 999999999; // Distance weighted by entity priority
        double distancePrioritizedTemp = 0;

        // Find the closest friendly (player or plant)
        for (Entity entity : gp.friendlies) {
            if (entity != null) {
                dXTemp = entity.worldXDouble - worldXDouble;
                dYTemp = entity.worldYDouble - worldYDouble;

                distanceTemp = Math.sqrt((dXTemp * dXTemp) + (dYTemp * dYTemp));
                distancePrioritizedTemp = distanceTemp / entity.priority;

                // Pick the closest high priority target
                if (distancePrioritizedTemp < distancePrioritized) {
                    distancePrioritized = distancePrioritizedTemp;
                    distance = distanceTemp;
                    dX = dXTemp;
                    dY = dYTemp;
                    index = gp.entities.indexOf(entity);;
                    distanceAngle = Math.atan2(dYTemp, dXTemp);
                }

            }
        }

        // Getting hit:
        if (getHit && !invincible) {
            invincible = true;
            invincibleCounter = invincibleCounterMax;
            stunCounter = stunCounterMax;
            stunned = true;

            getDamage = true;
            hp -= 10;
            gp.sound.playSfx(6);

            // Knockback in the direction opposite to the attack
            speedX = speedX - 30 * Math.cos(Math.toRadians(directionDamage));
            speedY = speedY - 30 * Math.sin(Math.toRadians(directionDamage));
        }

        // Handle invincibility flicker
        if (invincibleCounter <= 0) {
            invincible = false;
            getHit = false;
        } else {
            invincibleCounter--;
        }

        // Handle stun duration
        if (stunCounter <= 0) {
            stunned = false;
           
        } else {
            stunCounter--;
        }

        speedDouble = Math.sqrt(speedX * speedX + speedY * speedY);
        speedAngle = Math.atan2(speedY, speedX);

        // Speed variation per monster
        acceleration = accelerationFactor + accelerationFactorRandomOffset * accelerationFactor;

        // Move toward target unless very close
        if (distance > 25) {
            speedX = speedX + (acceleration * Math.cos(distanceAngle));
            speedY = speedY + (acceleration * Math.sin(distanceAngle));
        } 

        // Apply drag (to make motion smoother)
        speedDouble = Math.sqrt(speedX * speedX + speedY * speedY);
        speedAngle = Math.atan2(speedY, speedX);
        deceleration = decelerationFactor * speedDouble * speedDouble;

        // Apply movement 
        if (speedDouble > 0.01) {
            speedX = speedX - (deceleration * Math.cos(speedAngle));
            speedY = speedY - (deceleration * Math.sin(speedAngle));

        } else if (speedDouble < 0.01) {
            speedX = speedX + (deceleration * Math.cos(speedAngle));
            speedY = speedY + (deceleration * Math.sin(speedAngle));
        } else {
            speedX = 0;
            speedY = 0;
        }

        speedDouble = Math.sqrt(speedX * speedX + speedY * speedY);
        speedAngle = Math.atan2(speedY, speedX);

        while (true) {
            break;
        }

        if (stunned) {
            worldXDouble += speedX / 2;
            worldYDouble += speedY / 2;
        } else {
            worldXDouble += speedX;
            worldYDouble += speedY;
        }

        // Update direction for correct sprite
        if (Math.abs(dX) > Math.abs(dY)) {
            if (dX > 0) {
                lookDirection = "right";
            } else {
                lookDirection = "left";
            }
        }

        worldX = (int) worldXDouble;
        worldY = (int) worldYDouble;
    }

    /**
     * Draw the monster sprite and apply visual effects. 
     * - Flicker white while invincible
     * - Bounce slightly (for animation)
    */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        double screenX = worldXDouble - gp.player.worldX + gp.player.screenX;
        double screenY = worldYDouble - gp.player.worldY + gp.player.screenY;

        // Animation timing
        spriteCounter++;
        if (spriteCounter > spriteCounterMax) {
            spriteCounter = 0;
            frameNum++;
            if (frameNum > frameNumMax) {
                frameNum = 1;
            }
        }

        // Use right or left frame depending on direction
        int frameNumDirection;
        if (lookDirection == "right") {
            frameNumDirection = frameNum + 2;
        } else {
            frameNumDirection = frameNum;
        }

        image = frame[frameNumDirection - 1];

        // Floating animation
        int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle) 
            + displacementAngleOffset) * 10);

        // Flicker to white if invincible
        if (invincible && invincibleCounter / 2 / 2 % 2 != 0) {
            BufferedImage whitePlayer = makeSilhouette(image, Color.WHITE);
            g2.drawImage(whitePlayer, (int) screenX, (int) screenY + displacementY, gp.tileSize, 
                gp.tileSize, null);
        } else {
            g2.drawImage(image, (int) screenX, (int) screenY + displacementY, gp.tileSize, 
                gp.tileSize, null);
        }

        // Draw collision box when debug is on
        if (keyH.debugEnabled) {
            g2.setColor(Color.RED);
            g2.drawRect((int) screenX + solidArea.x, (int) screenY + solidArea.y, 
                solidArea.width, solidArea.height);
            g2.setColor(Color.RED);
        }

    }

}
