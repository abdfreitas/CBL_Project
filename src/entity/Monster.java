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
import src.user.ConfigManager;
import src.entity.Player;

public class Monster extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    private CharStack cStack;

    private Player player1;

    int hasKey = 0;

    

    

    


    public Monster(GamePanel gp, Player player) {

        
        doesDamage = true;
        player1 = player;

        HPMax = ConfigManager.getInt("monster.HPMax", 50);
        HP = HPMax;
        //System.out.println("Monster MAX HP set to: " + ConfigManager.getInt("monster.HPMax", 50));



        this.gp = gp;
        this.keyH = keyH;

        //this.cStack = keyH.cStack;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        //worldX = gp.tileSize * 25;
        //worldY = gp.tileSize * 21;

        speedDouble = 2;

        
        
    }

    public void update(GamePanel gp) {

        int index = 999;
        double distance = 0;
        double distanceTemp = 0;
        double dX = 0;
        double dY = 0;
        double dXTemp;
        double dYTemp;
        double distancePrioritized = 999999999;
        double distancePrioritizedTemp = 0;

        for (Entity entity : gp.friendlies) {
            if (entity != null) {

                

                dXTemp = entity.worldXDouble - worldXDouble;
                dYTemp = entity.worldYDouble - worldYDouble;

                distanceTemp = Math.sqrt((dXTemp * dXTemp) + (dYTemp * dYTemp));
                distancePrioritizedTemp = distanceTemp / entity.priority;
                if (distancePrioritizedTemp < distancePrioritized) {
                    distancePrioritized = distancePrioritizedTemp;
                    distance = distanceTemp;
                    dX = dXTemp;
                    dY = dYTemp;
                    index = gp.entities.indexOf(entity);;
                }

            }
        }


        // Getting hit:
        if (getHit && !invincible) {
            invincible = true;
            invincibleCounter = invincibleCounterMax;

            getDamage = true;
            HP -= 10;

            //System.out.println("Monster HP: " + HP);

            gp.playSE(6);


        }

        if (invincibleCounter <= 0) {
            invincible = false;
            getHit = false;
        } else {
            invincibleCounter--;
        }

        // int playerX = player1.worldX;
        // int playerY = player1.worldY;

        // double dX = playerX - worldXDouble;
        // double dY = playerY - worldYDouble;

        // double distance = Math.sqrt((dX * dX) + (dY * dY));

        double speedX;
        double speedY;

        if (distance > 25) {
            speedX = (speedDouble * dX / distance) + (speedDouble * dX / 100);
            speedY = (speedDouble * dY / distance) + (speedDouble * dY / 100);

        } else {
            speedX = 0;
            speedY = 0;

        }

        

        

        while (true) {
            break;
        }



        worldXDouble += speedX;
        worldYDouble += speedY;

        

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        double screenX = worldXDouble - gp.player.worldX + gp.player.screenX;
        double screenY = worldYDouble - gp.player.worldY + gp.player.screenY;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entities/monster/ghost.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);

        if (keyH.debugEnabled) {
            g2.setColor(Color.RED);
            g2.drawRect((int) screenX + solidArea.x, (int) screenY + solidArea.y, solidArea.width, solidArea.height);
            g2.setColor(Color.RED);
        }

    }

    
}
