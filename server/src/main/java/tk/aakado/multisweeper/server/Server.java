package tk.aakado.multisweeper.server;

import tk.aakado.multisweeper.server.connection.ServerConnector;
import tk.aakado.multisweeper.server.game.GameManager;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.Connector;

import java.util.Optional;

/**
 * This is the main class for the server part of the application.
 */
public class Server {

    /**
     * This is the connector instance of the serverConnector. It is static so that the game can easily get the instance
     * without passing the instance around.
     */
    private static Connector connector;

    /**
     * This is the game manager instance.It is static so that the game can easily get the instance without passing the
     * instance around.
     */
    private static GameManager gameManager;

    /**
     * Main method of server
     * @param args The command line args
     */
    public static void main(String[] args) {
        startServer(parseArguments(args));
    }

    /**
     * Starts the server.
     * @param arguments The parsed arguments
     */
    private static void startServer(MultiSweeperArguments arguments) {
        // set up game manager
        gameManager = new GameManager();
        for (int i = 0; i < arguments.getNumberOfGames(); i++) {
            gameManager.createGame();
        }

        // set up connector
        connector = new ServerConnector(arguments.getPort());
        connector.start();

    }

    public static MultiSweeperArguments parseArguments(String[] args) {
        // port
        Optional<Integer> parsedPort = getPort(args);
        if (!parsedPort.isPresent()) {
            Logger.get(Server.class).error("Either you did not specify a port or the specified port is not valid.");
            System.exit(1);
        }
        int port = parsedPort.get();

        // number of games
        int numberOfGames = getNumberOfGames(args).orElse(1);

        return new MultiSweeperArguments(port, numberOfGames);
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

        return Optional.of(numberOfGames);
    }

    /**
     * Getter for static connector instance.
     * @return The static connector.
     */
    public static Connector getConnector() {
        return connector;
    }

    /**
     * Getter for static game manager instance.
     * @return The static game manager.
     */
    public static GameManager getGameManager() {
        return gameManager;
    }

    /**
     * Simple java bean object to hold the arguments information.
     */
    public static class MultiSweeperArguments {

        private final int port;
        private final int numberOfGames;

        public MultiSweeperArguments(int port, int numberOfGames) {
            this.port = port;
            this.numberOfGames = numberOfGames;
        }

        public int getPort() {
            return port;
        }

        public int getNumberOfGames() {
            return numberOfGames;
        }
    }

}
