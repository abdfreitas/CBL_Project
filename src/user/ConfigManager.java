package src.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import src.main.GamePanel;


public class ConfigManager {

    private static Path path;
    public static Map<String, String> config;


    public ConfigManager(GamePanel gp) {


        // Get file path of config file.
        String projectRoot = System.getProperty("user.dir");
        Path path = Path.of(projectRoot, "res", "user", "config.txt");


        // Debug: see exactly where it’s looking
        // System.out.println("Looking for: " + path.toAbsolutePath());

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                //System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    
        
    }

    public static Map<String, String> loadConfig() {

        String projectRoot = System.getProperty("user.dir");
        Path path = Path.of(projectRoot, "res", "user", "config.txt");


        config = new HashMap<>();
            
        

        try {
            for (String line : Files.readAllLines(path)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue; // Skip lines that are empty or start with #
                }
                System.out.println(line);
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }

            }
            
        } catch (Exception e) {
            System.out.println("Config error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace(); // show the line number and reason
        }

        System.out.println("Config loaded");

        return config;

    }

    public static String get(String key, String def) {

        return config.getOrDefault(key, def);

    }

    public static int getInt(String key, int def) {

        try {
            return Integer.parseInt(get(key, Integer.toString(def)));
        } catch (Exception e) {
            return def;
        }
    
    }

    public static double getDouble(String key, double def) {

        try {
            return Double.parseDouble(get(key, Double.toString(def)));
        } catch (Exception e) {
            return def;
        }
    
    }

    public static boolean getBoolean(String key, boolean def) {

        try {
            return Boolean.parseBoolean(get(key, Boolean.toString(def)));
        } catch (Exception e) {
            return def;
        }
    
    }
    
}
