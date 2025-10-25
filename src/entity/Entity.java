package src.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import src.main.GamePanel;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public double worldXDouble;
    public double worldYDouble;

    public double speedDouble;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public String lookDirection;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    
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

    
    public void draw(Graphics2D g2) {
        // drawing logic for this entity
    }

    public void update(GamePanel gp) {
        // drawing logic for this entity
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

    
    
}
