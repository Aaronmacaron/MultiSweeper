package tk.aakado.multisweeper.server.connection;

import com.google.gson.JsonObject;
import tk.aakado.multisweeper.shared.connection.AbstractMessage;
import tk.aakado.multisweeper.shared.connection.Connection;
import tk.aakado.multisweeper.shared.connection.Connector;

/**
 * Represents one ServerMessage that can be sent through connector.
 */
public class ServerMessage extends AbstractMessage {

    private Connection sender;

    /**
     * Constructor
     * @param connector The Connector that the ServerMessage is coming through
     * @param sender The client that sent the ServerMessage
     * @param params The params that come with the ServerMessage
     */
    public ServerMessage(Connector connector, JsonObject params, Connection sender) {
        super(connector, params);
        this.sender = sender;
    }

    /**
     * Getter for Sender
     * @return The sender
     */
    public Connection getSender() {
        return sender;
    }

}
