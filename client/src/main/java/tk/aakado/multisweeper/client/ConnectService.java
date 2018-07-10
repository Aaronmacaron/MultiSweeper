package tk.aakado.multisweeper.client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import tk.aakado.multisweeper.client.connection.ClientConnector;
import tk.aakado.multisweeper.client.connection.Transmitter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class ConnectService extends Service<Void> {

    private static int DEFAULT_PORT = 41414;

    private URI uri;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Establish connection to server using client connector. Connects to address specified by the user.
                ClientConnector clientConnector = new ClientConnector(uri.getHost(), uri.getPort());

                // Register all Action Handlers
                clientConnector.addAllActionHandlers(Client.getInstance().getAllActionHandlers());
                Optional<Exception> exception = clientConnector.start();

                // Fail if connector had exception
                if (exception.isPresent()) {
                    failed();
                }

                // Create new Transmitter of clientConnector and store it in Client Main class
                Transmitter transmitter = new Transmitter(clientConnector);
                Client.getInstance().setTransmitter(transmitter);

                // if task has been cancelled, do not connect transmitter to server
                if (isCancelled()) {
                    return null;
                }

                // Connect to server
                transmitter.connect();
                succeeded();
                return null;
            }
        };
    }

    public void setAddress(String address) throws URISyntaxException {
        // The uri scheme 'multisweeper' is implicitly added so the connection string becomes a valid hierarchical
        // URI. Thus the connection string can be parsed very easily using the URI class.
        String uriString = "multisweeper://" + address;
        URI uri = new URI(uriString);

        // Set to default port when none specified
        if (uri.getPort() == -1) {
            uri = new URI(uri.getScheme(), null, uri.getHost(), DEFAULT_PORT, null, null, null);
        }

        this.uri = uri;
    }

}
