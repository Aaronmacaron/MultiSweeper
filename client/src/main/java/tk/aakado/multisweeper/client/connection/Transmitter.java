package tk.aakado.multisweeper.client.connection;


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

}
