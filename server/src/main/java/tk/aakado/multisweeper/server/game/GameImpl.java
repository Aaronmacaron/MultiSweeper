package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.shared.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameImpl implements Game {

    private static final GameConfig DEFAULT_CONFIGURATION = new GameConfig(15, 15, 20);

    private List<Player> players = new ArrayList<>();
    private PlayingField currentPlayingField;
    private GameConfig configuration;
    private String password;

    @Override
    public boolean addPlayer(Player player, String passwordToCheck) {
        if (checkPlayer(player) || !this.password.equals(passwordToCheck)) {
            return false;
        }
        this.players.add(player);
        return true;
    }

    @Override
    public void removePlayer(Player player) {
        if (checkPlayer(player)) {
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
        GameConfig runConfig = this.configuration;
        if (this.configuration == null) {
            runConfig = DEFAULT_CONFIGURATION;
        }

        int width = runConfig.getWidth();
        int height = runConfig.getHeight();
        int mines = width * height * runConfig.getMinesPercentage();

        this.currentPlayingField = new PlayingField(width, height, mines);
    }

    @Override
    public void configure(Player player, GameConfig gameConfig) {
        if (checkAdmin(player)) {
            this.configuration = gameConfig;
        }
    }

    @Override
    public void setPassword(Player player, String password) {
        if (checkAdmin(player)) {
            this.password = password;
        }
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
     * Checks if the given player is a player of this game.
     * @param player The player to check.
     * @return If the player is of this game.
     */
    private boolean checkPlayer(Player player) {
        return this.players.contains(player);
    }

    /**
     * Checks if the given player is the admin of this game.
     * @param player The player to check.
     * @return Whether the player is admin or not.
     */
    private boolean checkAdmin(Player player) {
        if (checkPlayer(player)) {
            return this.players.get(0).equals(player);
        }
        Logger.get(this).warn("Player {} tried to invoke a admin action.", player);
        return false;
    }

}
