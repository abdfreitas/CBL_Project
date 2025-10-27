package src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles mouse clicks from the keyboard 
 * 
 * Left click is used for attacking, right click for interacting.
 * Links to KeyHandler so mouse actions can trigger the same input flags.
 */
public class MouseClickInput implements MouseListener {
    public boolean leftPressed = false;
    KeyHandler keyH;

    /**
     * Connects the mouse input to the KeyHandler so clicks can
     * set the same booleans used by the keyboard actions.
     * 
     * @param keyH the shared KeyHandler instance
     */
    public MouseClickInput(KeyHandler keyH) {
        this.keyH = keyH;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Left click for attack
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
            keyH.attack_Pressed = true;
        }
        // Right click for interacting
        if (e.getButton() == MouseEvent.BUTTON3) {
            keyH.interactPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Stop attacking/interacting when button is released
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
            keyH.attack_Pressed = false;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            keyH.interactPressed = false;
        }
    }

    // These menthods aren't used but must be included for MouseListener
    @Override public void mouseClicked(MouseEvent e) {}

    @Override public void mouseEntered(MouseEvent e) {}

    @Override public void mouseExited(MouseEvent e) {}
}
