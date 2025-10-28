package src.entity;

import java.io.IOException;
import javax.imageio.ImageIO;
import src.main.GamePanel;


public class Snail1 extends Entity {

    public Snail1(GamePanel gp, int originX, int originY, String name) {
        super(gp, originX, originY, name);

        interactable = false;
        collisionOn = true;
        frameNumMax = 2;
        doesDamage = true;

        try {
            frame[0] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/snail1/Snail1-1.png.png"));
            frame[1] = ImageIO.read(getClass().getResourceAsStream(
                "/res/entities/snail1/Snail1-2.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}