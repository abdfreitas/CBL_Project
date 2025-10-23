package src.fx;

import src.entity.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class HealthBar {

    double worldXDouble;
    double worldYDouble;

    BufferedImage[] frame = new BufferedImage[6];

    public HealthBar() {

        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream("/res/fx/HealthBar/Health Bar-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream("/res/fx/HealthBar/Health Bar-2.png.png"));
            frame[2] = ImageIO.read(getClass().getResourceAsStream("/res/fx/HealthBar/Health Bar-3.png.png"));
            frame[3] = ImageIO.read(getClass().getResourceAsStream("/res/fx/HealthBar/Health Bar-4.png.png"));
            frame[4] = ImageIO.read(getClass().getResourceAsStream("/res/fx/HealthBar/Health Bar-5.png.png"));
            frame[5] = ImageIO.read(getClass().getResourceAsStream("/res/fx/HealthBar/Health Bar-6.png.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2, GamePanel gp ,Entity entity) {

        BufferedImage image = null;

        int i = entity.HP * 100 / entity.HPMax / 16;

        //System.out.println(i);

        if (i > 0 && i < 7 && entity.HP != entity.HPMax) {

            image = frame[6 - i];

            worldXDouble = entity.worldXDouble;
            worldYDouble = entity.worldYDouble;

            int worldX = (int) worldXDouble;
            int worldY = (int) worldYDouble;

            double screenX = worldX - gp.player.worldX + gp.player.screenX;
            double screenY = worldY - gp.player.worldY + gp.player.screenY - 12;

            g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);

        }

        


    }
    
}
