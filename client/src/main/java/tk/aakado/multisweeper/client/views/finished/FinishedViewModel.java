package tk.aakado.multisweeper.client.views.finished;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import tk.aakado.multisweeper.client.App;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.client.views.game.GameView;

public class FinishedViewModel implements ViewModel, FinishedNotificator {

    private BooleanProperty victory = new SimpleBooleanProperty(false);
    private IntegerProperty totalPlayers = new SimpleIntegerProperty(0);
    private ObjectProperty<Duration> totalTime = new SimpleObjectProperty<>(Duration.ZERO);//TODO to prevent NullPointerException Duration.ZERO is set

    /**
     * Starts a new game
     */
    public void startNewGame() {
        App.getInstance().getTransmitter().restart();
    }

    @Override
    public void restart() {
        App.getInstance().changeView(GameView.class);
    }


    @Override
    public void reconfigure() {
        App.getInstance().changeView(ConfigurationView.class);
    }


    @Override
    public void disconnect() {
        App.getInstance().getTransmitter().disconnect();
        App.getInstance().changeView(ConnectionView.class);
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

}
