package src.main;

import javax.swing.JFrame;

/**
 * Creates the main window, adds the GamePanel to it,
 * and starts the game loop.
 */
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Bloom or Doom");

        // Create the main game panel and add it to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // Size window to fit panel exactly
        window.setLocationRelativeTo(null); // Center on screen
        window.setVisible(true);

        // Prepare the game and start the main loop
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}