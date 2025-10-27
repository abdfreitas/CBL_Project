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
    public boolean attack_Pressed = false;
    public boolean boost = false;
    public boolean interactPressed = false;
    public boolean dodgePressed = false;

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, handled via pressed/released only
    }

    // Player movement
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // Movement
        if (code == KeyEvent.VK_W) {
            cStack.push('w');
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            cStack.push('a');
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            cStack.push('s');
            downPressed = true; 
        }
        if (code == KeyEvent.VK_D) {
            cStack.push('d');
            rightPressed = true;
        }
        // Debug
        if (code == KeyEvent.VK_F12) {
            debugEnabled = !debugEnabled;
        }
        // Actions
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            dodgePressed = true;
        }
        if (code == KeyEvent.VK_B) {
            boost = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Stop tracking movement when key is released
        if (code == KeyEvent.VK_W) {
            cStack.pop('w');
            upPressed = false; 
        }
        if (code == KeyEvent.VK_A) {
            cStack.pop('a');
            leftPressed = false;            
        }
        if (code == KeyEvent.VK_S) {
            cStack.pop('s');
            downPressed = false;  
        }
        if (code == KeyEvent.VK_D) {
            cStack.pop('d');
            rightPressed = false;
        }

        // Stop action keys when released
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            dodgePressed = false;
        }
        if (code == KeyEvent.VK_B) {
            boost = false;
        }
    }
}
