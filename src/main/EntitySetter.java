package src.main;

import src.entity.Entity;
import src.entity.Flower1;
import src.entity.Flower2;
import src.entity.Monster;
import src.entity.Player;
import src.entity.Wizard;
import src.user.ConfigManager;
import src.entity.Chicken;

public class EntitySetter {

    GamePanel gp;

    Player player;

    int spawnCounter;
    int spawnCounterMin = 100;
    int spawnCounterMax = 1200;
    
    int spawnWidth = 200;
    int spawnHeight = 200;
    int spawnN;
    int spawnNMax = 12;
    int spawnNMin = 1;

    int spawnX;
    int spawnXMax = 2400 - spawnWidth;
    int spawnXMin = 0;
    int spawnY;
    int spawnYMax = 2400 - spawnWidth;
    int spawnYMin = 0;

    double x;

    

    public EntitySetter(GamePanel gp, Player player) {

        this.gp = gp;
        this.player = player;

    }

    public void setEntity(GamePanel gp) {

        

        Monster m = new Monster(gp, 25 * gp.tileSize, 20 * gp.tileSize, "monster");
        m.accelerationFactor = ConfigManager.getDouble("monster.speed", 0.1);
        gp.entities.add(m);

        m = new Monster(gp, 25 * gp.tileSize, 22 * gp.tileSize, "monster");
        m.accelerationFactor = ConfigManager.getDouble("monster.speed", 0.1);
        gp.entities.add(m);


        

        // gp.friendlies.add(new Flower1(gp, player));
        // gp.friendlies.get(1).worldX = gp.tileSize * 20;
        // gp.friendlies.get(1).worldY = gp.tileSize * 20;
        // gp.friendlies.get(1).worldXDouble = gp.tileSize * 20;
        // gp.friendlies.get(1).worldYDouble = gp.tileSize * 20;

        Flower2 f = (new Flower2(gp, 25 * gp.tileSize, 25 * gp.tileSize, "flower2"));
        gp.friendlies.add(f);

        Chicken c = (new Chicken(gp, 25 * gp.tileSize, 19 * gp.tileSize, "chicken"));
        gp.friendlies.add(c);
        c = (new Chicken(gp, 20 * gp.tileSize, 20 * gp.tileSize, "chicken"));
        gp.friendlies.add(c);
        c = (new Chicken(gp, 25 * gp.tileSize, 23 * gp.tileSize, "chicken"));
        gp.friendlies.add(c);

        Wizard w = (new Wizard(gp, 27 * gp.tileSize, 22 * gp.tileSize, "wizard"));
        gp.friendlies.add(w);
        
        


    }

    public void update(GamePanel gp) {
        if (spawnCounter <= 0) {

            spawnN = (int) randomDoubleWeighedDown(spawnNMin, spawnNMax, 3, 0.3);;
            spawnCounter = (int) randomDoubleWeighedDown(spawnCounterMin, spawnCounterMax, 3, 0.3);

            

            spawnX = randomInt(spawnXMin, spawnXMax);

            spawnY = randomInt(spawnYMin, spawnYMax);

            while (spawnN > 0) {

                double spawnWorldXDouble = (double) randomInt(spawnX, spawnX + spawnWidth);
                double spawnWorldYDouble = (double) randomInt(spawnX, spawnX + spawnWidth);

                Monster m = new Monster(gp, (int) spawnWorldXDouble, (int) spawnWorldYDouble, "monster");
                m.accelerationFactor = ConfigManager.getDouble("monster.speed", 0.1);
                gp.entities.add(m);

                
                

                

                spawnN--;
                
            }

            



        } else {
            spawnCounter--;
        }
        // System.out.println(spawnCounter);


        //System.out.println(randomDoubleWeighedDown(0, 100, 3, 0.3));

        



        



    }

    public int randomInt(int min, int max) {
        int n = (int) (Math.random() * (double) ((max 
                - min) + min));

        return n;
    }

    public double randomDouble(double min, double  max) {
        double n = Math.random() * ((max 
                - min) + min);

        return n;
    }

    public double randomDoubleWeighedDown(double min, double max, double exp, double pol2) {

        // I made the following desmos graph to visualize the randomizer based on parameters
        // b = max, a = exp, c = pol2, d = min

        // https://www.desmos.com/calculator/5vm5tltysh

        x = Math.random();

        double n =  (Math.pow(x, exp) * (max - min)) + ((max - min) * pol2 * x * (1 - x)) + min;

        return n;
    }

    

    
}
