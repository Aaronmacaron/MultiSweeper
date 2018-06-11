package tk.aakado.multisweeper.client.views.game;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class GameViewModel implements ViewModel, GameNotificator {

    private ObjectProperty<Duration> elapsedTime = new SimpleObjectProperty<>(Duration.ZERO);//TODO to prevent NUllPointerException Duration.ZERO is set
    private IntegerProperty numberOfPlayers = new SimpleIntegerProperty();
    private IntegerProperty remainingMines = new SimpleIntegerProperty();

    @Override
    public void playerDisconnected(String player, boolean isNewAdmin) {
        //TODO: Implement
    }

    @Override
    public void playerConnected(String player) {
        //TODO: Implement
    }

    @Override
    public void restart() {
        // TODO: Implement
    }

    @Override
    public void updateField(int[] coords, String newState) {
        //TODO: Implement
    }

    @Override
    public void finished() {
        //TODO: Implement
    }

    /**
     * The Player disconnects from the game
     */
    public void disconnect() {
        // TODO: Implement
    }

    public ObjectProperty<Duration> elapsedTimeProperty() {
        return elapsedTime;
    }

    public IntegerProperty numberOfPlayersProperty() {
        return numberOfPlayers;
    }

    public IntegerProperty remainingMinesProperty() {
        return remainingMines;
    }

}
