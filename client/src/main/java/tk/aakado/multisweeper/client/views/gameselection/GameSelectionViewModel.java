package tk.aakado.multisweeper.client.views.gameselection;

import java.util.Set;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import tk.aakado.multisweeper.client.App;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationView;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;

/**
 * View model for the game selection view.
 */
public class GameSelectionViewModel implements ViewModel, GameSelectionNotificator {

    private ListProperty<Integer> gameIds = new SimpleListProperty<>(FXCollections.emptyObservableList());

    @Override
    public void setAvailableGames(Set<Integer> gameIds) {
        this.gameIds.setAll(gameIds);
    }

    @Override
    public void gameSelected(int gameId, boolean isAdmin, boolean authRequired) {
        App.getInstance().getGameProperties().setAdmin(isAdmin);
        App.getInstance().getGameProperties().setGameId(gameId);

        // display the correct view
        if (authRequired && !isAdmin) {
            App.getInstance().changeView(AuthenticationView.class);
        } else {
            App.getInstance().changeView(ConfigurationView.class);
        }
    }

    public ListProperty<Integer> gameIdsProperty() {
        return gameIds;
    }

    /**
     * Join a game.
     * @param gameId The id of the game.
     */
    public void join(int gameId) {
        App.getInstance().getTransmitter().joinGame(gameId);
    }
}
