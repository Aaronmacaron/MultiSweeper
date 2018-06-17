package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.shared.connection.dtos.GameConfigDTO;

/**
 * Interface of a game.
 */
public interface Game {

    /**
     * Adds a player to this game if the password is correct.
     * @param player The player to add.
     * @param passwordToCheck To password of the game, empty if none.
     * @return If the player has been added.
     */
    boolean addPlayer(Player player, String passwordToCheck);

    /**
     * Removes a player from this game.
     * @param player The player to remove.
     */
    void removePlayer(Player player);

    /**
     * Configure the game.
     * This configuration will be set for this game but this does not mean it is directly in use.
     * The configuration will be in use the next time the game is started.
     * @param player The player which configures it. The player has to be an admin.
     * @param gameConfigDTO The config.
     */
    void configure(Player player, GameConfigDTO gameConfigDTO);

    /**
     * Sets the password for this game.
     * @param player The player which configures the password. The player has to be an admin.
     * @param password The password to set.
     */
    void setPassword(Player player, String password);

    /**
     * Starts a new game round.
     * This creates a new minesweeper game field with the current configuration. The game is then in running state.
     * @param player The player to start the new round.
     */
    void startNewRound(Player player);

    /**
     * Discovers a field.
     * The game has to be running.
     * @param player
     * @param fieldCords
     */
    void discoverField(Player player, FieldCords fieldCords);


    /**
     * Flags/unflags a field.
     * If the field is already flagged it gets unflagged and vice-versa.
     * The game has to be running.
     * @param player The player who triggered the action.
     * @param fieldCords The coordinates of the field which should be flagged/unflagged.
     */
    void flagField(Player player, FieldCords fieldCords);

}