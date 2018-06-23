package tk.aakado.multisweeper.client.connection;


import javafx.scene.input.MouseButton;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.dtos.AuthenticationDTO;
import tk.aakado.multisweeper.shared.connection.dtos.ClickDTO;
import tk.aakado.multisweeper.shared.connection.dtos.GameConfigDTO;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionType;

/**
 * The Transmitter contains all IO/Server/Connector related methods that the Client potentially could execute.
 */
public class Transmitter {

    private final ClientConnector clientConnector;

    /**
     * Constructor
     * @param clientConnector The Connector that the Transmitter transmits through.
     */
    public Transmitter(ClientConnector clientConnector) {
        this.clientConnector = clientConnector;
    }

    /**
     * Disconnects client from server.
     */
    public void disconnect() {
        Action action = new Action(ActionType.DISCONNECT);
        clientConnector.send(action);
    }

    /**
     * Saves password that is used to authenticate new connecting users.
     * @param password The new password. If the string is empty, the password is removed
     */
    public void savePassword(String password) {
        Action action = new Action(ActionType.SAVE_PASSWORD, password);
        clientConnector.send(action);
    }

    /**
     * Starts game with given information
     * @param fieldWidth Width of playingField
     * @param fieldHeight Height of playingField
     * @param minesPercentage Percentage of mine Fields in playingField
     */
    public void start(int fieldWidth, int fieldHeight, double minesPercentage) {
        GameConfigDTO gameConfigDTO = new GameConfigDTO(fieldWidth, fieldHeight, minesPercentage);
        Action action = new Action(ActionType.START, gameConfigDTO);
        clientConnector.send(action);
    }

    /**
     * Sends a click action to server
     * @param x X coordinate on playing field
     * @param y Y coordinate on playing field
     * @param mouseButton The mouse button that was clicked
     */
    public void click(int x, int y, MouseButton mouseButton) {
        String clickType;
        if (mouseButton == MouseButton.SECONDARY) {
            clickType = "right";
        } else {
            clickType = "left";
        }
        ClickDTO clickDTO = new ClickDTO(x, y, clickType);

        Action action = new Action(ActionType.CLICK, clickDTO);
        clientConnector.send(action);
    }

    /**
     * Submits password to server and thus tries to authenticate user to server
     * @param password The password that the user submits
     */
    public void authenticate(int gameId, String password) {
        AuthenticationDTO authData = new AuthenticationDTO(gameId, password);
        Action action = new Action(ActionType.AUTHENTICATE, authData);
        clientConnector.send(action);
    }

    /**
     * Restarts the game with the same settings
     */
    public void restart() {
        Action action = new Action(ActionType.RESTART);
        clientConnector.send(action);
    }

    /**
     * Join a game.
     * @param gameId The game to join.
     */
    public void joinGame(int gameId) {
        Action action = new Action(ActionType.JOIN_GAME, gameId);
        clientConnector.send(action);
    }

}
