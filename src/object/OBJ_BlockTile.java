package src.object;

/**
 * A solid tile object that blocks player and entity movement.
 * (used for building walls or obstacles in the world
 */
public class OBJ_BlockTile extends SuperObject {
    
    /**
     * Create a blocking tile with collision flag so entities can’t pass through it.
     */
    public OBJ_BlockTile() {
        name = "BlockTile";
        image = null;
        collision = true;
    }
}
