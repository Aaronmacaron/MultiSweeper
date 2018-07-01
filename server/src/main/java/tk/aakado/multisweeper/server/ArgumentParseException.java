package tk.aakado.multisweeper.server;

import org.apache.commons.cli.Options;

/**
 * This exception signals that the arguments could not be parsed.
 */
public class ArgumentParseException extends Exception {

    private Options options;

    /**
     * Constructs exception without message.
     */
    public ArgumentParseException() {
        super();
    }

    /**
     * Constructs exception with a detailed message.
     * @param message The message of the exception.
     */
    public ArgumentParseException(String message, Options options) {
        super(message);
        this.options = options;
    }

    /**
     * Constructs exception with a detailed message.
     * @param message The message of the exception.
     */
    public ArgumentParseException(String message) {
        super(message);
    }

    /**
     * Getter for options
     * @return options
     */
    public Options getOptions() {
        return options;
    }

}
