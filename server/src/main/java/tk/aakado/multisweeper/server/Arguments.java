package tk.aakado.multisweeper.server;

import org.apache.commons.cli.*;
import tk.aakado.multisweeper.shared.Logger;

import java.util.Optional;

/**
 * Java representation of the command line arguments.
 * It has contains the following arguments:
 * <li> port (0): The port that the server uses </li>
 * <li> numberOfGames (1): The amount of games the server creates </li>
 */
public class Arguments {

    // Default values
    private static final int DEFAULT_PORT = 41414;
    private static final int DEFAULT_NUMBER_OF_GAMES = 1;

    // Definition of options
    private static final Option PORT_OPTION = Option.builder("p")
            .longOpt("port")
            .hasArg(true)
            .desc(String.format("The port the server listens on. (Default %d)", DEFAULT_PORT))
            .required(false)
            .type(Integer.class)
            .build();
    private static final Option NUMBER_OF_GAMES_OPTION = Option.builder("n")
            .longOpt("numberOfGames")
            .hasArg(true)
            .desc(String.format("How many games the server should start. (Default %d)", DEFAULT_NUMBER_OF_GAMES))
            .required(false)
            .type(Integer.class)
            .build();

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
     * Logs warn message if values correspond to defaults.
     */
    public void warnUserOnDefault() {
        // Warn user if defaults are used
        if (getPort() == DEFAULT_PORT) {
            Logger.get(Server.class).warn("The default port is used: {}", DEFAULT_PORT);
        }
        if (getNumberOfGames() == DEFAULT_NUMBER_OF_GAMES) {
            Logger.get(Server.class).warn("The default number of games is used: {}", DEFAULT_NUMBER_OF_GAMES);
        }
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

            // Create Options object
            Options options = new Options();
            options.addOption(PORT_OPTION);
            options.addOption(NUMBER_OF_GAMES_OPTION);

            CommandLine commandLine;
            try {
                // Parse args
                commandLine = new DefaultParser().parse(options, args);
            } catch (ParseException e) {
                // If parsing fails throw argumentParseException with options so the catcher can generate help
                throw new ArgumentParseException(e.getMessage(), options);
            }

            // Store args into variables
            String portArg = commandLine.getOptionValue("p", String.valueOf(DEFAULT_PORT));
            String numberOfGamesArg = commandLine.getOptionValue(
                    "n",
                    String.valueOf(DEFAULT_NUMBER_OF_GAMES)
            );

            // port
            int port = parsePort(portArg)
                    .orElseThrow(() -> new ArgumentParseException("The specified port is not valid."));

            // number of games
            int numberOfGames = parseNumberOfGames(numberOfGamesArg)
                    .orElseThrow(() -> new ArgumentParseException("The specified value for number of games is invalid"));

            return new Arguments(port, numberOfGames);
        }

        /**
         * Parses port by string
         * @param portArg The string containing the port
         * @return Optional of port. Empty if not available or valid.
         */
        public static Optional<Integer> parsePort(String portArg) {
            int port;
            try {
                // Convert portArg to integer
                port = Integer.parseInt(portArg);
            } catch (NumberFormatException exception) {
                return Optional.empty();
            }

            if (port > 0 && port < 0x10000) { // Check if invalid port (Between 0 and 65536)
                return Optional.of(port);
            }

            // Else return empty
            return Optional.empty();
        }

        /**
         * Gets number of games by args
         * @param numberOfGamesArg The args array
         * @return Optional of number of games. Empty if not available or valid.
         */
        public static Optional<Integer> parseNumberOfGames(String numberOfGamesArg) {
            int numberOfGames;
            try {
                // Convert portArg to integer
                numberOfGames = Integer.parseInt(numberOfGamesArg);
            } catch (NumberFormatException e) {
                return Optional.empty();
            }

            if (numberOfGames < 1) { // Minimum one game
                return Optional.empty();
            }

            return Optional.of(numberOfGames);
        }

    }

}
