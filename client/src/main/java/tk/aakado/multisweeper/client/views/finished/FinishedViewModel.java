package tk.aakado.multisweeper.client.views.finished;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.client.views.game.GameView;

public class FinishedViewModel implements ViewModel, FinishedNotificator {

    private BooleanProperty victory = new SimpleBooleanProperty(false);
    private IntegerProperty totalPlayers = new SimpleIntegerProperty(0);
    private ObjectProperty<Duration> totalTime = new SimpleObjectProperty<>(Duration.ZERO);
    private BooleanProperty admin = new SimpleBooleanProperty(false);

    /**
     * Creates a Bidirectional binding to the admin property
     */
    public FinishedViewModel() {
        admin.bindBidirectional(Client.getInstance().getGameProperties().adminProperty());
    }

    /**
     * Starts a new game
     */
    public void startNewGame() {
        if (admin.get()) Client.getInstance().getTransmitter().restart();
    }

    /**
     * Reconfigure the existing game
     */
    public void sendReconfigure(){
        if (admin.get()) Client.getInstance().changeView(ConfigurationView.class);
    }

    @Override
    public void restart() {
        Client.getInstance().changeView(GameView.class);
    }


    @Override
    public void reconfigure() {
        Client.getInstance().changeView(ConfigurationView.class);
    }


    @Override
    public void disconnect() {
        Client.getInstance().getTransmitter().disconnect();
        Client.getInstance().changeView(ConnectionView.class);
    }

    public BooleanProperty victoryProperty() {
        return victory;
    }

    public IntegerProperty totalPlayersProperty() {
        return totalPlayers;
    }

    public ObjectProperty<Duration> totalTimeProperty() {
        return totalTime;
    }

    public boolean isAdmin() {
        return admin.get();
    }

    public BooleanProperty adminProperty() {
        return admin;
    }
}
