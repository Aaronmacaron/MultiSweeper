package tk.aakado.multisweeper.client.connection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.AbstractConnector;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * Connector for client side that connects client and server.
 */
public class ClientConnector extends AbstractConnector {

    private final String host;
    private final int port;
    private boolean isStarted = false;
    private Socket connection;
    private PrintWriter output;
    private BufferedReader input;
    private ExecutorService queue = Executors.newFixedThreadPool(20);

    /**
     * Constructor
     * @param host The hostname of the server
     * @param port The port of the MultiSweeper Server on the Server
     */
    public ClientConnector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Starts the Connector. This connects to the server and starts listening to server.
     */
    @Override
    public Optional<Exception> start() {
        try {
            connection = new Socket(host, port);
            Logger.get(this).info("Connected to MultiSweeper ServerConnector at 'multisweeper://{}:{}'", host, port);
            output = new PrintWriter(connection.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Listen to input in new thread
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.submit(this::handleInput);

            isStarted = true;

            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    /**
     * Sends Action to Server
     * @param action The Action to be sent through the Connector.
     */
    @Override
    public void send(Action action) {
        if (!isStarted) {
            throw new IllegalStateException("Client connector is not started yet.");
        }

        output.println(action.toJson());
    }

    /**
     * Handles input of server and adds received tasks to queue.
     */
    private void handleInput() {
        try {
            String line;
            while((line = input.readLine()) != null) {
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(line).getAsJsonObject();
                JsonElement params = json.get("params");
                ActionType actionType = ActionType.valueOf(json.get("actionType").getAsString());
                queue.submit(() -> executeAllMatchingActionHandlers(actionType, params));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Executes all ActionHandlers, that match the Action.
     *
     * @param actionType The Action Type which the ActionHandler must match
     * @param json       The params in form of a JsonObject
     */
    private void executeAllMatchingActionHandlers(ActionType actionType, JsonElement json) {
        actionHandlers.stream()
                .flatMap(aClass -> Stream.of(aClass.getDeclaredMethods()))
                .filter(method -> method.isAnnotationPresent(ActionHandler.class))
                .filter(method -> actionType == method.getAnnotation(ActionHandler.class).actionType())
                .forEach(method -> this.executeMethod(method, json));
    }

    /**
     * Executes the given method.
     *
     * @param method     Method tho execute
     * @param json       Json containing relevant information.
     */
    private void executeMethod(Method method, JsonElement json) {
        try {
            ClientMessage message = new ClientMessage(this, json);
            method.invoke(method.getDeclaringClass().newInstance(), message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // Do nothing if method hasn't got the right parameters.
            Logger.get(this).warn("Could not invoke method {} because it has the wrong parameters.", e);
        } catch (InstantiationException e) {
            Logger.get(this).error("Could not instantiate action handler: " + method.getDeclaringClass().getSimpleName(), e);
        }
    }

}
