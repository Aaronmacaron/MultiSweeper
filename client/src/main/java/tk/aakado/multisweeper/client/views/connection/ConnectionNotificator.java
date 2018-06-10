package tk.aakado.multisweeper.client.views.connection;

public interface ConnectionNotificator {

    /**
     * Connection was successful
     *
     * @param authRequired An authentication is required
     * @param isAdmin      Shows if the player is the admin
     */
    void connected(boolean authRequired, boolean isAdmin);

    /**
     * The connection failed.
     */
    void rejected();

}
