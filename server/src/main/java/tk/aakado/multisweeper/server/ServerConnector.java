package tk.aakado.multisweeper.server;

import com.google.gson.JsonObject;
import tk.aakado.multisweeper.client.AbstractConnector;
import tk.aakado.multisweeper.client.Action;
import tk.aakado.multisweeper.client.ActionType;
import tk.aakado.multisweeper.client.Connection;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnector extends AbstractConnector {

    private ServerSocket server;
    private List<Connection> connections = new ArrayList<>();
    private List<Class> actionHandlers = new ArrayList<>();
    private ExecutorService queue = Executors.newFixedThreadPool(20);
    private final int port;
    private boolean isStarted = false;

    public ServerConnector(int port) {
        this.port = port;
    }

    @Override
    public void start() {

    }

    private void executeRepeatedly(Runnable r) {

    }

    private void listen() {

    }

    private void handleInput(Connection connection) {

    }

    private void executeAllMatchingActionHandlers(ActionType actionType, JsonObject json, Connection connection) {

    }

    @Override
    public void send(Action action) {

    }

    public void sendExcept(Action action, Connection except) {

    }

}
