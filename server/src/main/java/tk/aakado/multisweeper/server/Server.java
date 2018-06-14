package tk.aakado.multisweeper.server;

import tk.aakado.multisweeper.server.connection.ServerConnector;
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
     * Main method of server
     * @param args The command line args
     */
    public static void main(String[] args) {

        if (!getPort(args).isPresent()) {
            Logger.get(Server.class).error("Either you did not specify a port or the specified port is not valid.");
            System.exit(1);
        }

        getPort(args).ifPresent(port -> {
            connector = new ServerConnector(port);
            connector.start();
            Logger.get(Server.class).info("Server started on port {}", port);
        });

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
     * Getter for static connector instance.
     * @return The static connector.
     */
    public static Connector getConnector() {
        return connector;
    }

}
