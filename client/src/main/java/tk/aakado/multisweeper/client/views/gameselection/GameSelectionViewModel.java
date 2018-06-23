package tk.aakado.multisweeper.client.views.gameselection;

import java.util.Set;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationView;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;

/**
 * View model for the game selection view.
 */
public class GameSelectionViewModel implements ViewModel, GameSelectionNotificator {

    private ListProperty<Integer> gameIds = new SimpleListProperty<>(FXCollections.observableArrayList());

    /**
     * Sets game Ids to list
     * @param gameIds List containing all available game ids.
     */
    @Override
    public void setAvailableGames(Set<Integer> gameIds) {
        this.gameIds.setAll(gameIds);
    }

    @Override
    public void gameSelected(int gameId, boolean isAdmin, boolean authRequired) {
        Client.getInstance().getGameProperties().setAdmin(isAdmin);
        Client.getInstance().getGameProperties().setGameId(gameId);

        // display the correct view
        if (authRequired && !isAdmin) {
            Client.getInstance().changeView(AuthenticationView.class);
        } else {
            Client.getInstance().changeView(ConfigurationView.class);
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
        Client.getInstance().getTransmitter().joinGame(gameId);
    }

    /**
     * The players cancels the selection
     */
    public void cancel() {
        Client.getInstance().changeView(ConnectionView.class);
    }
}
