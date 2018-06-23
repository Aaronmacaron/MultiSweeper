package tk.aakado.multisweeper.client.views.authentication;

import tk.aakado.multisweeper.client.views.Notificator;

public interface AuthenticationNotificator extends Notificator {

    /**
     * Sets the game id of the game that the user currently tried to join but needs authentication.
     * @param gameId The id of the game.
     */
    void setGameId(int gameId);

    /**
     * The authentication failed
     */
    void rejected();

    /**
     * The authentication was successful
     */
    void success();

}
