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

public class Wizard extends Entity{

    public Wizard(GamePanel gp, int originX, int originY, String name) {

        super(gp, originX, originY, name);

        frame = new BufferedImage[8];

        interactable = true;

        collisionOn = true;

        frameNumMax = 8;
        spriteCounterMax = 12;
        imageScaleX = 1;
        imageScaleY = 2;
        solidArea.height = 64;

        doesDamage = true;

        

        

        try {

            for (int i = 0; i < 8; i++) {
                String path = "/res/entities/wizard/WizardNew-" + (i + 1) + ".png.png";
                frame[i] = ImageIO.read(getClass().getResourceAsStream(path));
            }
            
            
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    

}