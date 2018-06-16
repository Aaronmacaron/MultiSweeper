package tk.aakado.multisweeper.client.views.configuration;

import tk.aakado.multisweeper.client.views.Notificator;

public interface ConfigurationNotificator extends Notificator {

    /**
     * A player disconnected
     *
     * @param player    Players IP
     * @param isNewAdmin If the Admin disconnected, an other Player is the new Admin
     */
    void playerDisconnected(String player, boolean isNewAdmin);

    /**
     * A new Player connected
     *
     * @param player Player IP
     */
    void playerConnected(String player);

    /**
     * Admin started the game
     */
    void gameStarted();

}
