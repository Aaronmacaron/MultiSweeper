package tk.aakado.multisweeper.shared;

import org.slf4j.LoggerFactory;

/**
 * Logger utility class.
 */
public class Logger {

    private Logger() {
        // prevent instantiation
    }

    /**
     * Get the logger for the given object.
     * @param obj The caller
     * @return The logger
     */
    public static org.slf4j.Logger get(Object obj) {
        return get(obj.getClass());
    }

    /**
     * Get the logger for a class.
     * @param clazz The caller
     * @return The logger
     */
    public static org.slf4j.Logger get(Class clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
