package src.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import src.lib.CharStack;

// Javadoc

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public static boolean debugEnabled = false;
    private char history[] = new char[4];
    private char lastKey;
    public CharStack cStack = new CharStack(4);
    public boolean shiftPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

        

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

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

        if (code == KeyEvent.VK_F12) {

            debugEnabled = !debugEnabled;

        }

        if (code == KeyEvent.VK_SHIFT) {

            shiftPressed = true;

        }

        //cStack.printStack();

        
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

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

        if (code == KeyEvent.VK_SHIFT) {

            shiftPressed = false;

        }


        //cStack.printStack();
    }


  
    



    
    
    
}
