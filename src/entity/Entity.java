package src.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;
import src.lib.Random;
import src.main.GamePanel;

/**
 * Base class for all entities in the world (player, monsters).
 *
 * Holds stuff like position, speed, sprites, collision box, HP,
 * and simple draw/update helpers.
 */
public class Entity {
    GamePanel gp;
    KeyHandler keyH;

    public int worldX;
    public int worldY;
    public int speed;
    public double worldXDouble;
    public double worldYDouble;
    public double speedDouble;
    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage down1; 
    public BufferedImage down2;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage right1;
    public BufferedImage right2;
    public String direction;
    public String lookDirection;
    
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean doesDamage = false;
    public int hpMax = 100;
    public int hp = hpMax;
    public int invincibleCounterMax = 30;
    public int invincibleCounter = 0;

    public double accelerationFactor;
    public boolean stunned = false;
    public int stunCounter = 0;
    public int stunCounterMax = 12;
    public int directionDamage = 0; // direction the hit came from (knockback)
    public int priority = 10;
    public boolean getHit = false; // Set true when an attack connects this frame
    public boolean invincible = false; 
    public boolean getDamage = false; // If this entity damages others
    public boolean interactable = false;

    BufferedImage[] frame = new BufferedImage[2];
    BufferedImage[] selectFrame = new BufferedImage[2];
    BufferedImage image = null;

    double screenX;
    double screenY;

    String name;
    int spriteCounter = 0;
    int spriteCounterMax = 23;
    int frameNum = 1;
    int frameNumMax = 2;
    int displacementAngle;
    int displacementAngleOffset;
    int imageScaleX = 1;
    int imageScaleY = 1;

    /**
     * Creates a new entity at a world position.
     *
     * @param gp reference to the GamePanel
     * @param originX starting x position in the world
     * @param originY starting y position in the world
     * @param name entity name 
    */    
    public Entity(GamePanel gp, int originX, int originY, String name) {
        this.gp = gp;
        this.name = name;
        displacementAngleOffset = Random.randomInt(0, 360);

        // Where the player is drawn on screen (camera center)
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        
        // Default collision box
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        worldX = originX;
        worldXDouble = originX;
        worldY = originY;
        worldYDouble = originY;
        
        // Selection square frames (used when hovered or interactable)
        try {
            selectFrame[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/fx/Select-1.png.png"));
            selectFrame[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/fx/Select-2.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setup() {

    }

    /** Draws the entity at its camera relative position. */
    public void draw(Graphics2D g2) {
        // Convert world to screen
        image = null;

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        spriteCounterAdd(); // 2 frame idle/walk loop

        image = frame[frameNum - 1]; // Choose current frame
        g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize * imageScaleX, 
            gp.tileSize * imageScaleY, null);

        drawSub(g2);

        // Draw hover square if cursor is over an interactable entity
        image = selectFrame[wrap(frameNum, 1, 2) - 1];
        if (underCursor() && interactable) {
            g2.drawImage(image, (int) screenX - gp.tileSize / 2, (int) screenY - gp.tileSize / 2, 
                gp.tileSize * 2, gp.tileSize * 2, null);
        }
        
        // Draw collision box when debug is on
        if (gp.keyH.debugEnabled) {
            g2.setColor(Color.RED);
            g2.drawRect((int) screenX + solidArea.x, (int) screenY + solidArea.y, solidArea.width, 
                solidArea.height);
            g2.setColor(Color.RED);
        }

    }

    public void drawSub(Graphics2D g2) {

    }
        
    /** Updates shared entity state, then calls updateSub() for subclass logic. */
    public void update() {
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        displacementAngle = displacementAngle + 2;
        updateSub();
    }

    public void updateSub() {

    }

    /** Creates a silhouette from a sprite. */
    static BufferedImage makeSilhouette(BufferedImage sprite, Color color) {
        BufferedImage out = new BufferedImage(sprite.getWidth(), sprite.getHeight(),
                                            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();

        g.setComposite(AlphaComposite.Src); // Replace dst
        g.setColor(color);
        g.fillRect(0, 0, out.getWidth(), out.getHeight());

        g.setComposite(AlphaComposite.DstIn); // Keep white only where sprite has alpha
        g.drawImage(sprite, 0, 0, null);

        g.dispose();
        return out;
    }

    /** Returns true if the player pressed interact while the cursor is over this entity. */
    public boolean interactedWith() {
        return gp.keyH.interactPressed && underCursor();
    }

    /** Checks if the mouse is currently inside the entity’s hitbox. */
    public boolean underCursor() {
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        return gp.mIn.mouseX > screenX + solidArea.x 
                && gp.mIn.mouseX < screenX + solidArea.x + solidArea.width
                && gp.mIn.mouseY > screenY + solidArea.y 
                && gp.mIn.mouseY < screenY + solidArea.y + solidArea.height;
    }

    /** Draws the selection square. */
    public void drawSelect(Graphics2D g2) {
        if (underCursor()) {
            g2.drawImage(selectFrame[0], (int) screenX, (int) screenY, gp.tileSize,
                gp.tileSize, null);
        }

    }

    /** Draws the 2 frame animation loop. */
    public void spriteCounterAdd() {
        spriteCounter++;
        if (spriteCounter > spriteCounterMax) {
            spriteCounter = 0;
            frameNum++;
            if (frameNum > frameNumMax) {
                frameNum = 1;
            }
        }
    }

    /** Wrap a value into [min, max] inclusive. */
    int wrap(int value, int min, int max) {
        int range = max - min + 1;
        return ((value - min) % range + range) % range + min;
    } 
    
}
