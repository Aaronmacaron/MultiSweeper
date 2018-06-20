package tk.aakado.multisweeper.client.views.connection;

import java.net.URI;
import java.net.URISyntaxException;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientConnector;
import tk.aakado.multisweeper.client.connection.Transmitter;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationView;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
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
            URI uri = new URI(connection.get());
            ClientConnector clientConnector = new ClientConnector(uri.getHost(), uri.getPort());
            clientConnector.start(); //TODO: catch on potential fail of connecting to server
            Transmitter transmitter = new Transmitter(clientConnector);
            Client.getInstance().setTransmitter(transmitter);
            transmitter.connect();
        } catch (URISyntaxException e) {
            Logger.get(this).warn("The provided URI is not formatted correctly.");
            // TODO: Show error message to user.
        }

    }


    @Override
    public void connected(boolean authRequired, boolean isAdmin) {
        if (authRequired && !isAdmin) {
            Client.getInstance().changeView(AuthenticationView.class);
        } else {
            Client.getInstance().changeView(ConfigurationView.class);
        }
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
