package tk.aakado.multisweeper.client.connection;

import com.google.gson.JsonElement;
import tk.aakado.multisweeper.shared.connection.AbstractMessage;

public class ClientMessage extends AbstractMessage {

    /**
     * Constructor
     * @param connector The connector
     * @param params    The params
     */
    public ClientMessage(ClientConnector connector, JsonElement params) {
        super(connector, params);
    }

    @Override
    public ClientConnector getConnector() {
        return (ClientConnector) super.getConnector();
    }
}
