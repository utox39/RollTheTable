package util;

import java.util.logging.*;

public class ServerLogger {
    public static final Logger logger = Logger.getLogger("Server Logger");

    private ServerLogger() {}

    public static void severe(String className, String message) {
        logger.log(Level.SEVERE, "[{0}] {1}", new Object[]{className, message});
    }

    public static void warning(String className, String message) {
        logger.log(Level.WARNING, "[{0}] {1}", new Object[]{className, message});
    }

    public static void info(String className, String message) {
        logger.log(Level.INFO, "[{0}] {1}", new Object[]{className, message});
    }
}
