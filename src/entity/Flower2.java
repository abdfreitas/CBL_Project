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

public class Flower2 extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    //public final int screenX;
    //public final int screenY;

    

    //BufferedImage[] frame = new BufferedImage[2];
    BufferedImage waterCan;

    int waterTimer;
    int waterTimerMax = 200;

    

    boolean hasBeenWatered = false;

    BufferedImage text;
    

    


    public Flower2(GamePanel gp, int originX, int originY, String name) {

        

        super(gp, originX, originY, name);

        frameNumMax = 2;

        worldX = originX;
        worldY = originY;

        collisionOn = true;

        interactable = true;

        this.gp = gp;
        this.keyH = gp.keyH;

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
            frame[0] = ImageIO.read(getClass().getResourceAsStream("/res/entities/flower2/Flower 2-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream("/res/entities/flower2/Flower 2-2.png.png"));

            waterCan = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-1.png.png"));
            text = ImageIO.read(getClass().getResourceAsStream("/res/texts/Text1-1.png.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    

    public void updateSub() {

        if (interactedWith()) {
            //System.out.println("MB2 clicked");

            

            if (gp.player.waterAmount >= 13) {
                gp.player.waterAmount = 0;
                hasBeenWatered = true;

                waterTimer = waterTimerMax;
                for (int i = 0; i < 8; i++) {
                    gp.dropSetter.dropHeart(gp, worldX, worldY, "heart");
                }

            }

            
        }

        if (waterTimer > 0) {
            waterTimer--;
        }



    }

    public void drawSub(Graphics2D g2) {

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