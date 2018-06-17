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

}
