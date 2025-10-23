package src.main;

import src.entity.Entity;
import src.entity.Flower1;
import src.entity.Monster;
import src.entity.Player;
import src.user.ConfigManager;

public class EntitySetter {

    GamePanel gp;

    Player player;

    

    public EntitySetter(GamePanel gp, Player player) {

        this.gp = gp;
        this.player = player;

    }

    public void setEntity(GamePanel gp) {

        gp.entities[0] = new Monster(gp, player);
        gp.entities[0].worldXDouble = gp.tileSize * 25;
        gp.entities[0].worldYDouble = gp.tileSize * 20;
        gp.entities[0].speedDouble = ConfigManager.getDouble("monster.speed", 0);


        gp.entities[1] = new Monster(gp, player);
        gp.entities[1].worldXDouble = gp.tileSize * 25;
        gp.entities[1].worldYDouble = (gp.tileSize * 22);
        gp.entities[1].speedDouble = 0.0;

        gp.friendlies[1] = new Flower1(gp, player);
        gp.friendlies[1].worldX = gp.tileSize * 20;
        gp.friendlies[1].worldY = gp.tileSize * 20;
        gp.friendlies[1].worldXDouble = gp.tileSize * 20;
        gp.friendlies[1].worldYDouble = gp.tileSize * 20;
        
        


    }

    
}
