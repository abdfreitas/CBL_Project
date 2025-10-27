package src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/** ADD COMMENT. */
public class MouseInput implements MouseMotionListener {
    public int mouseX; 
    public int mouseY;
    
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}