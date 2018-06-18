package tk.aakado.multisweeper.shared.connection;

import java.util.Collection;

/**
 * Interface for all Connectors. A Connector is a piece of the MultiSweeper Software system which connects the server
 * and the client.
 */
public interface Connector {

    /**
     * Adds new ActionHandler to Connector.
     * @param clazz The ActionHandler to add to the Connector.
     * @see ActionHandler
     */
    void addActionHandler(Class clazz);

    /**
     * Adds a collection of ActionHandlers to Connector.
     * @param classes The Collection of ActionHandlers to add to the Connector.
     * @see ActionHandler
     * @see Collection
     */
    void addAllActionHandlers(Collection<Class> classes);

    /**
     * Sends Action through Connector.
     * @param action The Action to be sent through the Connector.
     */
    void send(Action action);

    /**
     * Starts the Connector. This can for example start a socket connection with the Connector on the other side of the
     * System.
     */
    void start();

}
