package tk.aakado.multisweeper.client.connection;

import com.google.gson.JsonObject;
import tk.aakado.multisweeper.shared.connection.AbstractMessage;
import tk.aakado.multisweeper.shared.connection.Connector;

public class ClientMessage extends AbstractMessage {

    /**
     * Constructor
     * @param connector The connector
     * @param params    The params
     */
    public ClientMessage(Connector connector, JsonObject params) {
        super(connector, params);
    }

}
