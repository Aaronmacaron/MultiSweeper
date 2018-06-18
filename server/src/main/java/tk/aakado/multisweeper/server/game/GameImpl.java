package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.dtos.GameConfigDTO;

import java.util.ArrayList;
import java.util.List;

public class GameImpl implements Game {

    private static final GameConfigDTO DEFAULT_CONFIGURATION = new GameConfigDTO(15, 15, 20);

    private List<Player> players = new ArrayList<>();
    private PlayingField currentPlayingField;
    private GameConfigDTO configuration;
    private String password = ""; // Default no password

    @Override
    public boolean addPlayer(Player player, String passwordToCheck) {
        if (hasPlayer(player) || !this.password.equals(passwordToCheck)) {
            return false;
        }
        this.players.add(player);
        return true;
    }

    @Override
    public boolean hasPlayer(Player player) {
        return this.players.contains(player);
    }

    @Override
    public void removePlayer(Player player) {
        if (hasPlayer(player)) {
            this.players.remove(player);
        }
    }

    @Override
    public void discoverField(Player player, FieldCords fieldCords) {
        requiredGameRunning();
        this.currentPlayingField.discoverField(fieldCords, player);
    }

    @Override
    public void flagField(Player player, FieldCords fieldCords) {
        requiredGameRunning();
        this.currentPlayingField.flagField(fieldCords, player);
    }

    @Override
    public void startNewRound(Player player) {
        GameConfigDTO runConfig = this.configuration;
        if (this.configuration == null) {
            runConfig = DEFAULT_CONFIGURATION;
        }

        int width = runConfig.getWidth();
        int height = runConfig.getHeight();
        double mines = width * height * runConfig.getMinesPercentage();

        this.currentPlayingField = new PlayingField(
                width,
                height,
                (int) Math.round(mines) // Convert from double to int
        );
    }

    @Override
    public void configure(Player player, GameConfigDTO gameConfigDTO) {
        if (checkAdmin(player)) {
            this.configuration = gameConfigDTO;
        }
    }

    @Override
    public void setPassword(Player player, String password) {
        if (checkAdmin(player)) {
            this.password = password;
        }
    }

    @Override
    public boolean hasPassword() {
        return this.password.isEmpty();
    }

    /**
     * Tests if the game is currently running or in the setup phase.
     * @return  If the game is running.
     */
    private boolean isStarted() {
        return this.currentPlayingField != null;
    }

    /**
     * Checks if the game ist running. If not an {@link IllegalStateException} is raised.
     */
    private void requiredGameRunning() {
        if (! isStarted()) {
            throw new IllegalStateException("The game is not running.");
        }
    }

    /**
     * Checks if the given player is the admin of this game.
     * @param player The player to check.
     * @return Whether the player is admin or not.
     */
    private boolean checkAdmin(Player player) {
        if (hasPlayer(player)) {
            return this.players.get(0).equals(player);
        }
        Logger.get(this).warn("Player {} tried to invoke a admin action.", player);
        return false;
    }

}
