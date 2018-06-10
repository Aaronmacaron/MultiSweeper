package tk.aakado.multisweeper.client.views.game;


public interface GameNotificator {

    /**
     * A player disconnected the game
     *
     * @param player    Players IP
     * @param isNewAdmin When the admin disconnected, an other Player becomes the admin
     */
    void playerDisconnected(String player, boolean isNewAdmin);

    /**
     * A new player joined the game
     *
     * @param player Players IP
     */
    void playerConnected(String player);

    /**
     * Admin restarts the game
     */
    void restart();

    /**
     * A player clicks a Field and every Player gets Notificated
     *
     * @param coords   Coordinates of the field
     * @param newState The new State of the field
     */
    void updateField(int[] coords, String newState);

    /**
     * The Game is finished
     */
    void finished();
}
