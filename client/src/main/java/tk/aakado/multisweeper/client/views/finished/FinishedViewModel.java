package tk.aakado.multisweeper.client.views.finished;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class FinishedViewModel implements ViewModel, FinishedNotificator {

    private SimpleStringProperty victory = new SimpleStringProperty();
    private SimpleIntegerProperty totalPlayers = new SimpleIntegerProperty();
    private SimpleObjectProperty<Duration> totalTime = new SimpleObjectProperty<>(Duration.ZERO);//TODO to prevent NullPointerException Duration.ZERO is set

    /**
     * Starts a new game
     */
    public void startNewGame() {
        // TODO: Implement
    }

    @Override
    public void restart() {

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

    public SimpleStringProperty victoryProperty() {
        return victory;
    }

    public SimpleIntegerProperty totalPlayersProperty() {
        return totalPlayers;
    }

    public SimpleObjectProperty<Duration> totalTimeProperty() {
        return totalTime;
    }

}
