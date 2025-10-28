package src.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import src.lib.CharStack;

/**
 * Handles keyboard/mouse input for the player movement and action.
 * 
 * Keeps track of which movement and action keys are currently pressed
 * and stores recent key order in a small character stack to check direction.
 */
public class KeyHandler implements KeyListener {
    // Movement keys
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public static boolean debugEnabled = false;
    // Stack that tracks recent movement key order (WASD)
    public CharStack cStack = new CharStack(4);
    // Action flags
    public boolean shiftPressed = false;
    public boolean attackPressed = false;
    public boolean boost = false;
    public boolean interactPressed = false;
    public boolean dodgePressed = false;

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, handled via pressed/released only
        // Must be included for KeyHandler
    }

    // Player movement
    @Override
    public void keyPressed(KeyEvent e) {
        handlePressedMovementKeys(e);
        handlePressedActionKeys(e);
    }
    
    // Track movement when key is pressed
    private void handlePressedMovementKeys(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) { 
            cStack.push('w'); 
            upPressed = true; 
        } else if (code == KeyEvent.VK_A) { 
            cStack.push('a'); 
            leftPressed = true; 
        } else if (code == KeyEvent.VK_S) { 
            cStack.push('s'); 
            downPressed = true; 
        } else if (code == KeyEvent.VK_D) { 
            cStack.push('d'); 
            rightPressed = true; 
        }
    }

    // Track movement when key is pressed
    private void handlePressedActionKeys(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_F12) {
            debugEnabled = !debugEnabled;
        } else if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        } else if (code == KeyEvent.VK_SPACE) {
            dodgePressed = true;
        } else if (code == KeyEvent.VK_B) {
            boost = true;
        }
    }    

    @Override
    public void keyReleased(KeyEvent e) {
        handleReleasedMovementKeys(e);
        handleReleasedActionKeys(e);
    }
    
    // Stop tracking movement when key is released
    private void handleReleasedMovementKeys(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            cStack.pop('w');
            upPressed = false; 
        } else if (code == KeyEvent.VK_A) { 
            cStack.pop('a');
            leftPressed = false;
        } else if (code == KeyEvent.VK_S) { 
            cStack.pop('s');
            downPressed = false; 
        } else if (code == KeyEvent.VK_D) { 
            cStack.pop('d');
            rightPressed = false;
        }
    }

    // Stop action keys when released
    private void handleReleasedActionKeys(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_F12) {
            debugEnabled = !debugEnabled;
        } else if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        } else if (code == KeyEvent.VK_SPACE) {
            dodgePressed = false;
        } else if (code == KeyEvent.VK_B) {
            boost = false;
        }

    } 

}
