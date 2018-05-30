package tk.aakado.multisweeper.server.connection;

import com.google.gson.JsonObject;
import tk.aakado.multisweeper.client.connection.Connection;

public class Message {
    private ServerConnector server;
    private Connection sender;
    private JsonObject params;

    public Message(ServerConnector server, Connection sender, JsonObject params) {
        this.server = server;
        this.sender = sender;
        this.params = params;
    }

    public ServerConnector getServer() {
        return server;
    }

    public Connection getSender() {
        return sender;
    }

    public JsonObject getParams() {
        return params;
    }
}
