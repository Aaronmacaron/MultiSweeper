package tk.aakado.multisweeper.client.connection;


import tk.aakado.multisweeper.client.connection.params.StartInfo;
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
     * @param startInfo The info to start the game
     */
    public void start(StartInfo startInfo) {
        Action action = new Action(ActionType.START, startInfo);
        clientConnector.send(action);
    }

}
