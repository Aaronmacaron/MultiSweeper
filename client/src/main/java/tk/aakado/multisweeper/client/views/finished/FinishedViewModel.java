package tk.aakado.multisweeper.client.views.finished;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.game.GameView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;

import java.time.Duration;

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
        victory.bind(Client.getInstance().getGameProperties().victoryProperty());
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

    public void leaveGame() {
        Client.getInstance().getTransmitter().leaveGame();
        Client.getInstance().changeView(GameSelectionView.class);
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

    public void setVictory(boolean victory) {
        this.victory.set(victory);
    }
}
