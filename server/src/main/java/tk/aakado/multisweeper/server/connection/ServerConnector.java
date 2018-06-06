package tk.aakado.multisweeper.server.connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tk.aakado.multisweeper.shared.connection.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This class is the Connector that connects the server to the client. It extends the AbstractConnector and thus
 * implements the Connector interface.
 */
public class ServerConnector extends AbstractConnector {

    private ServerSocket server;
    private List<Connection> connections = new ArrayList<>();
    private List<Class> actionHandlers = new ArrayList<>();
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
                JsonObject params = json.getAsJsonObject("params");
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
    private void executeAllMatchingActionHandlers(ActionType actionType, JsonObject json, Connection connection) {
        getAllMatchingMethods(actionHandlers, actionType).forEach(method -> {
            try {
                ServerMessage serverMessage = new ServerMessage(this, json, connection);
                method.invoke(method.getDeclaringClass().newInstance(), serverMessage);
            } catch (IllegalAccessException | InvocationTargetException e) {
                // Do nothing if method hasn't got the right parameters.
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Gets all ActionHandler Methods of all actionHandlers and an ActionType
     * @param actionHandlers The ActionHandlers to get methods from
     * @param actionType The ActionType to Match.
     * @return A Collection of all matched methods
     */
    private Collection<Method> getAllMatchingMethods(Collection<Class> actionHandlers, ActionType actionType) {
        return actionHandlers.stream().collect(
                ArrayList::new,
                (methods, aClass) -> methods.addAll(Arrays.stream(aClass.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(ActionHandler.class))
                        .filter(method -> method.getAnnotation(ActionHandler.class).actionType().equals(actionType))
                        .collect(Collectors.toList())),
                ArrayList::addAll
        );
    }

    /**
     * Sends a ServerMessage containing Action to all connected clients.
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
     * Sends a ServerMessage containing an Action to all but one Client.
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

}
