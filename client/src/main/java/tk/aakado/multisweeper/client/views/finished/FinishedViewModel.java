package tk.aakado.multisweeper.client.views.finished;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.game.GameView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;

import java.time.Duration;

public class FinishedViewModel implements ViewModel, FinishedNotificator {

    private IntegerProperty totalPlayers = new SimpleIntegerProperty(0);
    private ObjectProperty<Duration> totalTime = new SimpleObjectProperty<>(Duration.ZERO);

    /**
     * Starts a new game
     */
    public void startNewGame() {
        if (Client.getInstance().isAdmin()) Client.getInstance().getTransmitter().restart();
    }

    /**
     * Reconfigure the existing game
     */
    public void sendReconfigure(){
        if (Client.getInstance().isAdmin()) Client.getInstance().changeView(ConfigurationView.class);
    }

    @Override
    public void restart() {
        Client.getInstance().changeView(GameView.class);
    }


    @Override
    public void reconfigure() {
        Client.getInstance().changeView(ConfigurationView.class);
    }

    public void leaveGame() {
        Client.getInstance().getTransmitter().leaveGame();
        Client.getInstance().changeView(GameSelectionView.class);
    }


    public IntegerProperty totalPlayersProperty() {
        return totalPlayers;
    }

    public ObjectProperty<Duration> totalTimeProperty() {
        return totalTime;
    }
}
