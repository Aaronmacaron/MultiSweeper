package tk.aakado.multisweeper.client.views.game;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import tk.aakado.multisweeper.logic.Field;

public class GameViewModel implements ViewModel, GameNotificator {

    private SimpleObjectProperty<Duration> elapsedTime = new SimpleObjectProperty<>(Duration.ZERO);//TODO to prevent NUllPointerException Duration.ZERO is set
    private SimpleIntegerProperty numberOfPlayers = new SimpleIntegerProperty();
    private SimpleIntegerProperty remainingMines = new SimpleIntegerProperty();

    @Override
    public void playerDisconnected(String players) {

    }

    @Override
    public void playerConnected(String players) {

    }

    // Restarts the game
    public void restart() {
        // TODO: Implement
    }

    @Override
    public void updateField(Field field) {

    }

    @Override
    public void finished() {

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
