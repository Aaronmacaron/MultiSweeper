package tk.aakado.multisweeper.shared.connection;

import com.google.gson.JsonElement;

/**
 * Abstract implementation of Message Interface
 */
abstract public class AbstractMessage implements Message {

    private Connector connector;
    private JsonElement params;

    /**
     * Constructor
     * @param connector The connector
     * @param params The params
     */
    public AbstractMessage(Connector connector, JsonElement params) {
        this.connector = connector;
        this.params = params;
    }

    /**
     * @see Message#getParams()
     * @return params
     */
    @Override
    public JsonElement getParams() {
        return params;
    }

    /**
     * @see Message#getConnector()
     */
    @Override
    public Connector getConnector() {
        return connector;
    }

}
