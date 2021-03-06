package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.shared.connection.dtos.FieldDTO;
import tk.aakado.multisweeper.shared.connection.dtos.GameConfigDTO;

import java.util.List;
import java.util.Optional;

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
     * Check if the given player is part of this game.
     * @param player The player
     * @return Whether the game has a password or not.
     */
    boolean hasPlayer(Player player);

    /**
     * Configure the game.
     * This configuration will be set for this game but this does not mean it is directly in use.
     * The configuration will be in use the next time the game is started.
     * @param player The player which configures it. The player has to be an admin.
     * @param gameConfigDTO The config.
     */
    void configure(Player player, GameConfigDTO gameConfigDTO);

    /**
     * Returns the current configured values of this game.
     */
    GameConfigDTO getCurrentConfiguration();

    /**
     * Sets the password for this game.
     * @param player The player which configures the password. The player has to be an admin.
     * @param password The password to set.
     */
    void setPassword(Player player, String password);

    /**
     * Check if the game has a password configured.
     * @return Whether the game has a password or not.
     */
    boolean hasPassword();

    /**
     * Starts a new game round.
     * This creates a new minesweeper game field with the current configuration. The game is then in running state.
     * @param player The player to start the new round.
     */
    void startNewRound(Player player);

    /**
     * Tests if the game is currently running or in the setup phase.
     * @return  If the game is running.
     */
    boolean isStarted();

    /**
     * Discovers a field.
     * The game has to be running.
     * @param player The player who triggered the action.
     * @param fieldCords The coordinates of the field which should be discovered.
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

    /**
     * Retrieve the player that is the current admin of this game.
     * @return Optional containing the admin, empty if no player is in the game.
     */
    Optional<Player> getAdmin();

    /**
     * Returns a list containing the state of all fields of the current game.
     * @return
     */
    List<FieldDTO> getCurrentStateOfGame();

}