package tk.aakado.multisweeper.client.views.connection;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientConnector;
import tk.aakado.multisweeper.client.connection.Transmitter;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;
import tk.aakado.multisweeper.shared.Logger;

public class ConnectionViewModel implements ViewModel, ConnectionNotificator {

    private StringProperty connection = new SimpleStringProperty("");
    private BooleanProperty correctAddress = new SimpleBooleanProperty(false);
    private BooleanProperty rejected = new SimpleBooleanProperty(false);

    /**
     * Connects player to MultiSweeper server
     */
    void connect() {
        try {
            // The uri scheme 'multisweeper' is implicitly added so the connection string becomes a valid hierarchical
            // URI. Thus the connection string can be parsed very easily using the URI class.
            String uriString = "multisweeper://" + connection.get();
            URI uri = new URI(uriString);

            // Establish connection to server using client connector. Connects to address specified by the user.
            ClientConnector clientConnector = new ClientConnector(uri.getHost(), uri.getPort());

            // Register all Action Handlers
            clientConnector.addAllActionHandlers(Client.getInstance().getAllActionHandlers());
            Optional<Exception> exception = clientConnector.start();

            // Show error message if connecting fails
            if (exception.isPresent()) {
                rejected.setValue(true);
                Logger.get(this).warn("Could not connect to server because the hostname and port could not be found.");
                return;
            }

            // Create new Transmitter of clientConnector and store it in Client Main class
            Transmitter transmitter = new Transmitter(clientConnector);
            Client.getInstance().setTransmitter(transmitter);
        } catch (URISyntaxException e) {
            Logger.get(this).warn("The provided URI is not formatted correctly.");
            rejected.setValue(true);
        }

    }


    @Override
    public void connected() {
        Client.getInstance().changeView(GameSelectionView.class);
    }

    @Override
    public void rejected() {
        rejected.setValue(true);
    }


    public boolean isRejected() {
        return rejected.get();
    }

    public BooleanProperty rejectedProperty() {
        return rejected;
    }

    public StringProperty connectionProperty() {
        return connection;
    }

    public boolean isCorrectAddress() {
        return correctAddress.get();
    }

    public BooleanProperty correctAddressProperty() {
        return correctAddress;
    }

    public void setCorrectAddress(boolean correctAddress) {
        this.correctAddress.set(correctAddress);
    }
}
