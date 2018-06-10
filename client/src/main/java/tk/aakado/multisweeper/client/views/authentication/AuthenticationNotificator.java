package tk.aakado.multisweeper.client.views.authentication;

public interface AuthenticationNotificator {

    /**
     * The authentication failed
     */
    void rejected();

    /**
     * The authentication was successful
     */
    void success();

}
