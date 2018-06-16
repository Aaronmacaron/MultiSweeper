package tk.aakado.multisweeper.client.views.authentication;

import tk.aakado.multisweeper.client.views.Notificator;

public interface AuthenticationNotificator extends Notificator {

    /**
     * The authentication failed
     */
    void rejected();

    /**
     * The authentication was successful
     */
    void success();

}
