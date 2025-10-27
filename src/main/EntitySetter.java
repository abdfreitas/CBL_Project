package src.main;

import src.entity.Chicken;
import src.entity.Flower2;
import src.entity.Monster;
import src.entity.Player;
import src.entity.Snail1;
import src.entity.ThroneBearer;
import src.entity.Wizard;
import src.user.ConfigManager;

/**
 * Spawns and positions entities in the map.
 * Called when game starts.
 */
public class EntitySetter {
    GamePanel gp;
    Player player;

    int spawnCounter;
    int spawnCounterMin = 100;
    int spawnCounterMax = 1200;
    
    int spawnWidth = 200;
    int spawnHeight = 200;
    int spawnN;
    int spawnNMax = 6;
    int spawnNMin = 1;

    int spawnX;
    int spawnXMax = 2400 - spawnWidth;
    int spawnXMin = 0;
    int spawnY;
    int spawnYMax = 2400 - spawnWidth;
    int spawnYMin = 0;

    double x;

    /**
    * Keep reference to the game so we can access tileSize.
    * @param gp main game panel
    */
    public EntitySetter(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
    }
    
    /**
    * Create and place all entities for the current map.
    * Uses tile coordinates and converts them to world pixels.
    */
    public void setEntity(GamePanel gp) {

        // Flower
        Flower2 f = (new Flower2(gp, 25 * gp.tileSize, 25 * gp.tileSize, "flower2"));
        gp.friendlies.add(f);

        // Chicken
        Chicken c = (new Chicken(gp, 25 * gp.tileSize, 15 * gp.tileSize, "chicken"));
        gp.friendlies.add(c);
        c = (new Chicken(gp, 20 * gp.tileSize, 20 * gp.tileSize, "chicken"));
        gp.friendlies.add(c);
        c = (new Chicken(gp, 25 * gp.tileSize, 23 * gp.tileSize, "chicken"));
        gp.friendlies.add(c); 

        // Wizard... or is he? ;)
        Wizard w = (new Wizard(gp, 27 * gp.tileSize, 22 * gp.tileSize, "wizard"));
        gp.friendlies.add(w);

        // Snail
        Snail1 s = (new Snail1(gp, 27 * gp.tileSize, 17 * gp.tileSize, "wizard"));
        gp.entities.add(s);

        // Thronebearer
        ThroneBearer t = (new ThroneBearer(gp, 21 * gp.tileSize, 16 * gp.tileSize, "wizard"));
        gp.entities.add(t);
    }

    /**
    * Handles enemy spawning and wave progression during the game.
    *
    * When there are a few enemies left, it starts a new wave and spawns more.
    * The number of enemies and spawn timing scale up a bit each wave.
    *
    * @param gp reference to the main GamePanel
    */
    public void update(GamePanel gp) {

        // Only ghosts
        if (gp.entities.size() < 3) {

            // Spawn a small wave of enemies around a region, gets harder each wave
            if (spawnCounter <= 0 && gp.wave > 0) {
                gp.wave++; // // Increase wave count every time a new spawn cycle starts
                
                // Determine how many enemies spawn this wave,
                // Formulas make each wave spawn slightly more monsters than previous               
                spawnNMin = (int) Math.pow(gp.wave * 0.4 + 1, 1.5); // Lower bound (grows slower)
                spawnNMax = (int) Math.pow(gp.wave * 0.5 + 3, 1.5);; // Higher bound (grows faster)
                spawnN = (int) randomDoubleWeighedDown(spawnNMin, spawnNMax, 3, 0.3);;

                // Randomly reset time until next spawn event
                // The bigger the result, the longer until the next wave
                spawnCounter = (int) randomDoubleWeighedDown(spawnCounterMin, 
                spawnCounterMax, 3, 0.3);

                // Random part of the map to spawn the enemies in
                spawnX = randomInt(spawnXMin, spawnXMax);
                spawnY = randomInt(spawnYMin, spawnYMax);

                
                while (spawnN > 0) {
                    // Pick random coordinates inside chosen spawn area
                    double spawnWorldXDouble = (double) randomInt(spawnX, spawnX + spawnWidth);
                    double spawnWorldYDouble = (double) randomInt(spawnX, spawnX + spawnWidth);

                    // Create monster and add it to the entity list
                    Monster m = new Monster(gp, (int) spawnWorldXDouble, 
                        (int) spawnWorldYDouble, "monster");
                    m.accelerationFactor = ConfigManager.getDouble("monster.speed", 0.1);
                    gp.entities.add(m);
                    spawnN--; 
                }
            } else {
                spawnCounter--;
            }
        }
    }

    /**
    * Gives a random integer between min and max.
    * Helper for spawning/timing.
    */
    public int randomInt(int min, int max) {
        int n = (int) (Math.random() * (double) ((max 
                - min) + min));
        return n;
    }

    /**
    * Gives a random double between min and max.
    * Helper for spawning/timing.
    */
    public double randomDouble(double min, double  max) {
        double n = Math.random() * ((max 
                - min) + min);
        return n;
    }

    /**
    * Returns a random double between min and max, but with a "weighted" curve.
    * 
    * We want to bias results, with smaller numbers are more likely than big ones.
    * Controlled by:
    *   exp  → how steep the bias curve is
    *   pol2 → how much we bend the middle section
    * Desmos to visualize this (see link below).
    */
    public double randomDoubleWeighedDown(double min, double max, double exp, double pol2) {
        // Made the following desmos graph to visualize the randomizer based on parameters
        // b = max, a = exp, c = pol2, d = min
        // https://www.desmos.com/calculator/5vm5tltysh

        x = Math.random();
        double n =  (Math.pow(x, exp) * (max - min)) 
                    + ((max - min) * pol2 * x * (1 - x)) 
                    + min;
        return n;
    }    
}
