package tk.aakado.multisweeper.client.views.game;


import tk.aakado.multisweeper.client.views.Notificator;
import tk.aakado.multisweeper.shared.game.FieldState;

import java.util.Optional;

public interface GameNotificator extends Notificator  {

    /**
     * Admin configures the field.
     *
     * @param width x-axis size
     * @param height y-axis size
     * @param numberOfMines the total amount of mines
     */
    void configureField(int width, int height, int numberOfMines);

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
     * @param won If the players won the game
     */
    void finished(boolean won);
}
