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
    BufferedImage move, attack, space; 
    //GamePanel gp;

    int spriteCounter = 0;
    int spriteCounterMax = 23;
    int frameNum = 1;
    int frameNumMax = 2;

    public GUI() {



        

        try {
            wasd[0] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/WASD-1.png.png"));
            wasd[1] = ImageIO.read(getClass().getResourceAsStream("/res/GUI/WASD-2.png.png"));
            move = ImageIO.read(getClass().getResourceAsStream("/res/GUI/MOVE-1.png.png"));
            space = ImageIO.read(getClass().getResourceAsStream("/res/GUI/SPACE.png"));
            attack = ImageIO.read(getClass().getResourceAsStream("/res/GUI/ATTACK.png"));
            
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

        

        g2.drawImage(wasd[frameNum - 1], (int) 20, (int) 450, gp.tileSize * 2, gp.tileSize * 2, null);

        g2.drawImage(move, (int) 120, (int) 490, gp.tileSize * 2, gp.tileSize * 2, null);

        g2.drawImage(space, (int) 250, (int) 445, gp.tileSize * 2, gp.tileSize * 2, null);

        g2.drawImage(attack, (int) 350, (int) 490, gp.tileSize * 4, gp.tileSize * 2, null);

        //g2.drawString("DEBUG MODE ON", debugOffsetX, debugOffsetY);

        //debugOffsetY += debugLineSpace;
        //g2.drawString("Player speed: " + player.speed, debugOffsetX, debugOffsetY);

    }



    
    
}
