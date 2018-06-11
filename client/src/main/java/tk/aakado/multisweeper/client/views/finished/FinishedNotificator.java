package tk.aakado.multisweeper.client.views.finished;

public interface FinishedNotificator {

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
