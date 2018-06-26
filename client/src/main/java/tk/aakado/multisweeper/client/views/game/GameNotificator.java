package tk.aakado.multisweeper.client.views.game;


import tk.aakado.multisweeper.client.views.Notificator;
import tk.aakado.multisweeper.shared.game.FieldState;

import java.util.Optional;

public interface GameNotificator extends Notificator  {

    /**
     * A player disconnected the game
     *
     * @param player     Players IP
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
     * Admin configures the field.
     *
     * @param width x-axis size
     * @param height y-axis size
     */
    void configureField(int width, int height);

    /**
     * A player clicks a Field and every Player gets Notificated
     * @param x The x-coordinate of the field.
     * @param y The y-coordinate of the field.
     * @param newState The new FieldState of the field
     * @param value The value of the field.
     */
    void updateField(int x, int y, FieldState newState, Optional<Integer> value);

    /**
     * The Game is finished
     */
    void finished();
}
