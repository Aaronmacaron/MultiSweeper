package tk.aakado.multisweeper.shared.connection.dtos;

/**
 * DTO to transfer data that is needed for authentication
 */
public class AuthenticationDTO {

    private final int gameId;
    private final String password;

    public AuthenticationDTO(int gameId, String password) {
        this.gameId = gameId;
        this.password = password;
    }

    public int getGameId() {
        return gameId;
    }

    public String getPassword() {
        return password;
    }

}
