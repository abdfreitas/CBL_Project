package src.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import src.main.GamePanel;

/**
 * Handles all the tiles in the game world (images, properties).
 * 
 * Loads the map file, sets up which tile is which,
 * and draws the visible part of the map around the player
 */
public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][]; // 2D array representing the world layout using tile numbers

    /**
     * Loads all tiles and reads the map from a text file.
     * 
     * @param gp reference to the GamePanel so we know world size and tile info
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[20]; // create space for 20 different tile types
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/maps/world01.txt");
    }

    /**
     * Loads the tile images and sets up which ones have collision.
     * 
     * The index here matches the numbers used in the map text file.
     */
    public void getTileImage() {
        try {
            // Grass
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/agrass.png"));

            // Wall
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/032.png"));
            tile[1].collision = true;

            // Water
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/019.png"));
            tile[2].collision = true;

            // Earth
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/017.png"));

            // Tree
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/ytree.png"));
            tile[4].collision = true;

            // Path tiles (visual variations for connecting paths)
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path.png"));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/apath_bl.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/apath_br.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/apath_tl.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/apath_tr.png"));

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/apath_t.png"));

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/apath_b.png"));

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/apath_l.png"));

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/apath_r.png"));

            // Grass Top Right
            tile[14] = new Tile();
            tile[14].image = 
                ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass_tr.png"));

            // Grass Top Left
            tile[15] = new Tile();
            tile[15].image = 
                ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass_tl.png"));

            // Grass Bottom Right
            tile[16] = new Tile();
            tile[16].image = 
                ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass_br.png"));

            // Grass Bottom Left
            tile[17] = new Tile();
            tile[17].image = 
                ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass_bl.png"));

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * Draws only the visible tiles around the player.
     * 
     * Camera stays centered on player
     */
    public void draw(Graphics2D g2) {
        for (int r = 0; r < gp.maxWorldRow; r++) {
            for (int c = 0; c < gp.maxWorldCol; c++) {

                int worldX = c * gp.tileSize;
                int worldY = r * gp.tileSize;

                // Translate world position to screen position
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Only draw tiles that are inside the visible area (camera view)
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX 
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY 
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[mapTileNum[c][r]].image, screenX, screenY,
                        gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }

    /**
     * Reads a map text file and fills in mapTileNum[][]
     * so the game knows which tile type goes in each position.
     * 
     * @param filePath the path to the map text file
     */
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Read map line by line
            for (int r = 0; r < gp.maxWorldRow; r++) {
                String line = br.readLine();
                for (int c = 0; c < gp.maxWorldCol; c++) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[c]);
                    mapTileNum[c][r] = num;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}