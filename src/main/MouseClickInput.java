package src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickInput implements MouseListener {

    public boolean leftPressed = false;

    KeyHandler keyH;

    public MouseClickInput(KeyHandler keyH){

        this.keyH = keyH;


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
            keyH.attack_Pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
            keyH.attack_Pressed = false;
        }
    }

    // You can leave the other methods empty
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
