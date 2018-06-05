package tk.aakado.multisweeper.server.connection;

import com.google.gson.JsonObject;
import tk.aakado.multisweeper.shared.connection.Connection;

/**
 * Represents one Message that can be sent through connector.
 */
public class Message {

    private ServerConnector server;
    private Connection sender;
    private JsonObject params;

    /**
     * Constructor
     * @param server The Connector that the Message is coming through
     * @param sender The client that sent the Message
     * @param params The params that come with the Message
     */
    public Message(ServerConnector server, Connection sender, JsonObject params) {
        this.server = server;
        this.sender = sender;
        this.params = params;
    }

    /**
     * Getter for Server
     * @return The server
     */
    public ServerConnector getServer() {
        return server;
    }

    /**
     * Getter for Sender
     * @return The sender
     */
    public Connection getSender() {
        return sender;
    }

    /**
     * Getter for Params
     * @return The Params
     */
    public JsonObject getParams() {
        return params;
    }

}
