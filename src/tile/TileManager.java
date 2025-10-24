package src.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import src.main.GamePanel;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][]; 

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[20];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/world01.txt");

    }

    public void getTileImage() {

        try {
            // Grass
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));

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
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/016.png"));
            tile[4].collision = true;

            // Paths---------------------------------------
            // Path
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path.png"));
            
            // Path Bottom Left
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path_bl.png"));

            // Path Bottom Right
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path_br.png"));

            // Path Top Left
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path_tl.png"));

            // Path Top Right
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path_tr.png"));

            // Path Top
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path_t.png"));

            // Path Bottom 
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path_b.png"));

            // Path Left
            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path_l.png"));

            // Path Right
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/path_r.png"));

            // Grass Top Right
            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass_tr.png"));

            // Grass Top Left
            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass_tl.png"));

            // Grass Bottom Right
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass_br.png"));

            // Grass Bottom Left
            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass_bl.png"));

        } catch (Exception e) {
            // TODO: handle exception
            e.getStackTrace();
        }

    }

    public void draw(Graphics2D g2) {



        

        for (int r = 0; r < gp.maxWorldRow; r++) {
            for (int c = 0; c < gp.maxWorldCol; c++) {

                int worldX = c * gp.tileSize;
                int worldY = r * gp.tileSize;

                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[mapTileNum[c][r]].image, screenX, screenY,
                        gp.tileSize, gp.tileSize, null);
                }
            }
        }

    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

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
            // TODO: handle exception
        }
    }

    
}