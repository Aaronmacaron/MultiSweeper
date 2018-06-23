package tk.aakado.multisweeper.shared.connection.dtos;

/**
 * DTO to transfer the data when a game has been joined.
 */
public class GameJoinedInfoDTO {

    private final boolean isAdmin;
    private final boolean isRunning;

    /**
     * Constructor.
     * @param isAdmin If the player is the admin.
     * @param isRunning If the game is already running.
     */
    public GameJoinedInfoDTO(boolean isAdmin, boolean isRunning) {
        this.isAdmin = isAdmin;
        this.isRunning = isRunning;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
