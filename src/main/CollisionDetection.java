package src.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

import src.entity.Attack;
import src.entity.Entity;
import src.entity.Player;

public class CollisionDetection {

    GamePanel gp;

    // Scans this amount of pixels extra in direction of walking, to reduce "sticking" to walls.
    int collisionBuffer = 0; 

    int[] tileNum1F = new int[2];
    int[] tileNum2F = new int[2];

    public CollisionDetection(GamePanel gp) {
        this.gp = gp;
    }

    public void CheckTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed - collisionBuffer) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

            // System.out.println(entity.speed);

                tileNum1F[0] = entityLeftCol;
                tileNum1F[1] = entityTopRow;
                tileNum2F[0] = entityRightCol;
                tileNum2F[1] = entityTopRow;



                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
            entityBottomRow = (entityBottomWorldY + entity.speed + collisionBuffer) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                tileNum1F[0] = entityLeftCol;
                tileNum1F[1] = entityBottomRow;
                tileNum2F[0] = entityRightCol;
                tileNum2F[1] = entityBottomRow;

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed - collisionBuffer) / gp.tileSize;

                tileNum1F[0] = entityLeftCol;
                tileNum1F[1] = entityTopRow;
                tileNum2F[0] = entityLeftCol;
                tileNum2F[1] = entityBottomRow;

                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed + collisionBuffer) / gp.tileSize;

                tileNum1F[0] = entityRightCol;
                tileNum1F[1] = entityTopRow;
                tileNum2F[0] = entityRightCol;
                tileNum2F[1] = entityBottomRow;

                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;


        
            default:
                break;
        }

        

    }

    public int checkObject(Entity entity, boolean player) {
            
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;


                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= (entity.speed + collisionBuffer);
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            //System.out.println("Up collision");
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += (entity.speed + collisionBuffer);
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            //System.out.println("Down collision");
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= (entity.speed + collisionBuffer);
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            //System.out.println("Left collision");
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += (entity.speed + collisionBuffer);
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            //System.out.println("Right collision");
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                
                    default:
                        break;
                    
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public int[] checkEntity(Player player, GamePanel gp) {

        int index = 999;
        int directionDamage = 0;

        for (Entity entity : gp.entities) {
            if (entity != null) {


                // Get Players solid area position
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;


                // Get the object's solid area position
                entity.solidArea.x = (int) entity.worldXDouble 
                    + entity.solidArea.x;
                entity.solidArea.y = (int) entity.worldYDouble 
                    + entity.solidArea.y;

                

                if (player.solidArea.intersects(entity.solidArea)) {
                    //System.out.println("Entity collision");
                    if (entity.doesDamage == true) {
                        player.getHit = true;
                        index = gp.entities.indexOf(entity);

                        int dX = player.worldX - (int) entity.worldXDouble;
                        int dY = player.worldY - (int) entity.worldYDouble;

                        if (Math.abs(dX) > Math.abs(dY)) {
                            if (dX > 0) {
                                directionDamage = 4;
                            } else {
                                directionDamage = 6;
                            }
                        } else {
                            if (dY > 0) {
                                directionDamage = 8;
                            } else {
                                directionDamage = 2;
                            }
                        }

                        
                    }
                }

                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
            }

            
    

        }
        return new int[] {index, directionDamage};

    }

    public List<int[]> checkAttack(GamePanel gp, Entity entity, Attack attack, List<Entity> entities) {

        List<int[]> attackInfo = new ArrayList<int[]>();

        int index = 999;
        int directionDamage = 0;

        if (attack.attacking) {

            for (Entity attackeeEntity : entities) {
                if (attackeeEntity != null) {

                    if (attackeeEntity.invincible) {
                        continue;
                    }

                    index = entities.indexOf(attackeeEntity);

                    // Get the object's solid area position
                    attackeeEntity.solidArea.x = (int) attackeeEntity.worldXDouble 
                        + attackeeEntity.solidArea.x;
                    attackeeEntity.solidArea.y = (int) attackeeEntity.worldYDouble 
                        + attackeeEntity.solidArea.y;

                    // int testEntityX = attackeeEntity.solidArea.x;
                    // int testEntityY = attackeeEntity.solidArea.y;

                    // int testAttackX = attack.solidArea.x;
                    // int testAttackY = attack.solidArea.y;
                        
                    if (attack.solidArea.intersects(attackeeEntity.solidArea) 
                        || attack.solidArea2.intersects(attackeeEntity.solidArea)) {
                        //System.out.println("Hit!");

                        attackeeEntity.getHit = true;
                        index = gp.entities.indexOf(entity);

                        int dX = entity.worldX - (int) attackeeEntity.worldXDouble;
                        int dY = entity.worldY - (int) attackeeEntity.worldYDouble;

                        if (Math.abs(dX) > Math.abs(dY)) {
                            if (dX > 0) {
                                directionDamage = 4;
                            } else {
                                directionDamage = 6;
                            }
                        } else {
                            if (dY > 0) {
                                directionDamage = 8;
                            } else {
                                directionDamage = 2;
                            }
                        }

                        attackInfo.add(new int[] {index, directionDamage});
                    }

                    // Find direction of damage
                    



                    entity.solidArea.x = entity.solidAreaDefaultX;
                    entity.solidArea.y = entity.solidAreaDefaultY;

                }
            }


        }

        return attackInfo;

        

    }

    public void drawCollision(Graphics2D g2) {

        //BufferedImage image = null;

        int worldX = tileNum1F[0] * gp.tileSize;
        int worldY = tileNum1F[1] * gp.tileSize;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawRect(screenX, screenY, 48, 48);
        g2.setColor(Color.RED);

        worldX = tileNum2F[0] * gp.tileSize;
        worldY = tileNum2F[1] * gp.tileSize;

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawRect(screenX, screenY, 48, 48);
        g2.setColor(Color.RED);



    }

    
    
}
