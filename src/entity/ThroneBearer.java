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

public class ThroneBearer extends Entity{

    public ThroneBearer(GamePanel gp, int originX, int originY, String name) {

        super(gp, originX, originY, name);

        interactable = false;

        collisionOn = true;

        frameNumMax = 2;

        imageScaleX = 5;
        imageScaleY = 4;
        solidArea.x = 8;
        solidArea.y = 80;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 60 * 3;
        solidArea.height = 29 * 3;

      

        doesDamage = true;

        

        

        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream("/res/entities/ThroneBearer/The Thronebearer-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream("/res/entities/ThroneBearer/The Thronebearer-2.png.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    

}