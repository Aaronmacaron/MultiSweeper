package tk.aakado.multisweeper.server;

import tk.aakado.multisweeper.shared.Logger;

import java.util.Optional;

/**
 * Java representation of the command line arguments.
 * It has contains the following arguments:
 * <li> port (0): The port that the server uses </li>
 * <li> numberOfGames (1): The amount of games the server creates </li>
 */
public class Arguments {

    private static final int DEFAULT_NUMBER_OF_GAMES = 1;

    private final int port;
    private final int numberOfGames;

    /**
     * Private constructor that is only used by the {@link Parser}.
     */
    private Arguments(int port, int numberOfGames) {
        this.port = port;
        this.numberOfGames = numberOfGames;
    }

    public int getPort() {
        return port;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    /**
     * Parser for the command line args.
     * This parser lazily parses the needed arguments into a {@link Arguments} object.
     */
    public static class Parser {

        private final String[] args;

        public Parser(String[] args) {
            this.args = args;
        }

        /**
         * Parses the args.
         * @return The parsed arguments as {@link Arguments}
         * @throws ArgumentParseException Thrown if unable to parse arguments
         */
        public Arguments parse() throws ArgumentParseException {
            // port
            Optional<Integer> parsedPort = getPort(this.args);
            if (!parsedPort.isPresent()) {
                throw new ArgumentParseException("Either you did not specify a port or the specified port is not valid.");
            }
            int port = parsedPort.get();

            // number of games
            if (this.args.length < 2) {
                Logger.get(this).info("No value has been specified in the arguments for the number of games. " +
                        "Using the default value ({}).", DEFAULT_NUMBER_OF_GAMES);
                return new Arguments(port, DEFAULT_NUMBER_OF_GAMES);
            }

            int numberOfGames = getNumberOfGames(args)
                    .orElseThrow(() -> new ArgumentParseException("The specified value for number of games is invalid"));

            return new Arguments(port, numberOfGames);
        }

        /**
         * Gets port by args
         * @param args The args array
         * @return Optional of port. Empty if not available or valid.
         */
        public static Optional<Integer> getPort(String[] args) {
            if (args.length < 1) {
                return Optional.empty();
            }

            String portString = args[0];
            int port;

            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException exception) {
                return Optional.empty();
            }

            if (port > 0 && port < 0x10000) { // Check if invalid port
                return Optional.of(port);
            }

            return Optional.empty();
        }

        /**
         * Gets number of games by args
         * @param args The args array
         * @return Optional of number of games. Empty if not available or valid.
         */
        public static Optional<Integer> getNumberOfGames(String[] args) {
            if (args.length < 2) {
                return Optional.empty();
            }

            String gamesString = args[1];
            int numberOfGames;

            try {
                numberOfGames = Integer.parseInt(gamesString);
            } catch (NumberFormatException e) {
                Logger.get(Server.class).warn("The second parameter (number of games) is not a valid number. " +
                        "Using the default value.");
                return Optional.empty();
            }

            if (numberOfGames < 1) {
                return Optional.empty();
            }

            return Optional.of(numberOfGames);
        }

    }

}
