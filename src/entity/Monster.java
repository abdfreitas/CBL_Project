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

    BufferedImage[] frame = new BufferedImage[4];

    
    int spriteCounter = 0;
    int spriteCounterMax = 23;
    int frameNum = 1;
    int frameNumMax = 2;

    int displacementAngle = 0;
    int displacementAngleOffset;

    

    

    


    public Monster(GamePanel gp, Player player) {

        
        doesDamage = true;
        player1 = player;

        HPMax = ConfigManager.getInt("monster.HPMax", 50);
        HP = HPMax;
        //System.out.println("Monster MAX HP set to: " + ConfigManager.getInt("monster.HPMax", 50));
        invincibleCounterMax = ConfigManager.getInt("monster.invincibleCounterMax", 20);

        stunCounterMax = ConfigManager.getInt("monster.stunCounterMax", 20);

        displacementAngleOffset = (int) (Math.random() * 360);
        



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

        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream("/res/entities/monster/ghost.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream("/res/entities/monster/ghost-2.png"));
            frame[2] = ImageIO.read(getClass().getResourceAsStream("/res/entities/monster/ghost-3.png"));
            frame[3] = ImageIO.read(getClass().getResourceAsStream("/res/entities/monster/ghost-4.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        
        
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
            stunCounter = stunCounterMax;
            stunned = true;

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

        if (stunCounter <= 0) {
            stunned = false;
           
        } else {
            stunCounter--;
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

        if (stunned) {
            speedX = speedX / 4;
            speedY = speedY / 4;
        }



        worldXDouble += speedX;
        worldYDouble += speedY;

        displacementAngle = displacementAngle + 2;

        if (Math.abs(dX) > Math.abs(dY)) {
            if (dX > 0) {
                lookDirection = "right";
            } else {
                lookDirection = "left";
            }
        } else {
            // if (dY > 0) {
            //     lookDirection = "down";
            // } else {
            //     lookDirection = "up";
            // }
        }

        worldX = (int) worldXDouble;
        worldY = (int) worldYDouble;

        

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        double screenX = worldXDouble - gp.player.worldX + gp.player.screenX;
        double screenY = worldYDouble - gp.player.worldY + gp.player.screenY;

        spriteCounter++;
        if (spriteCounter > spriteCounterMax) {
            spriteCounter = 0;
            frameNum++;
            if (frameNum > frameNumMax) {
                frameNum = 1;
            }
        }

        int frameNumDirection;

        if (lookDirection == "right") {
            frameNumDirection = frameNum + 2;
        } else {
            frameNumDirection = frameNum;
        }

        image = frame[frameNumDirection - 1];

        int displacementX = (int) Math.cos(Math.toRadians(displacementAngle));
        int displacementY = (int) (Math.sin(Math.toRadians(displacementAngle 
            + displacementAngleOffset)) * 10);

        if (invincible && invincibleCounter / 2 / 2 % 2 != 0) {
            BufferedImage whitePlayer = makeSilhouette(image, Color.WHITE);
            g2.drawImage(whitePlayer, (int) screenX, (int) screenY + displacementY, gp.tileSize, gp.tileSize, null);
        } else {
            g2.drawImage(image, (int) screenX, (int) screenY + displacementY, gp.tileSize, gp.tileSize, null);
        }

        //g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);

        if (keyH.debugEnabled) {
            g2.setColor(Color.RED);
            g2.drawRect((int) screenX + solidArea.x, (int) screenY + solidArea.y, solidArea.width, solidArea.height);
            g2.setColor(Color.RED);
        }

    }

    
}
