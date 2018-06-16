package tk.aakado.multisweeper.client.connection;


import javafx.scene.input.MouseButton;
import tk.aakado.multisweeper.shared.connection.dtos.ClickDTO;
import tk.aakado.multisweeper.shared.connection.dtos.StartInfoDTO;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionType;

/**
 * The Transmitter contains all IO/Server/Connector related methods that the Client potentially could execute.
 */
public class Transmitter {

    final private ClientConnector clientConnector;

    /**
     * Constructor
     */
    public Transmitter(ClientConnector clientConnector) {
        this.clientConnector = clientConnector;
    }

    /**
     * Connects client to server.
     */
    public void connect() {
        Action action = new Action(ActionType.CONNECT, new Object());
        clientConnector.send(action);
    }

    /**
     * Disconnects client from server.
     */
    public void disconnect() {
        Action action = new Action(ActionType.DISCONNECT, new Object());
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
     * @param startInfoDTO The info to start the game
     */
    public void start(StartInfoDTO startInfoDTO) {
        Action action = new Action(ActionType.START, startInfoDTO);
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

}
