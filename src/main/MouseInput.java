package src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseMotionListener {

    public int mouseX, mouseY;

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        //System.out.println(mouseX);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}