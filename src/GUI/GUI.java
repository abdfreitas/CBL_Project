package src.GUI;

import src.main.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GUI {

    BufferedImage[] wasd = new BufferedImage[2];
    BufferedImage[] space = new BufferedImage[2];
    BufferedImage[] mouse = new BufferedImage[2];
    BufferedImage[] wateringCan = new BufferedImage[14];
    BufferedImage move, attack, aim; 
    //GamePanel gp;

    int spriteCounter = 0;
    int spriteCounterMax = 23;
    int frameNum = 1;
    int frameNumMax = 2;


    int displacementAngle = 0;
    int displacementAngleOffset = 60;

    public int coins;

    public GUI() {


        try {
            wasd[0] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/WASD-1.png.png"));
            wasd[1] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/WASD-2.png.png"));
            move = ImageIO.read(getClass().getResourceAsStream("/res/GUI/MOVE-1.png.png"));
            space[0] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/SPACE-1.png"));
            space[1] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/SPACE-2.png"));
            attack = ImageIO.read(getClass().getResourceAsStream("/res/GUI/ATTACK.png"));
            mouse[0] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/MOUSE-1.png"));
            mouse[1] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/MOUSE-2.png"));
            aim = ImageIO.read(getClass().getResourceAsStream("/res/GUI/AIM.png"));

            wateringCan[0]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-1.png.png"));
            wateringCan[1]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-2.png.png"));
            wateringCan[2]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-3.png.png"));
            wateringCan[3]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-4.png.png"));
            wateringCan[4]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-5.png.png"));
            wateringCan[5]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-6.png.png"));
            wateringCan[6]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-7.png.png"));
            wateringCan[7]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-8.png.png"));
            wateringCan[8]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-9.png.png"));
            wateringCan[9]  = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-10.png.png"));
            wateringCan[10] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-11.png.png"));
            wateringCan[11] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-12.png.png"));
            wateringCan[12] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-13.png.png"));
            wateringCan[13] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/watercan/WaterCan2-14.png.png"));

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    
    }
    public void draw(Graphics2D g2, GamePanel gp) {

        spriteCounter++;
        if (spriteCounter > spriteCounterMax) {
            spriteCounter = 0;
            frameNum++;
            if (frameNum > frameNumMax) {
                frameNum = 1;
            }
        }

        int debugOffsetX = 20;
        int debugOffsetY = 20;

        displacementAngle = displacementAngle + 2;
        int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle)) * 10);

        int displacementY2 = (int) (Math.sin(Math.toRadians(displacementAngle 
            + displacementAngleOffset)) * 10);

        displacementY = 0;
        displacementY2 = 0;

        

        // MOVE
        g2.drawImage(wasd[frameNum - 1], (int) 20 , (int) 450 + displacementY, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(move, (int) 120, (int) 490 + displacementY, gp.tileSize * 2, gp.tileSize * 2, null);

        // ATTACK
        g2.drawImage(space[frameNum - 1], (int) 250, (int) 445 + displacementY2, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(attack, (int) 350, (int) 490 + displacementY2, gp.tileSize * 4, gp.tileSize * 2, null);

        // AIM
        g2.drawImage(mouse[frameNum - 1], (int) 520, (int) 445 + displacementY2, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(aim, (int) 600, (int) 490 + displacementY2, gp.tileSize * 4, gp.tileSize * 2, null);

        g2.drawString("Coins: " + coins, 500, 100);

        //g2.drawString("DEBUG MODE ON", debugOffsetX, debugOffsetY);

        //debugOffsetY += debugLineSpace;
        //g2.drawString("Player speed: " + player.speed, debugOffsetX, debugOffsetY);

        int waterFrameNum = 13 - gp.player.waterAmount;

        if (waterFrameNum < 0) {
            waterFrameNum = 0;
        }

        g2.drawImage(wateringCan[waterFrameNum], 600, 50, gp.tileSize * 2, gp.tileSize * 2, null);

    }



    
    
}
