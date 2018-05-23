package tk.aakado.multisweeper.client.game;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class GameViewModel implements ViewModel {

    private SimpleObjectProperty<Duration> elapsedTime = new SimpleObjectProperty<>(Duration.ZERO);//TODO to prevent NUllPointerException Duration.ZERO is set
    private SimpleIntegerProperty numberOfPlayers = new SimpleIntegerProperty();
    private SimpleIntegerProperty remainingMines = new SimpleIntegerProperty();

    // Restarts the game
    public void restart() {
        // TODO: Implement
    }

    // Disconnects player from the game
    public void disconnect() {
        // TODO: Implement
    }

    public SimpleObjectProperty<Duration> elapsedTimeProperty() {
        return elapsedTime;
    }

    public SimpleIntegerProperty numberOfPlayersProperty() {
        return numberOfPlayers;
    }

    public SimpleIntegerProperty remainingMinesProperty() {
        return remainingMines;
    }

}
