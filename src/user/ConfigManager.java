package src.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.main.GamePanel;

/**
 * Loads and manages the user config file.
 * 
 * The config file is basically a list of key=value pairs that
 * let you change game settings (like volume, speed, etc.) without editing code.
 */
public class ConfigManager {
    // Stores all loaded key-value pairs from the config file
    public static Map<String, String> config; 

    /**
     * Reads the config file when the game starts and makes sure the file exists.
     * 
     * @param gp reference to the game (not used directly here)
     */
    public ConfigManager(GamePanel gp) {

        // Get file path of config file
        String projectRoot = System.getProperty("user.dir");
        Path path = Path.of(projectRoot, "res", "user", "config.txt");

        // Debug: see exactly where it’s looking
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                //System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }  
    }

    /**
     * Loads all pairs from the config file into memory.
     * Lines starting with '#' or empty lines are ignored (used for comments)
     *
     * @return a map containing all the loaded config values
     */
    public static Map<String, String> loadConfig() {
        String projectRoot = System.getProperty("user.dir");
        Path path = Path.of(projectRoot, "res", "user", "config.txt");
        config = new HashMap<>();
            
        try {
            for (String line : Files.readAllLines(path)) {
                
                // Ignore blank or commented-out lines
                if (line.isBlank() || line.startsWith("#")) {
                    continue; // Skip lines that are empty or start with #
                }
                String[] parts = line.split("=", 2);

                // Split "key=value" into two parts
                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }

            }
        } catch (Exception e) {
            // System.out.println("Config error: " + e.getClass().
            e.printStackTrace(); // Show the line number and reason
        }
        // System.out.println("Config loaded");
        return config;
    }

    /**
     * Returns a string from the config file/default value if not found.
     *
     * @param key the name of the config setting
     * @param def the default value if the key doesn’t exist
     * @return the stored value as a string
     */
    public static String get(String key, String def) {
        return config.getOrDefault(key, def);
    }

    /**
     * Returns a config value as an integer.
     * Falls back to the default value if its missing/invalid
     */
    public static int getInt(String key, int def) {
        try {
            return Integer.parseInt(get(key, Integer.toString(def)));
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * Returns a config value as a double.
     */
    public static double getDouble(String key, double def) {
        try {
            return Double.parseDouble(get(key, Double.toString(def)));
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * Returns a config value as a boolean (true/false). (enabling/disabling features)
     */
    public static boolean getBoolean(String key, boolean def) {
        try {
            return Boolean.parseBoolean(get(key, Boolean.toString(def)));
        } catch (Exception e) {
            return def;
        }
    } 
}
