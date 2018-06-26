package tk.aakado.multisweeper.server;

import tk.aakado.multisweeper.server.connection.ServerConnector;
import tk.aakado.multisweeper.server.connection.handler.*;
import tk.aakado.multisweeper.server.game.GameManager;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.Connector;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * This is the main class for the server part of the application.
 *
 * @author Aaron Ebnöther
 * @author Dominik Strässle
 * @author Kay Mattern
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
        Arguments.Parser parser = new Arguments.Parser(args);
        try {
            startServer(parser.parse());
        } catch (ArgumentParseException e) {
            Logger.get(Server.class).error("Server has not been started as the arguments could not be parsed: ", e);
            System.exit(1);
        }
    }

    /**
     * Starts the server.
     * @param arguments The parsed arguments. First argument is the server port. This argument is necessary. The second
     *                  argument is the number of concurrent games that should be running on the server. This argument
     *                  is not necessary and the default is one game.
     */
    private static void startServer(Arguments arguments) {
        // set up game manager
        gameManager = new GameManager();
        for (int i = 0; i < arguments.getNumberOfGames(); i++) {
            gameManager.createGame();
        }

        // set up connector
        connector = new ServerConnector(arguments.getPort());

        registerActionHandlers();

        Optional<Exception> exception = connector.start();

        // Handle potential error
        if (exception.isPresent()) {
            Logger.get(Server.class).error("Error occurred while starting server, exiting now: {}",
                    exception.get().getMessage());
            System.exit(1);
        }
    }

    /**
     * Registers all action handlers which are needed from start on to the connector of the server.
     */
    private static void registerActionHandlers() {
        Collection<Class> actionHandlers = Arrays.asList(
                JoinGameHandler.class,
                ConnectHandler.class,
                DisconnectedHandler.class,
                ClickHandler.class,
                ConfigurationHandler.class
        );

        connector.addAllActionHandlers(actionHandlers);
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


}
