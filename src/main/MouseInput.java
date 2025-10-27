package src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Tracks the mouse position on the screen.
 * 
 * (used for things like aiming, hovering over flower or chickens,
 * or showing where the player is pointing)
 */
public class MouseInput implements MouseMotionListener {
    public int mouseX; 
    public int mouseY;
    
    @Override
    public void mouseMoved(MouseEvent e) {
        // Update position whenever the mouse moves
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Also update while dragging
        mouseX = e.getX();
        mouseY = e.getY();
    }
}