package tk.aakado.multisweeper.client.views.connection;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import de.saxsys.mvvmfx.ViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.ConnectService;
import tk.aakado.multisweeper.client.connection.ClientConnector;
import tk.aakado.multisweeper.client.connection.Transmitter;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;
import tk.aakado.multisweeper.shared.Logger;

public class ConnectionViewModel implements ViewModel, ConnectionNotificator {

    private static int DEFAULT_PORT = 41414;

    private StringProperty connection = new SimpleStringProperty("");
    private BooleanProperty correctAddress = new SimpleBooleanProperty(false);
    private BooleanProperty rejected = new SimpleBooleanProperty(false);
    private BooleanProperty connecting = new SimpleBooleanProperty(false);

    private ConnectService connectService = new ConnectService();

    /**
     * Connects player to MultiSweeper server
     */
    public void connect() {
        try {
            this.connectService.setAddress(connection.get());
            this.connecting.set(true);
            this.connectService.restart();
            this.connectService.setOnSucceeded(event -> this.connecting.set(false));
            this.connectService.setOnFailed(event -> connectionFail());
        } catch (URISyntaxException e) {
            Logger.get(this).warn("The provided URI is not formatted correctly.");
            rejected.setValue(true);
        }
    }

    /**
     * Handles a connection fail.
     */
    private void connectionFail() {
        rejected();
        this.connecting.set(false);
        KeyFrame hideRejected = new KeyFrame(Duration.seconds(4), e -> this.rejected.set(false));
        new Timeline(hideRejected).play();
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

    public boolean isConnecting() {
        return connecting.get();
    }

    public BooleanProperty connectingProperty() {
        return connecting;
    }

    public void setConnecting(boolean connecting) {
        this.connecting.set(connecting);
    }
}
