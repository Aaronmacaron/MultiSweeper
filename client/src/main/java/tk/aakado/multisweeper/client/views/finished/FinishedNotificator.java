package tk.aakado.multisweeper.client.views.finished;

import tk.aakado.multisweeper.client.views.Notificator;

public interface FinishedNotificator extends Notificator {

    /**
     * Restart the game
     */
    void restart();

    /**
     * Reconfigure the game settings
     */
    void reconfigure();

    /**
     * Disconnect from the server
     */
    void disconnect();

}
