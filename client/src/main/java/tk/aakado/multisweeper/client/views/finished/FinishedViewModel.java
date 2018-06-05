package tk.aakado.multisweeper.client.views.finished;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;

public class FinishedViewModel implements ViewModel {

    private BooleanProperty victory = new SimpleBooleanProperty(false);
    private IntegerProperty totalPlayers = new SimpleIntegerProperty(0);
    private ObjectProperty<Duration> totalTime = new SimpleObjectProperty<>(Duration.ZERO);//TODO to prevent NullPointerException Duration.ZERO is set

    /**
     * Starts a new game
     */
    public void startNewGame() {
        // TODO: Implement
    }

    /**
     * Reconfigure the game settings
     */
    public void reconfigure() {
        // TODO: Implement
    }

    /**
     * Disconnect from the server
     */
    public void disconnect() {
        // TODO: Implement
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
