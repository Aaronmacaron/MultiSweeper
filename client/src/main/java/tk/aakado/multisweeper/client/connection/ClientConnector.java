package tk.aakado.multisweeper.client.connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tk.aakado.multisweeper.shared.connection.AbstractConnector;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientConnector extends AbstractConnector {

    private final String host;
    private final int port;
    private boolean isStarted = false;
    private Socket connection;
    private PrintWriter output;
    private BufferedReader input;
    private ExecutorService queue = Executors.newFixedThreadPool(20);


    public ClientConnector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void start() {
        try {
            connection = new Socket(host, port);
            System.out.println("Connected to MultiSweeper ServerConnector");
            output = new PrintWriter(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            executeRepeatedly(this::handleInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Action action) {
        output.println(action.toJson());
    }

    /**
     * This method executes a runnable repeatedly in an other thread. This basically is a non-blocking while true.
     * @param r The runnable that is executed repeatedly
     */
    private void executeRepeatedly(Runnable r) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(r, 1, 1, TimeUnit.MICROSECONDS);
    }

    private void handleInput() {
        try {
            String line;
            while((line = input.readLine()) != null) {
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(line).getAsJsonObject();
                JsonObject params = json.getAsJsonObject("params");
                ActionType actionType = ActionType.valueOf(json.get("actionType").getAsString());
                queue.submit(() -> executeAllMatchingActionHandlers(actionType, params));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void executeAllMatchingActionHandlers(ActionType actionType, JsonObject json) {
        // TODO: Implement, too many open questions
    }

}
