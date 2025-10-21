package src.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;
import src.main.KeyHandler;


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

    }

    /**
     * Update method.
     * Checks if a new attack should be started 
     * or how far allong the animation of the attack should be
     */
    public void update() {

        if (!keyH.attack_Pressed && !attackWasReleased) {
            attackWasReleased = true;
        }

        if (keyH.attack_Pressed && !attacking && attackWasReleased) {
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
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    /** 
     * Draws the sprite.
     * Uses affinetransform to rotate and translate the image into the correct angle and position
     */
    public void draw(Graphics2D g2, Player player) {
        // Draw Attack frames
        if (attacking == true){

            int angle = 0;
            int localOffsetX;

            int localOffsetY;
            BufferedImage imageAttack = null;

            AffineTransform transform = new AffineTransform();
            
            // Move to player
            transform.translate(player.screenX + gp.tileSize / 2, 
                player.screenY + gp.tileSize / 2); 

            int angleLocalOffset = (attackFrame - 1) * attackAngle / attackFrameMax 
                + attackFrameCounter * attackAngle / (attackFrameCounterMax * attackFrameMax);
            
            switch (player.direction) {
                case "up":
                    localOffsetX = 0;
                    localOffsetY = -(gp.tileSize / gp.scale);
                    angle = -angleLocalOffset + 45 + attackAngle / 2 - 90;
                    break;
                case "left":
                    localOffsetX = -(gp.tileSize / gp.scale);
                    localOffsetY = 0;
                    angle = -angleLocalOffset + 45 + attackAngle / 2 + + 180;
                    break;
                case "down":
                    localOffsetX = 0;
                    localOffsetY = (gp.tileSize / gp.scale);
                    angle = -angleLocalOffset + 45 + attackAngle / 2 + 90;
                    break;
                case "right":
                    localOffsetX = (gp.tileSize / gp.scale);
                    localOffsetY = 0;
                    angle = -angleLocalOffset + 45 + attackAngle / 2;
                    break;
                default:
                    localOffsetX = (gp.tileSize / gp.scale);
                    localOffsetY = 0;
                    break;
            }

            // Uses affinetransform to rotate, scale and translate. Order is important.
            transform.translate(localOffsetX, localOffsetY); 
            transform.rotate(Math.toRadians(angle));
            transform.scale(gp.scale, gp.scale);
            transform.translate(0, -(gp.tileSize / gp.scale));
            
            imageAttack = weapon[attackFrame - 1];

            g2.drawImage(imageAttack, transform, null);

        }
    }
}
