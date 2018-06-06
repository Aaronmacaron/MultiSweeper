package tk.aakado.multisweeper.shared.connection;

import com.google.gson.JsonObject;

/**
 * Represents a Message that can be sent through the Connector
 */
public interface Message {

    /**
     * Getter for Connector
     * @return connector
     */
    Connector getConnector();

    /**
     * Getter for Params
     * @return params
     */
    JsonObject getParams();

}
