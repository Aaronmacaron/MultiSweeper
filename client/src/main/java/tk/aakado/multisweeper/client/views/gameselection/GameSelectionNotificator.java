package tk.aakado.multisweeper.client.views.gameselection;

import tk.aakado.multisweeper.client.views.Notificator;

import java.util.Set;

/**
 * Notificator for the game selection view.
 */
public interface GameSelectionNotificator extends Notificator {

    /**
     * Set all current available games.
     * @param gameIds List containing all available game ids.
     */
    void setAvailableGames(Set<Integer> gameIds);

    /**
     * The selected game will be started with our without authentication.
     *
     * @param gameId       Id of the game
     * @param isAdmin      true if the player is the admin of the game
     * @param authRequired true if a authentication is required
     */
    void gameSelected(int gameId, boolean isAdmin, boolean authRequired);

}
