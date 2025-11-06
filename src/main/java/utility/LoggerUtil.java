package utility;

import java.util.logging.Logger;
import java.util.logging.Level;

public class LoggerUtil {
    private static final Logger LOGGER = Logger.getLogger("GlobalLogger");

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void warn(String msg) {
        LOGGER.warning(msg);
    }

    public static void error(String msg, Throwable t) {
        LOGGER.log(Level.SEVERE, msg, t);
    }
}
