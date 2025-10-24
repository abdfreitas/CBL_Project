package src.main;

import src.drops.WaterBottle;
import src.drops.DropSupercClass;

public class DropSetter {



    GamePanel gp;

    public DropSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setDrop(GamePanel gp, int originX, int originY, String name) {

        DropSupercClass d = new WaterBottle(gp, originX, originY, name);
        d.name = name;
        d.worldX = originX;
        d.worldY = originY;
        gp.drops.add(d);

    }
    
}
