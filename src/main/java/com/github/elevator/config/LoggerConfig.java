package main.java.com.github.elevator.config;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    private static FileHandler fileHandler = null;

    public static Logger getLogger(String className) {
        if (fileHandler == null) {
            try {
                fileHandler = new FileHandler("logs/elevator.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(Level.INFO);
            } catch (IOException e) {
                System.err.println("Failed to initialize logger: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        Logger logger = Logger.getLogger(className);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);
        return logger;
    }
}
