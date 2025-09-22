package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Report {
    private final Logger logger;

    private Report(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    // Factory method for creating Report instances
    public static Report getLogger(Class<?> clazz) {
        return new Report(clazz);
    }

    public void info(String message, Object... params) {
        logger.info(message, params);
        // Future: ExtentReports.log(Status.INFO, message);
    }

    public void debug(String message, Object... params) {
        logger.debug(message, params);
    }

    public void warn(String message, Object... params) {
        logger.warn(message, params);
    }

    public void error(String message, Object... params) {
        logger.error(message, params);
    }

    public void error(String message, Throwable t) {
        logger.error(message, t);
    }

}
