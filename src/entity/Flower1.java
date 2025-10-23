package src.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.lib.CharStack;
import src.main.GamePanel;
import src.main.KeyHandler;

public class Flower1 extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int spriteCounter = 0;
    int spriteCounterMax = 23;
    int frameNum = 1;
    int frameNumMax = 2;

    BufferedImage[] frame = new BufferedImage[2];

    


    public Flower1(GamePanel gp, Player player) {

        collisionOn = true;

        this.gp = gp;
        this.keyH = keyH;

        doesDamage = true;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        worldXDouble = (double) worldX;
        worldYDouble = (double) worldY;

        

        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream("/res/entities/flower1/Flower 1-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream("/res/entities/flower1/Flower 1-2.png.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {


        BufferedImage image = null;

        double screenX = worldX - gp.player.worldX + gp.player.screenX;
        double screenY = worldY - gp.player.worldY + gp.player.screenY;

        spriteCounter++;
        if (spriteCounter > spriteCounterMax) {
            spriteCounter = 0;
            frameNum++;
            if (frameNum > frameNumMax) {
                frameNum = 1;
            }
        }

        image = frame[frameNum - 1];

        g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);

        if (keyH.debugEnabled) {
            g2.setColor(Color.RED);
            g2.drawRect((int) screenX + solidArea.x, (int) screenY + solidArea.y, solidArea.width, solidArea.height);
            g2.setColor(Color.RED);
        }

    }

}