package tk.aakado.multisweeper.client.finished;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.Duration;

public class FinishedViewModel implements ViewModel {

    private SimpleStringProperty victoryString = new SimpleStringProperty();
    private SimpleIntegerProperty totalPlayers = new SimpleIntegerProperty();
    private SimpleObjectProperty<Duration> totalTime = new SimpleObjectProperty<>();

    public SimpleStringProperty victoryStringProperty() {
        return victoryString;
    }

    public SimpleIntegerProperty totalPlayersProperty() {
        return totalPlayers;
    }

    public SimpleObjectProperty<Duration> totalTimeProperty() {
        return totalTime;
    }

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

}
