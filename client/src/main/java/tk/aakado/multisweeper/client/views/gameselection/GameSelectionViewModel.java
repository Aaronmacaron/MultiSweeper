package tk.aakado.multisweeper.client.views.gameselection;

import java.util.Set;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import tk.aakado.multisweeper.client.Client;

/**
 * View model for the game selection view.
 */
public class GameSelectionViewModel implements ViewModel, GameSelectionNotificator {

    private ListProperty<Integer> gameIds = new SimpleListProperty<>(FXCollections.emptyObservableList());

    @Override
    public void setAvailableGames(Set<Integer> gameIds) {
        this.gameIds.setAll(gameIds);
    }

    public ListProperty<Integer> gameIdsProperty() {
        return gameIds;
    }

    /**
     * Join a game.
     * @param gameId The id of the game.
     */
    public void join(int gameId) {
        Client.getInstance().getTransmitter().joinGame(gameId);
    }
}
