package tk.aakado.multisweeper.client.views.connection;

import tk.aakado.multisweeper.client.views.Notificator;

public interface ConnectionNotificator extends Notificator {

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
