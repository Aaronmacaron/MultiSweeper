package tk.aakado.multisweeper.client;

import com.google.gson.JsonObject;

public class ClientConnector extends AbstractConnector {

    final private String host;
    final private int port;
    private boolean isStarted = false;

    public ClientConnector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void send(Action action) {

    }

    @Override
    public void start() {

    }

    private void handleInput(Connection connection) {

    }

    private void executeAllMatchingActionHandlers(ActionType actionType, JsonObject json, Connection connection) {

    }
}
