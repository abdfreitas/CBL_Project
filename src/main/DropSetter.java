package src.main;

import src.drops.Coin;
import src.drops.DropSupercClass;
import src.drops.Heart;
import src.drops.WaterBottle;

/**
 * Drops items into the world (coins, hearts, waters).
 */
public class DropSetter {
    GamePanel gp;

    /**
    * Connects DropSetter to the main GamePanel so we can place drops.
    * @param gp the main game panel
    */
    public DropSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Spawns a drop at a world position.
     * @param name what to drop
     * @param originX tile/world X
     * @param originY tile/world Y
     */
    public void setDrop(GamePanel gp, int originX, int originY, String name) {
        DropSupercClass d = new WaterBottle(gp, originX, originY, name);
        d.name = name;
        d.worldX = originX;
        d.worldY = originY;
        gp.drops.add(d);
    }

    /** ADD COMMENT. */
    public void dropHeart(GamePanel gp, int originX, int originY, String name) {
        DropSupercClass d = new Heart(gp, originX, originY, name);
        d.name = name;
        d.worldX = originX;
        d.worldY = originY;
        gp.drops.add(d);
    }

    /** ADD COMMENT. */
    public void dropCoin(GamePanel gp, int originX, int originY, String name) {

        DropSupercClass d = new Coin(gp, originX, originY, name);
        d.name = name;
        d.worldX = originX;
        d.worldY = originY;
        gp.drops.add(d);
    } 
}
