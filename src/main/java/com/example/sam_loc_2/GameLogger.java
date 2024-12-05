package com.example.sam_loc_2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class GameLogger {
    private static final Logger logger = Logger.getLogger(GameLogger.class.getName());
    private List<String> gameLog = new ArrayList<>();

    public void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = timestamp + ": " + message;
        gameLog.add(logEntry);
        logger.info(logEntry);
    }

    public void saveLogToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            gameLog.forEach(writer::println);
        } catch (FileNotFoundException e) {
            logger.severe("Không thể lưu file log: " + e.getMessage());
        }
    }

    public List<String> getGameLog() {
        return new ArrayList<>(gameLog);
    }
}
