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
import src.lib.Random;

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

    double acceleration;
    double accelerationFactorRandomOffset;
    double decelerationFactor = 0.01;
    double deceleration;

    double distanceAngle;
    public double speedDouble;
    public double speedAngle;
    double speedX;
    double speedY;
    double speedMax = 5;

    

    

    


    public Monster(GamePanel gp, int originX, int originY, String name) {

        super(gp, originX, originY, name);

        //interactable = true;

        speedX = 0;
        speedY = 0;
        speedDouble = 0;

        accelerationFactorRandomOffset = Random.randomDouble(-0.5, 0.5);
        //System.out.println(accelerationFactorRandomOffset);
        

        
        doesDamage = true;
        player1 = gp.player;

        HPMax = ConfigManager.getInt("monster.HPMax", 50);
        HP = HPMax;
        //System.out.println("Monster MAX HP set to: " + ConfigManager.getInt("monster.HPMax", 50));
        invincibleCounterMax = ConfigManager.getInt("monster.invincibleCounterMax", 20);

        stunCounterMax = ConfigManager.getInt("monster.stunCounterMax", 20);

        
        



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

        speedDouble = 0;

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

    public void updateSub() {

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
                    distanceAngle = Math.atan2(dYTemp, dXTemp);
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

            gp.sound.playSfx(6);

            speedX = speedX - 30 * Math.cos(Math.toRadians(directionDamage));
            speedY = speedY - 30 * Math.sin(Math.toRadians(directionDamage));

            // System.out.println("dspeedX: " + 20 * Math.cos(Math.toRadians(directionDamage)) + " dspeedY: " + 20 * Math.sin(Math.toRadians(directionDamage)) + " directionDamage: " + directionDamage);


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

        
        speedDouble = Math.sqrt(speedX * speedX + speedY * speedY);
        speedAngle = Math.atan2(speedY, speedX);

        acceleration = accelerationFactor + accelerationFactorRandomOffset * accelerationFactor;

        //System.out.println(acceleration);

        if (distance > 25) {
            speedX = speedX + (acceleration * Math.cos(distanceAngle));
            speedY = speedY + (acceleration * Math.sin(distanceAngle));

            

        } else {
            // speedX = 0;
            // speedY = 0;

        }

        speedDouble = Math.sqrt(speedX * speedX + speedY * speedY);
        speedAngle = Math.atan2(speedY, speedX);

        deceleration = decelerationFactor * speedDouble * speedDouble;

        if (speedDouble > 0.01) {
            speedX = speedX - (deceleration * Math.cos(speedAngle));
            speedY = speedY - (deceleration * Math.sin(speedAngle));

        } else if (speedDouble < 0.01) {
            speedX = speedX + (deceleration * Math.cos(speedAngle));
            speedY = speedY + (deceleration * Math.sin(speedAngle));
        } else {
            speedX = 0;
            speedY = 0;
        }

        speedDouble = Math.sqrt(speedX * speedX + speedY * speedY);
        speedAngle = Math.atan2(speedY, speedX);

        //System.out.println("Speed: " + speedDouble + " Acceleration: " + acceleration + " Deceleration: " + deceleration); 
        

        

        while (true) {
            break;
        }

        if (stunned) {
            worldXDouble += speedX / 2;
            worldYDouble += speedY / 2;
        } else {
            worldXDouble += speedX;
            worldYDouble += speedY;
        }



        

        

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
