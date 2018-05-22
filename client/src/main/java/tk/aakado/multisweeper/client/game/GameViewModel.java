package tk.aakado.multisweeper.client.game;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;

public class GameViewModel implements ViewModel {

    private SimpleObjectProperty<Duration> elapsedTime = new SimpleObjectProperty<>();
    private SimpleIntegerProperty numberOfPlayers = new SimpleIntegerProperty();
    private SimpleIntegerProperty remainingMines = new SimpleIntegerProperty();

    public SimpleObjectProperty<Duration> elapsedTimeProperty() {
        return elapsedTime;
    }

    public SimpleIntegerProperty numberOfPlayersProperty() {
        return numberOfPlayers;
    }

    public SimpleIntegerProperty remainingMinesProperty() {
        return remainingMines;
    }

    // Restarts the game
    public void restart() {
        // TODO: Implement
    }

    // Disconnects player from the game
    public void disconnect() {
        // TODO: Implement
    }

}
