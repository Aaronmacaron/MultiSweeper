package tk.aakado.multisweeper.server.connection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * This class is the Connector that connects the server to the client. It extends the AbstractConnector and thus
 * implements the Connector interface.
 */
public class ServerConnector extends AbstractConnector {

    private ServerSocket server;
    private List<Connection> connections = new ArrayList<>();
    private ExecutorService queue = Executors.newFixedThreadPool(20);
    private final int port;
    private boolean isStarted = false;

    /**
     * Constructor
     * @param port The port the Server should be listening on
     */
    public ServerConnector(int port) {
        this.port = port;
    }

    /**
     * This Method starts the Connector and the Server starts listening on the specified port. After this method is
     * called clients can connect to the server.
     */
    @Override
    public void start() {
        try {
            server = new ServerSocket(port);
            System.out.println("ServerConnector started! Listening for incoming connections on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        executeRepeatedly(this::listen);
        isStarted = true;
    }

    /**
     * This method executes a runnable repeatedly in an other thread. This basically is a non-blocking while true.
     * @param r The runnable that is executed repeatedly
     */
    private void executeRepeatedly(Runnable r) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(r, 1, 1, TimeUnit.MICROSECONDS);
    }

    /**
     * Listens for new connections.
     */
    private void listen() {
        try {
            Socket socket = server.accept();
            System.out.println("Client connected: " + socket.getRemoteSocketAddress());

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Connection connection = new Connection(socket, output, input);
            connections.add(connection);
            executeRepeatedly(() -> handleInput(connection));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * This method handles input of a client. This method is executed repeatedly
     * @param connection The Connection whose input should be handled
     */
    private void handleInput(Connection connection) {
        try {
            String line;
            while((line = connection.getInput().readLine()) != null) {
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(line).getAsJsonObject();
                JsonElement params = json.get("params");
                ActionType actionType = ActionType.valueOf(json.get("actionType").getAsString());
                queue.submit(() -> executeAllMatchingActionHandlers(actionType, params, connection));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Executes all ActionHandlers, that match the Action.
     * @param actionType The Action Type which the ActionHandler must match
     * @param json The params in form of a JsonObject
     * @param connection The connection which the message originates from
     */
    private void executeAllMatchingActionHandlers(ActionType actionType, JsonElement json, Connection connection) {
        actionHandlers.stream()
                .flatMap(aClass -> Stream.of(aClass.getDeclaredMethods())) // create a Stream of all declard Methods in aClass
                .filter(method -> actionType.equals(method.getAnnotation(ActionHandler.class).actionType())) // Remove Methods without the right Annotations
                .forEach(method -> this.executeMethod(method, json, connection)); // Execute all Methods
    }

    /**
     * Executes a given Method with a Message containing a json and a connection as parameter.
     *
     * @param method     Method tho execute
     * @param json       Json containing relevant informations.
     * @param connection Current connection
     */
    private void executeMethod(Method method, JsonElement json, Connection connection) {
        try {
            Message message = new ServerMessage(this, json, connection);
            method.invoke(method.getDeclaringClass().newInstance(), message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // Do nothing if method hasn't got the right parameters.
            Logger.get(this).warn("Could not invoke method %s because it has the wrong parameters.", method.getName());
        } catch (InstantiationException e) {
            Logger.get(this).error("Could not instantiate action handler: " + method.getDeclaringClass().getSimpleName(), e);
        }
    }

    /**
     * Sends a Message containing Action to all connected clients.
     * @param action The Action to be sent through the Connector.
     */
    @Override
    public void send(Action action) {
        if (!isStarted) {
            throw new IllegalStateException("Server is not started yet.");
        }

        for (Connection connection : connections) {
            connection.getOutput().println(action.toJson());
        }
    }

    /**
     * Sends a Message containing an Action to all but one Client.
     * @param action The Action to be sent through the Connector
     * @param except The Connection that should be ignored.
     */
    public void sendExcept(Action action, Connection except) {
        if (!isStarted) {
            throw new IllegalStateException("Server is not started yet.");
        }

        for (Connection connection : connections) {
            if (!connection.equals(except)) {
                connection.getOutput().println(action.toJson());
            }
        }
    }

    /**
     * Sends a Message containing an Action to one specific Client.
     * @param action The Action to be sent through the connector
     * @param to The Connection the message should be sent to
     */
    public void sendTo(Action action, Connection to) {
        if (!isStarted) {
            throw new IllegalStateException("Server is not started yet.");
        }

        to.getOutput().println(action.toJson());
    }

}
