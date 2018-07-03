package tk.aakado.multisweeper.client.views.connection;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.regex.Pattern;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.validation.FunctionBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
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

    private static int DEFAULT_PORT = 41414;

    /**
     * Regex that checks if string is hostname or ip address
     */
    private static final Pattern pattern = Pattern.compile("(^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.)" +
            "{3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])|^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-" +
            "9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9]))(:([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|" +
            "65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]))?$");

    private StringProperty connection = new SimpleStringProperty("");

    private BooleanProperty rejected = new SimpleBooleanProperty(false);

    private FunctionBasedValidator<String> addressValidator;


    /**
     * Setup all validations
     */
    public ConnectionViewModel() {
        addressValidator = new FunctionBasedValidator<>(connection,
                str -> pattern.matcher(str).matches(),
                ValidationMessage.error("Invalid Hostname"));
    }

    /**
     * Connects player to MultiSweeper server
     */
    void connect() {
        try {
            // The uri scheme 'multisweeper' is implicitly added so the connection string becomes a valid hierarchical
            // URI. Thus the connection string can be parsed very easily using the URI class.
            String uriString = "multisweeper://" + connection.get();
            URI uri = new URI(uriString);

            // Set to default port when none specified
            if (uri.getPort() == -1) {
                uri = new URI(uri.getScheme(), null, uri.getHost(), DEFAULT_PORT, null, null, null);
            }

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

            // Connect to server
            transmitter.connect();
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

    public ValidationStatus addressValidation(){
        return addressValidator.getValidationStatus();
    }

}
