package src.main;

import src.entity.Chicken;
import src.entity.Flower2;
import src.entity.Monster;
import src.entity.Player;
import src.entity.Snail1;
import src.entity.ThroneBearer;
import src.entity.Wizard;
import src.user.ConfigManager;

/** ADD COMMENT. */
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

    /** ADD COMMENT. */
    public EntitySetter(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
    }

    /** ADD COMMENT. */
    public void setEntity(GamePanel gp) {

        Flower2 f = (new Flower2(gp, 25 * gp.tileSize, 25 * gp.tileSize, "flower2"));
        gp.friendlies.add(f);

        Chicken c = (new Chicken(gp, 25 * gp.tileSize, 15 * gp.tileSize, "chicken"));
        gp.friendlies.add(c);
        c = (new Chicken(gp, 20 * gp.tileSize, 20 * gp.tileSize, "chicken"));
        gp.friendlies.add(c);
        c = (new Chicken(gp, 25 * gp.tileSize, 23 * gp.tileSize, "chicken"));
        gp.friendlies.add(c);

        Wizard w = (new Wizard(gp, 27 * gp.tileSize, 22 * gp.tileSize, "wizard"));
        gp.friendlies.add(w);

        Snail1 s = (new Snail1(gp, 27 * gp.tileSize, 17 * gp.tileSize, "wizard"));
        gp.entities.add(s);

        ThroneBearer t = (new ThroneBearer(gp, 21 * gp.tileSize, 16 * gp.tileSize, "wizard"));
        gp.entities.add(t);
    }

    /** ADD COMMENT. */
    public void update(GamePanel gp) {
        if (gp.entities.size() < 3) {
            if (spawnCounter <= 0 && gp.wave > 0) {
                gp.wave++;
                
                spawnNMin = (int) Math.pow(gp.wave * 0.4 + 1, 1.5);
                spawnNMax = (int) Math.pow(gp.wave * 0.5 + 3, 1.5);;
                spawnN = (int) randomDoubleWeighedDown(spawnNMin, spawnNMax, 3, 0.3);;

                spawnCounter = (int) randomDoubleWeighedDown(spawnCounterMin, 
                spawnCounterMax, 3, 0.3);

                spawnX = randomInt(spawnXMin, spawnXMax);
                spawnY = randomInt(spawnYMin, spawnYMax);

                while (spawnN > 0) {
                    double spawnWorldXDouble = (double) randomInt(spawnX, spawnX + spawnWidth);
                    double spawnWorldYDouble = (double) randomInt(spawnX, spawnX + spawnWidth);

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

    /** ADD COMMENT. */
    public int randomInt(int min, int max) {
        int n = (int) (Math.random() * (double) ((max 
                - min) + min));
        return n;
    }

    /** ADD COMMENT. */
    public double randomDouble(double min, double  max) {
        double n = Math.random() * ((max 
                - min) + min);
        return n;
    }

    /** ADD COMMENT. */
    public double randomDoubleWeighedDown(double min, double max, double exp, double pol2) {

        // I made the following desmos graph to visualize the randomizer based on parameters
        // b = max, a = exp, c = pol2, d = min

        // https://www.desmos.com/calculator/5vm5tltysh

        x = Math.random();
        double n =  (Math.pow(x, exp) * (max - min)) + ((max - min) * pol2 * x * (1 - x)) + min;
        return n;
    }    
}
