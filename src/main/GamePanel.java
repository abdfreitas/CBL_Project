package src.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import src.entity.Monster;
import src.entity.Player;
import src.object.SuperObject;
import src.tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS:
    final int originalTileSize = 16; // 16x16 tile
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS:
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int fps = 60;

    // System

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();
    public CollisionDetection collisionDetection = new CollisionDetection(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Thread gameThread;

    // Entity and object
    public Player player = new Player(this, keyH);
    public Monster monster = new Monster(this, player);
    public SuperObject[] obj = new SuperObject[10];



    // Set players initial position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        assetSetter.setObject();

        playMusic(0);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawInterval = 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while (gameThread != null) {

            long currentTime = System.nanoTime();

            // 1: Update.
            update();

            // 2: Draw.
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

        
                //System.out.println("Remaining time: " + remainingTime);

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime = System.nanoTime() + drawInterval;
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    public void update() {


        player.update();

        monster.update();
        

    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // Tile
        tileM.draw(g2);

        // Object
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }

        }

        // Player
        player.draw(g2);

        // Monster
        monster.draw(g2);

        // Collision debugger

        if (KeyHandler.debugEnabled) {

            this.drawDebug(g2);

            try {
                collisionDetection.drawCollision(g2);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        
        
        g2.dispose();

    }

    public void playMusic(int i) {

        sound.setFile(i);
        sound.play();
        
        sound.loop();
    }

    public void stopMusic() {

        sound.stop();

    }

    public void playSE(int i) {

        sound.setFile(i);
        sound.play();
    }

    
        private int debugOffsetX;
        private int debugOffsetY;
        private int debugLineSpace = 10;

    public void drawDebug(Graphics2D g2) {

        debugOffsetX = 20;
        debugOffsetY = 20;

        g2.drawString("DEBUG MODE ON", debugOffsetX, debugOffsetY);

        debugOffsetY += debugLineSpace;
        g2.drawString("Player speed: " + player.speed, debugOffsetX, debugOffsetY);

    }
}
