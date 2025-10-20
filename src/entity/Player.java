package src.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.main.GamePanel;
import src.main.KeyHandler;

import src.lib.CharStack;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    private CharStack cStack;

    int hasKey = 0;

    public int speedMax;
    //public int speed;
    public double acceleration = 0.5;
    public double speedDouble;

    private boolean shiftWasPressed;
    private boolean shiftWasReleased;


    public Player (GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        this.cStack = keyH.cStack;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 9;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        setDefaultValues();
        getPlayerImage();

        
        
  

    }

    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_walking/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_walking/boy_up_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_walking/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_walking/boy_left_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_walking/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_walking/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_walking/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_walking/boy_right_2.png"));
            
            
            
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 1;
        speedMax = 4;
        direction = "down";
    }

    public void update() {

        

        if (keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed) {

            if (keyH.shiftPressed && !shiftWasPressed) {

                shiftWasPressed = true;
                //shiftWasReleased = false;
                speedMax += 2;
            }

            if (!keyH.shiftPressed && shiftWasPressed) {

                shiftWasPressed = false;
                speedMax -= 2;
            }

            if (speedDouble < speedMax) {
                speedDouble += acceleration;
                speed = (int) speedDouble;
                
            } else {
                speed = speedMax;
            
            }

            //System.out.println(speed);

            char key = cStack.stack[0];

            switch (key) {
                case 'w': 
                    direction = "up";
                    break;
                case 'a': 
                    direction = "left";
                    break;
                case 's': 
                    direction = "down";
                    break;
                case 'd': 
                    direction = "right";
                    break;
            
                default:
                    break;
            }

            /*
            if (keyH.upPressed == true) {
                
                direction = "up";
                //System.out.println("Up pressed");
            }
            else if (keyH.leftPressed == true) {
                
                direction = "left";
            }
            else if (keyH.downPressed == true) {
                
                direction = "down";
            }
            else if (keyH.rightPressed == true) {
                
                direction = "right";
            }
                */


            // Check tile collision
            collisionOn = false;
            gp.collisionDetection.CheckTile(this);

            // Check object collision
            int objIndex = gp.collisionDetection.checkObject(this, true);
            pickUpObject(objIndex);

            // If collision is false, player can move
            if (!collisionOn) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;

                    case "down":
                        worldY += speed;
                        break;

                    case "left":
                        worldX -= speed;
                        break;

                    case "right":
                        worldX += speed;
                        break;
                
                    default:
                        break;
                }
            } else {
                if (speedDouble > 1) {
                    speedDouble -= 2 * acceleration;
                    
                } 
                if (speedDouble < 1) {
                    speedDouble = 1;
                }
                speed = (int) speedDouble;
                //System.out.println(speed);
            }

            spriteCounter++;
            if (spriteCounter >= 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            speedDouble = 1;
        }

    }

    public void pickUpObject(int i) {

        if (i != 999) {

            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null; 
                    System.out.println("Key: " + hasKey);
                    gp.playSE(1);
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.playSE(3);
                    }
                    
                    break;

                case "Boots":
                    speedMax += 2;
                    gp.obj[i] = null;
                    gp.playSE(2);
                    break;

                case "Chest":
                    
                    gp.obj[i] = null;
                    gp.playSE(4);
                    break;
                
                
                default:
                    break;
            }


        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        if (keyH.debugEnabled) {
            g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            g2.setColor(Color.RED);
        }
        


    }


    
}
