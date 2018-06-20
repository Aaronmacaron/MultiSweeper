package tk.aakado.multisweeper.client.views.connection;

import tk.aakado.multisweeper.client.views.Notificator;

public interface ConnectionNotificator extends Notificator {

    /**
     * Connection was successful
     *
     */
    void connected();

    /**
     * The connection failed.
     */
    void rejected();

}
