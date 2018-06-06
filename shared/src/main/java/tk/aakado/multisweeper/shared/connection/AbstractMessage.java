package tk.aakado.multisweeper.shared.connection;

import com.google.gson.JsonObject;

/**
 * Abstract implementation of Message Interface
 */
abstract public class AbstractMessage implements Message {

    private Connector connector;
    private JsonObject params;

    /**
     * Constructor
     * @param connector The connector
     * @param params The params
     */
    public AbstractMessage(Connector connector, JsonObject params) {
        this.connector = connector;
        this.params = params;
    }

    /**
     * @see Message#getConnector()
     * @return connector
     */
    @Override
    public Connector getConnector() {
        return null;
    }

    /**
     * @see Message#getParams()
     * @return params
     */
    @Override
    public JsonObject getParams() {
        return null;
    }

}
