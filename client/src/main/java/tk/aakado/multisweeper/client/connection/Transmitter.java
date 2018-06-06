package tk.aakado.multisweeper.client.connection;

/**
 * The Transmitter contains all IO/Server/Connector related methods that the Client potentially could execute.
 */
public class Transmitter {

    final private ClientConnector clientConnector;

    /**
     * Constructor
     * @param clientConnector The Connector that the Transmitter transmits through.
     */
    public Transmitter(ClientConnector clientConnector) {
        this.clientConnector = clientConnector;
    }

}
