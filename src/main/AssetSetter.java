package src.main;

import src.object.OBJ_BlockTile;
import src.object.OBJ_Chest;
import src.object.OBJ_Coffee;
import src.object.OBJ_Door;
import src.object.OBJ_House;
import src.object.OBJ_Key;

/**
 * Handles placing all the game objects (like keys, doors, chests) into the world   
 * and connecting with gamepanel.
 * 
 * This is basically where we decide what goes where before the game starts.
 */
public class AssetSetter {
    GamePanel gp;

    /**
    * Connects AssetSetter to the main GamePanel so it can access the object array.
    * @param gp the main game panel
    */
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    
    /**
    * Creates and positions all objects in the game world.
    * Each object's location is set using tile coordinates.
    */
    public void setObject() {
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 38 * gp.tileSize;
        gp.obj[0].worldY = 28 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 10 * gp.tileSize;
        gp.obj[1].worldY = 14 * gp.tileSize;

        // gp.obj[2] = new OBJ_Key();
        // gp.obj[2].worldX = 37 * gp.tileSize;
        // gp.obj[2].worldY = 7 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(); // Winning door leading to chest
        gp.obj[3].worldX = 12 * gp.tileSize;
        gp.obj[3].worldY = 34 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(); 
        gp.obj[4].worldX = 14 * gp.tileSize;
        gp.obj[4].worldY = 37 * gp.tileSize;

        // gp.obj[5] = new OBJ_Door();
        // gp.obj[5].worldX = 12 * gp.tileSize;
        // gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new OBJ_Chest();
        gp.obj[6].worldX = 18 * gp.tileSize;
        gp.obj[6].worldY = 37 * gp.tileSize;

        gp.obj[7] = new OBJ_Coffee();
        gp.obj[7].worldX = 12 * gp.tileSize;
        gp.obj[7].worldY = 37 * gp.tileSize;

        // Create a small wall made out of block tiles
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                gp.obj[9 + k] = new OBJ_BlockTile();
                // Use tileSize to convert tile positions to world coordinates
                gp.obj[9 + k].worldX = (35 + i) * gp.tileSize;
                gp.obj[9 + k].worldY = (20 + j) * gp.tileSize;
                k++;
            }
        }

        gp.obj[8] = new OBJ_House();
        gp.obj[8].worldX = (35) * gp.tileSize;
        gp.obj[8].worldY = (20) * gp.tileSize;
    }
}
