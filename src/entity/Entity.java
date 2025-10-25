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

public class Entity {

    GamePanel gp;
    KeyHandler keyH;

    public int worldX, worldY;
    public int speed;

    public double worldXDouble;
    public double worldYDouble;

    public double speedDouble;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public String lookDirection;

    // public int spriteCounter = 0;
    // public int spriteNum = 1;
    
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    
    public boolean collisionOn = false;

    public boolean doesDamage = false;
    public int HPMax = 100;
    public int HP = HPMax;

    public int invincibleCounterMax = 30;

    public int invincibleCounter = 0;

    public double accelerationFactor;


    public boolean stunned = false;
    public int stunCounter = 0;
    public int stunCounterMax = 12;

    public int directionDamage = 0;

    public int priority = 10;

    public boolean getHit = false;

    public boolean invincible = false;

    public boolean getDamage = false;

    public boolean interactable = false;

    BufferedImage[] frame = new BufferedImage[2];

    BufferedImage[] selectFrame = new BufferedImage[2];

    double screenX;
    double screenY;

    int spriteCounter = 0;
    int spriteCounterMax = 23;
    int frameNum = 1;
    int frameNumMax = 2;

    BufferedImage image = null;

    String name;

    int displacementAngle;
    int displacementAngleOffset;

    public Entity(GamePanel gp, int originX, int originY, String name) {

        this.gp = gp;
        this.name = name;
        //keyH = gp.keyH;
        displacementAngleOffset = Random.randomInt(0, 360);

        

        worldX = originX;
        worldXDouble = originX;
        worldY = originY;
        worldYDouble = originY;

         try {
            selectFrame[0] = ImageIO.read(getClass().getResourceAsStream("/res/fx/Select-1.png.png"));
            selectFrame[1] = ImageIO.read(getClass().getResourceAsStream("/res/fx/Select-2.png.png"));

            
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }

    public void setup() {

    }

    
    public void draw(Graphics2D g2) {
        // drawing logic for this entity
        image = null;

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        spriteCounterAdd();

        image = frame[frameNum - 1];

        g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);

        drawSub(g2);

        image = selectFrame[wrap(frameNum, 1, 2) - 1];

        if (underCursor() && interactable) {
            g2.drawImage(image, (int) screenX - gp.tileSize / 2, (int) screenY - gp.tileSize / 2, gp.tileSize * 2, gp.tileSize * 2, null);
        }
        
        if (gp.keyH.debugEnabled) {
            g2.setColor(Color.RED);
            g2.drawRect((int) screenX + solidArea.x, (int) screenY + solidArea.y, solidArea.width, solidArea.height);
            g2.setColor(Color.RED);
        }

    }

    public void drawSub(Graphics2D g2) {

    }
        
    

    public void update() {
        // drawing logic for this entity

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        displacementAngle = displacementAngle + 2;

        updateSub();
    }

    public void updateSub() {

    }

    static BufferedImage makeSilhouette(BufferedImage sprite, Color color) {
        BufferedImage out = new BufferedImage(sprite.getWidth(), sprite.getHeight(),
                                            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();

        g.setComposite(AlphaComposite.Src);   // replace dst
        g.setColor(color);
        g.fillRect(0, 0, out.getWidth(), out.getHeight());

        g.setComposite(AlphaComposite.DstIn); // keep white only where sprite has alpha
        g.drawImage(sprite, 0, 0, null);

        g.dispose();
        return out;
    }

    

    public boolean interactedWith() {
        if (gp.keyH.interactPressed && underCursor()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean underCursor() {

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (gp.mIn.mouseX > screenX + solidArea.x 
                && gp.mIn.mouseX < screenX + solidArea.x + solidArea.width
                && gp.mIn.mouseY > screenY + solidArea.y 
                && gp.mIn.mouseY < screenY + solidArea.y + solidArea.height) {

            //System.out.println("under");

            return true;
        } else { 
            return false;
        }
    }

    public void drawSelect(Graphics2D g2) {

        if (underCursor()) {
            g2.drawImage(selectFrame[0], (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);
        }

    }

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

    int wrap(int value, int min, int max) {
        int range = max - min + 1;
        return ((value - min) % range + range) % range + min;
    }

    
    
}
