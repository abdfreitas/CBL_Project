package src.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import src.GUI.GUI;
import src.drops.DropSupercClass;
import src.entity.Attack;
import src.entity.Entity;
import src.entity.Player;
import src.fx.HealthBar;
import src.lib.Random;
import src.object.SuperObject;
import src.tile.TileManager;
import src.user.ConfigManager;

/** ADD COMMENT. */
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

    // Load Config and Save files
    ConfigManager configManager;

    // System
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    public MouseInput mIn = new MouseInput();
    MouseClickInput mClick = new MouseClickInput(keyH);
    public SoundManager sound = new SoundManager();
    public CollisionDetection collisionDetection = new CollisionDetection(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Thread gameThread;

    // Entity and object
    public Player player = new Player(this, 30, 30, "player");
    public SuperObject[] obj = new SuperObject[20];
    public Attack attack = new Attack(keyH, this, player);

    public EntitySetter entitySetter = new EntitySetter(this, player);
    public List<Entity> entities = new ArrayList<>(); // Entities is list of monsters
    public List<Entity> friendlies = new ArrayList<>(); // Friendlies is list of player + plants
    public DropSetter dropSetter = new DropSetter(this);
    public List<DropSupercClass> drops = new ArrayList<>(); // List of drops
    public List<int[]> attackInfo = new ArrayList<>(); // What enemies are hit during an attack
    public HealthBar healthbar = new HealthBar();

    //GUI
    public GUI gui = new GUI();

    // Set players initial position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // Waves
    public int wave = 0;
    public int waveStarted = 0;

    /**
     * Basic panel setup: size, background, etc.
     * This is called once when we create the game window.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        addMouseMotionListener(mIn);
        addMouseListener(mClick);
    }

    /**
     * Load config and content, place objects/entities/drops,
     * and start background music. Call before starting the game loop.
     */
    public void setupGame() {
        ConfigManager.loadConfig();
        assetSetter.setObject();
        friendlies.add(player);
        entitySetter.setEntity(this);
        dropSetter.setDrop(this, 20 * tileSize, 30 * tileSize, "coin");
        sound.playMusic(0, true);;

        // Spawn some drops so the player can water flower
        for (int j = 0; j < 50; j++) {
            dropSetter.setDrop(this, Random.randomInt(0, 50) * tileSize, 
                Random.randomInt(0, 50) * tileSize, "waterbottle");
        }
    }


    /**
     * Start game thread so run() can update/render.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Fixed-rate game loop (fps).
     */
    public void run() {
        double drawInterval = 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            // long currentTime = System.nanoTime();
            update();
            repaint(); // Draw

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

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

    /**
     * Advance the game with player + entities + attacks + drops + wave logic.
     * Also triggers music changes tied to waves.
     */
    public void update() {
        
        // Wave music trigger once
        if (wave == 1 && waveStarted == 0) {
            waveStarted = 1;
            sound.playMusic(8, true);
            sound.setMusicGainDb(-15);
        }

        player.update(this, mIn);
        attack.update(player, mIn);
        attackInfo = collisionDetection.checkAttack(this, player, attack, entities);
        entitySetter.update(this);

        if (!entities.isEmpty()) {
            Iterator<Entity> it = entities.iterator();
            while (it.hasNext()) {
                Entity entity = it.next();
                if (entity != null) {
                    // Apply attack hits
                    for (int[] i : attackInfo) {
                        if (i[0] == entities.indexOf(entity)) {
                            entity.directionDamage = i[1];
                            entity.getHit = true;
                        }
                    }
                    entity.update();

                    // When killed, enemies drop coins (1–8) and play SFX
                    if (entity.HP <= 0) {
                        dropSetter.setDrop(this, entity.worldX, entity.worldY, "coin");
                        int rInt = Random.randomInt(1, 8);
                        for (int j = 0; j < rInt; j++) {
                            dropSetter.dropCoin(this, entity.worldX, entity.worldY, "coin");
                        }
                        it.remove();
                        sound.playSfx(7);
                    }
                }
            }
        }

        // Update friendlies but skip player because we already updated it
        for (Entity entity : friendlies) {
            if (entity != null) {
                if (entity != player) {
                    entity.update();
                }
            }
        }

        // Update drops
        if (!drops.isEmpty()) {
            collisionDetection.checkDrop(player, this);
            // if (index != 999) {
            //     //System.out.println(index);
            // }

            for (Iterator<DropSupercClass> itDrop = drops.iterator(); itDrop.hasNext();) {
                DropSupercClass d = itDrop.next();
                if (d == null) {
                    continue;
                }

                d.update(this, player);

                if (d.pickedUp) {
                    itDrop.remove();
                    sound.playSfx(1);
                }
            }
        }      
    }

    /**
     * Draw everything in the correct order.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Tiles
        tileM.draw(g2);

        // Object
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }

        }

        // Friendlies
        for (Entity entity : friendlies) {
            if (entity != null) {
                entity.draw(g2);
            }
        }

        // Drops
        if (!drops.isEmpty()) {
            for (DropSupercClass drop : drops) {
                if (drop != null) {
                    drop.draw(g2, this);
                }

            }
        }      

        // Player
        player.draw(g2);

        // Monster
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                if (entity != null) {
                    if (entity != player) {
                        entity.draw(g2);
                    }
                }

            }
        }   
        
        // Draw weapon swing
        if (attack.attacking) {
            attack.draw(g2, player);
        }

        // Entities
        for (Entity entity : entities) {
            if (entity != null) {
                if (entity != player) {
                    healthbar.draw(g2, this, entity);
                }
            }

        }

        // Healthbar
        for (Entity entity : friendlies) {
            if (entity != null) {
                healthbar.draw(g2, this, entity);
            }

        }

        // GUI
        gui.draw(g2, this);
        
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

    private int debugOffsetX;
    private int debugOffsetY;
    private int debugLineSpace = 10;
    
    /**
     * Debug overlay.
     */
    public void drawDebug(Graphics2D g2) {
        debugOffsetX = 20;
        debugOffsetY = 20;

        g2.drawString("DEBUG MODE ON", debugOffsetX, debugOffsetY);

        debugOffsetY += debugLineSpace;
        g2.drawString("Player speed: " + player.speed, debugOffsetX, debugOffsetY);

        debugOffsetY += debugLineSpace;
        g2.drawString("Spawn counter MAX: " + entitySetter.spawnCounterMax, 
            debugOffsetX, debugOffsetY);

        debugOffsetY += debugLineSpace;
        g2.drawString("random x: " + entitySetter.x, debugOffsetX, debugOffsetY);

        debugOffsetY += debugLineSpace;
        g2.drawString("Spawn counter: " + entitySetter.spawnCounter, debugOffsetX, debugOffsetY);

        debugOffsetY += debugLineSpace;
        g2.drawString("Monster count: " + this.entities.size(), debugOffsetX, debugOffsetY);
    }
}
