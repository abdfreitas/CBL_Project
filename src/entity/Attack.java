package src.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;
import src.main.KeyHandler;
import src.main.MouseInput;


/**
    Class for swinging weapon.
*/
public class Attack {
    GamePanel gp;
    KeyHandler keyH;

    private boolean attackWasReleased = true;

    public BufferedImage[] weapon = new BufferedImage[6];
    public boolean attacking = false;
    public int attackFrameCounter = 0;
    public int attackFrameCounterMax = 2;
    public int attackFrame = 1;
    public int attackFrameMax = 5;
    public int attackAngle = 120;
    public int angleLocalOffset = 0;

    AffineTransform transform = new AffineTransform();
    AffineTransform transformColB = new AffineTransform(); // Collision box transform

    int angle = 0;
    int localOffsetX;
    int localOffsetY;
    BufferedImage imageAttack = null;

    int hitboxX;
    int hitboxY;
    int hitboxX2;
    int hitboxY2;
    int hitboxPivotX;
    int hitboxPivotY;
    int hitboxAngle;
    int hitboxPivotAngle;
    int hitboxPivotDistance = 20;
    int hitboxDistance = 50;
    int hitboxDistance2 = 25;
    int hitboxWidth = 20;

    public Rectangle solidArea;
    public Rectangle solidArea2;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    
    /**
    * Creates a new Attack instance.
    *
    * @param keyH  The KeyHandler for input
    * @param gp    The GamePanel reference
    * @param player The Player who performs the attack
    */
    public Attack(KeyHandler keyH, GamePanel gp, Player player) {

        this.gp = gp;
        this.keyH = keyH;
        getAttackImage();

        solidArea = new Rectangle();
        solidArea2 = new Rectangle();
        solidArea.x = 9;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = hitboxWidth;
        solidArea.height = hitboxWidth;
        solidArea2.width = hitboxWidth;
        solidArea2.height = hitboxWidth;

    }

    /**
     * Update method.
     * Checks if a new attack should be started 
     * or how far allong the animation of the attack should be
     */
    public void update(Player player, MouseInput mIn) {

        if (!keyH.attackPressed && !attackWasReleased) {
            attackWasReleased = true;
        }

        if (keyH.attackPressed && !attacking && attackWasReleased) {
            attacking = true;
            attackWasReleased = false;
            attackFrame = 1;
            attackFrameCounter = 0;
        }

        if (attacking) {
            attackFrameCounter++;

            if (attackFrame > attackFrameMax) {
                attacking = false;
            } else if (attackFrameCounter >= attackFrameCounterMax) {
                attackFrame++;
                attackFrameCounter = 0;
            }

            angle = 0;
            imageAttack = null;
            transform = new AffineTransform();

            // Move to player
            transform.translate(player.screenX + gp.tileSize / 2, 
                player.screenY + gp.tileSize / 2); 

            angleLocalOffset = (attackFrame - 1) * attackAngle / attackFrameMax 
                + attackFrameCounter * attackAngle / (attackFrameCounterMax * attackFrameMax);

            localOffsetX = (int) (player.dXNorm * 20);
            localOffsetY = (int) (player.dYNorm * 20);

            double angleDouble = (double) (-angleLocalOffset + 45 + attackAngle / 2) 
                + Math.toDegrees(player.mAngle);

            angle = (int) angleDouble;
            hitboxPivotX = player.worldX + localOffsetX + (gp.tileSize / 2);
            hitboxPivotY = player.worldY + localOffsetY + (gp.tileSize / 2);
            hitboxAngle = angle + 135;

            hitboxX = hitboxPivotX - (int) (Math.cos(Math.toRadians(hitboxAngle)) 
                * (double) hitboxDistance) - hitboxWidth  / 2;
            hitboxY = hitboxPivotY - (int) (Math.sin(Math.toRadians(hitboxAngle)) 
                * (double) hitboxDistance) - hitboxWidth  / 2;
            hitboxX2 = hitboxPivotX - (int) (Math.cos(Math.toRadians(hitboxAngle)) 
                * (double) hitboxDistance / 2) - hitboxWidth  / 2;
            hitboxY2 = hitboxPivotY - (int) (Math.sin(Math.toRadians(hitboxAngle)) 
                * (double) hitboxDistance / 2) - hitboxWidth  / 2;

            solidArea.x = hitboxX;
            solidArea.y = hitboxY;
            solidArea2.x = hitboxX2;
            solidArea2.y = hitboxY2;

            // Uses affinetransform to rotate, scale and translate. Order is important.
            transform.translate(localOffsetX, localOffsetY); 
            transform.rotate(Math.toRadians(angle));
            transform.scale(gp.scale, gp.scale);
            transform.translate(0, -(gp.tileSize / gp.scale));
            
            imageAttack = weapon[attackFrame - 1];
        }
    }

    /** 
     * Put image streams in an image array.
     */
    public void getAttackImage() {

        try {  
            weapon[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/objects/Shovel_Swing-1.png.png"));
            weapon[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/objects/Shovel_Swing-2.png.png"));
            weapon[2] = ImageIO.read(getClass().getResourceAsStream(
                "/res/objects/Shovel_Swing-3.png.png"));
            weapon[3] = ImageIO.read(getClass().getResourceAsStream(
                "/res/objects/Shovel_Swing-4.png.png"));
            weapon[4] = ImageIO.read(getClass().getResourceAsStream(
                "/res/objects/Shovel_Swing-5.png.png"));
            weapon[5] = ImageIO.read(getClass().getResourceAsStream(
                "/res/objects/Shovel_Swing-5.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 
     * Draws the sprite.
     * Uses affinetransform to rotate and translate the image into the correct angle and position
     */
    public void draw(Graphics2D g2, Player player) {
        // Draw Attack frames
        if (attacking) {
            g2.drawImage(imageAttack, transform, null);
            g2.drawRect(hitboxPivotX, hitboxPivotY, 3, 3);

            if (KeyHandler.debugEnabled) {
                int hitboxXScreen = hitboxX - player.worldX + player.screenX;
                int hitboxYScreen = hitboxY - player.worldY + player.screenY;
                int hitboxX2Screen = hitboxX2 - player.worldX + player.screenX;
                int hitboxY2Screen = hitboxY2 - player.worldY + player.screenY;
                g2.drawRect(hitboxX2Screen, hitboxY2Screen, hitboxWidth, hitboxWidth);
                g2.drawRect(hitboxXScreen, hitboxYScreen, hitboxWidth, hitboxWidth);
            }
        }
    }
}
