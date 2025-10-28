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
import src.main.MouseInput;

/**
 * The controllable character.
 *
 * Handles keyboard + mouse input, movement acceleration,
 * dodging, collisions, taking damage, and drawing the right animation frame.
*/
public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    // Where the player is drawn on screen (camera centers on the player)
    public final int screenX;
    public final int screenY;
    // Tracks most-recent movement keys (WASD) to pick a direction
    private CharStack cStack;

    int hasKey = 0;

    // Movement
    public int speedMax;
    public double acceleration = 0.5;
    public double speedDouble;

    // Shift sprint tracking
    private boolean shiftWasPressed;

    // Dodge tracking
    boolean dodgePressed;
    boolean dodgeWasPressed;
    boolean dodging;

    int dodgeCounter;
    int dodgeCounterMax = 30;
    
    // Mouse input 
    int playerCenterX;
    int playerCenterY;
    int dX;
    int dY;
    int distanceMouse;
    double dXNorm;
    double dYNorm;
    double mAngle;
    
    String lookDirection = "down";
    public int waterAmount = 10; // Water used for watering plants 

    /** Create the player at a world position. */
    public Player(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);

        this.gp = gp;
        keyH = gp.keyH;
        this.cStack = keyH.cStack;

        // Player is drawn at screen center (world moves around us)
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Collision box (player top left)
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

    /** Load walking sprites for all four directions. */
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream(
                "/res/player/player_walking/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream(
                "/res/player/player_walking/boy_up_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream(
                "/res/player/player_walking/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream(
                "/res/player/player_walking/boy_left_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream(
                "/res/player/player_walking/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream(
                "/res/player/player_walking/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream(
                "/res/player/player_walking/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream(
                "/res/player/player_walking/boy_right_2.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Initial stats and position. */
    public void setDefaultValues() {
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 30;
        speed = 0;
        speedMax = 4;
        direction = "down";
        priority = 30; // Friendliess have higher priority than monsters
    }

    /**
     * Main player update.
     * 1) Read mouse/keyboard
     * 2) Compute movement (dodge)
     * 3) Handle collisions and damage
     * 4) Update world position and timers
    */
    public void update(GamePanel gp, MouseInput mIn) {
        playerCenterX = screenX + gp.tileSize / 2;
        playerCenterY = screenY + gp.tileSize / 2;

        dX = mIn.mouseX - playerCenterX;
        dY = mIn.mouseY - playerCenterY;

        distanceMouse = (int) Math.sqrt(dX * dX + dY * dY);
        dXNorm = (double) dX / (double) distanceMouse;
        dYNorm = (double) dY / (double) distanceMouse;
        mAngle = Math.atan2(dYNorm, dXNorm);

        // Pick a facing direction for animation
        if (Math.abs(dX) > Math.abs(dY)) {
            if (dX > 0) {
                lookDirection = "right";
            } else {
                lookDirection = "left";
            }
        } else {
            if (dY > 0) {
                lookDirection = "down";
            } else {
                lookDirection = "up";
            }
        }

        // Movement keys held? 
        if (keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed) {

            // Hold SHIFT to temporarily increase top speed
            if (keyH.shiftPressed && !shiftWasPressed) {
                shiftWasPressed = true;
                speedMax += 2;
            }

            if (!keyH.shiftPressed && shiftWasPressed) {
                shiftWasPressed = false;
                speedMax -= 2;
            }

            // Accelerate towards speedMax
            if (speedDouble < speedMax) {
                speedDouble += acceleration;

            } else if (speedDouble > speedMax) {
                speed -= acceleration;
            }
            
            // Use the most recent of WASD from the stack to choose movement direction
            char key = cStack.stack[0];

            if (!stunned) {
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

                // If not attacking, face the way we move
                if (!gp.attack.attacking) {
                    lookDirection = direction;
                }

                // One tap dodge
                if (keyH.dodgePressed && !dodgeWasPressed && dodgeCounter < 1) {
                    dodging = true;
                    dodgeWasPressed = true;
                    dodge();
                    dodgeCounter = dodgeCounterMax;
                }
            }

            if (!keyH.dodgePressed) {
                dodgeWasPressed = false;
            }

            // Don’t exceed top speed
            if (speedDouble > speedMax) {
                speedDouble -= acceleration;
            }

            // Walk cycle changes with speed
            spriteCounter++;
            if (spriteCounter >= 12 * 4 / speedDouble) {
                if (frameNum >= 4) {
                    frameNum = 1;
                } else {
                    frameNum++;
                }
                spriteCounter = 0;
            }

        } else if (speedDouble > 0) {
            // Slow down when no movement keys are pressed
            speedDouble -= 2 * acceleration;
            speed = (int) speedDouble;  
        } else if (speedDouble < 0) {
            speedDouble += 2 * acceleration;
        }

        if (keyH.boost) {
            speedDouble = 20;
        }

        // Check tile collision
        collisionOn = false;
        gp.collisionDetection.CheckTile(this);

        // Check object collision
        int objIndex = gp.collisionDetection.checkObject(this, true);
        pickUpObject(objIndex);

        // Check hits from enemies (returns index + direction of damage)
        getHit = false;
        for (Entity entity : gp.entities) {
            if (entity != null) {
                int[] result = gp.collisionDetection.checkEntity(this, gp);
                directionDamage = result[1];
            }
        }

        // Took damage this frame?
        if (getHit && !invincible) {
            gp.sound.playSfx(5);
            getDamage = true;
            invincible = true;
            hp -= 10;
            // System.out.println("Gets damage from " + index + ", Player has " + HP + "HP");

            invincibleCounter = invincibleCounterMax;
            stunCounter = stunCounterMax;
            stunned = true;

            switch (directionDamage) {
                case 6:
                    direction = "left";
                    // System.out.println("Damaged from right");
                    break;
                case 8:
                    direction = "down";
                    // System.out.println("Damaged from up");
                    break;
                case 4:
                    direction = "right";
                    // System.out.println("Damaged from left");
                    break;
                case 2:
                    direction = "up";
                    // System.out.println("Damaged from down");
                    break;
            
                default:
                    break;
            }

            speedDouble = speedDouble * 0.4;
            speedDouble += 10;
        }

        // Invincibility + stun timers
        if (invincibleCounter <= 0) {
            invincible = false;
        } else {
            invincibleCounter--;
        }

        if (stunCounter <= 0) {
            stunned = false;
        } else {
            stunCounter--;
        }

        speed = (int) speedDouble; // Convert double speed to int for tile movement

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
        } 

        worldXDouble = worldX;
        worldYDouble = worldY;
        dodgeCounter--;
    }

    /** Handle interactions with world objects (key, door, coffee, chest). */
    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null; 
                    //System.out.println("Key: " + hasKey);
                    gp.sound.playSfx(1);
                    break;

                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.sound.playSfx(3);
                    }
                    break;

                case "Coffee":
                    speedMax += 2; // Faster top speed
                    gp.obj[i] = null;
                    gp.sound.playSfx(2);
                    break;

                case "Chest":
                    gp.obj[i] = null;
                    gp.sound.playSfx(4);
                    // Normal win: opened chest
                    gp.winGame();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Draw the player with the correct walking frame.
     * Also draws a white flicker when invincible and a debug hitbox if enabled.
    */
    public void draw(Graphics2D g2) {
        // Draw walking sprites
        BufferedImage image = null;

        switch (lookDirection) {
            case "up":
                if (frameNum % 2 != 0) {
                    image = up1;
                } else {
                    image = up2;
                }
                break;
            case "left":
                if (frameNum % 2 != 0) {
                    image = left1;
                } else {
                    image = left2;
                }
                break;
            case "down":
                if (frameNum % 2 != 0) {
                    image = down1;
                } else {
                    image = down2;
                }
                break;
            case "right":
                if (frameNum % 2 != 0) {
                    image = right1;
                } else {
                    image = right2;
                }
                break;
            default:
                break;
        }

        // Draw Debug Collision box around player
        if (keyH.debugEnabled) {
            g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width,
                solidArea.height);
            g2.setColor(Color.RED);
            g2.drawRect(playerCenterX, playerCenterY, 2, 2);

        }

        // Invisible flicker
        if (invincible && invincibleCounter / 2 / 2 % 2 != 0) {
            BufferedImage whitePlayer = makeSilhouette(image, Color.WHITE);
            g2.drawImage(whitePlayer, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    /**
     * Short dash forward with brief invincibility.
     * Called when the player presses the space key.
     */
    public void dodge() {
        speedDouble += 10;
        invincible = true;
        invincibleCounter = invincibleCounterMax / 2;
    }
}
