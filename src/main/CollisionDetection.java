package src.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import src.drops.DropSupercClass;
import src.entity.Attack;
import src.entity.Entity;
import src.entity.Player;

/**
 * Checks if things in the game collide into each other.
 * This includes tiles, objects, enemies, drops, and attacks.
 */
public class CollisionDetection {
    GamePanel gp;

    // Scans this amount of pixels extra in direction of walking, to reduce "sticking" to walls.
    int collisionBuffer = 0; 

    int[] tileNum1F = new int[2];
    int[] tileNum2F = new int[2];

    /**
    * Connects CollisionDetection and logic to the main GamePanel.
    * @param gp the main game panel
    */
    public CollisionDetection(GamePanel gp) {
        this.gp = gp;
    }

    /**
    * Checks if an entity is about to walk into a solid tile.
    * If yes, collisionOn = true so movement stops.
    * @param entity the thing we're checking (player, monster, etc.)
    */
    public void CheckTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1;
        int tileNum2;

        // Get which tiles the entity touches depending on its direction
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed - collisionBuffer) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                tileNum1F[0] = entityLeftCol;
                tileNum1F[1] = entityTopRow;
                tileNum2F[0] = entityRightCol;
                tileNum2F[1] = entityTopRow;
                
                if (gp.tileM.tile[tileNum1].collision 
                    || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
                
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed 
                                  + collisionBuffer) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                tileNum1F[0] = entityLeftCol;
                tileNum1F[1] = entityBottomRow;
                tileNum2F[0] = entityRightCol;
                tileNum2F[1] = entityBottomRow;

                if (gp.tileM.tile[tileNum1].collision 
                    || gp.tileM.tile[tileNum2].collision) {
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

                if (gp.tileM.tile[tileNum1].collision 
                    || gp.tileM.tile[tileNum2].collision) {
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

                if (gp.tileM.tile[tileNum1].collision 
                    || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            default:
                break;
        }
    }

    /**
    * Checks collisions between an entity and any placed object.
    * If it's the player, returns the index of the object hit.
    * @param entity the entity being checked
    * @param player true if this entity is the player
    * @return index of the collided object or 999 if none
    */
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
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "down":
                        entity.solidArea.y += (entity.speed + collisionBuffer);
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            //System.out.println("Down collision");
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                        
                    case "left":
                        entity.solidArea.x -= (entity.speed + collisionBuffer);
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            //System.out.println("Left collision");
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "right":
                        entity.solidArea.x += (entity.speed + collisionBuffer);
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            //System.out.println("Right collision");
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
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

    /**
    * Checks if the player bumps into another entity (like an enemy).
    * If yes, determines which direction the hit came from.
    * @param player the player
    * @param gp the game panel
    * @return [index of entity, direction of damage]
    */
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

                // Compare hitboxes and see if they overlap
                if (player.solidArea.intersects(entity.solidArea)) {
                    if (entity.doesDamage) {
                        player.getHit = true;
                        index = gp.entities.indexOf(entity);

                        int dX = player.worldX - (int) entity.worldXDouble;
                        int dY = player.worldY - (int) entity.worldYDouble;

                        // Use dx, dy to figure out if hit came from top, bottom, left, or right
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

    /**
    * Checks if the player walks over a drop and picks it up.
    * @param player the player
    * @param gp the game panel
    * @return index of the drop, or 999 if none
    */
    public int checkDrop(Player player, GamePanel gp) {
        int index = 999;
        int directionDamage = 0;

        for (DropSupercClass drop : gp.drops) {
            if (drop != null) {
                // Get Players solid area position
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;

                // Get the object's solid area position
                drop.solidArea.x = (int) drop.worldXDouble 
                    + drop.solidArea.x;
                drop.solidArea.y = (int) drop.worldYDouble 
                    + drop.solidArea.y;

                // When player overlaps with drop’s hitbox, mark as picked up
                if (player.solidArea.intersects(drop.solidArea)) {
                    //System.out.println("drop collision");
                    index = gp.drops.indexOf(drop);
                    drop.pickedUp = true;
                }
                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                drop.solidArea.x = drop.solidAreaDefaultX;
                drop.solidArea.y = drop.solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
    * Checks if an attack hits any entities.
    * Adds info about who got hit and from which direction.
    * @param gp main game panel
    * @param entity the attacker
    * @param attack the attack area
    * @param entities list of possible targets
    * @return list of [entity index, hit direction]
    */
    public List<int[]> checkAttack(GamePanel gp, Entity entity,
        Attack attack, List<Entity> entities) {

        List<int[]> attackInfo = new ArrayList<int[]>();

        int index = 999;
        int directionDamage = 0;

        if (attack.attacking) {
            for (Entity attackeeEntity : entities) {
                // Skip invincible entities
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
                        
                    // Check overlap between attack area and target hitbox
                    if (attack.solidArea.intersects(attackeeEntity.solidArea) 
                        || attack.solidArea2.intersects(attackeeEntity.solidArea)) {

                        attackeeEntity.getHit = true;
                        index = gp.entities.indexOf(attackeeEntity);

                        int dX = entity.worldX - (int) attackeeEntity.worldXDouble;
                        int dY = entity.worldY - (int) attackeeEntity.worldYDouble;

                        directionDamage = (int) Math.toDegrees(Math.atan2(dY, dX));
                        attackeeEntity.directionDamage = directionDamage;
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

    /**
    * (Debug) Draw red boxes where collisions are happening. (Helps to see what's happening)
    * @param g2 graphics context
    */
    public void drawCollision(Graphics2D g2) {
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
