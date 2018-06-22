package tk.aakado.multisweeper.server;

/**
 * This exception signals that the arguments could not be parsed.
 */
public class ArgumentParseException extends Exception {

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
    public ArgumentParseException(String message) {
        super(message);
    }

}
