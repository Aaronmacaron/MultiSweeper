package tk.aakado.multisweeper.shared.connection.dtos;

import java.util.List;

/**
 * DTO to transfer the data when a game has been joined.
 */
public class GameJoinedInfoDTO {

    private final boolean isAdmin;
    private final boolean isRunning;
    private final List<String> players;

    /**
     * Constructor.
     * @param isAdmin If the player is the admin.
     * @param isRunning If the game is already running.
     */
    public GameJoinedInfoDTO(boolean isAdmin, boolean isRunning, List<String> players) {
        this.isAdmin = isAdmin;
        this.isRunning = isRunning;
        this.players = players;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public List<String> getPlayers() {
        return players;
    }
}
