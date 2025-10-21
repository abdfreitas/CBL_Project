package src.entity;

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

    public int spriteCounter = 0;
    public int spriteNum = 1;
    
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    
    public boolean collisionOn = false;

    public boolean doesDamage = false;

    public int HP = 100;

    public int invincibleCounterMax = 30;

    public int directionDamage = 0;

    
    public void draw(Graphics2D g2) {
        // drawing logic for this entity
    }

    public void update() {
        // drawing logic for this entity
    }

    
    
}
