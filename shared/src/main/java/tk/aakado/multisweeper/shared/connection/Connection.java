package tk.aakado.multisweeper.shared.connection;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The Connection Class is a <a href="https://en.wikipedia.org/wiki/Plain_old_Java_object">POJO</a> that represents a
 * Connection of a client to the server. It stores the socket of the connection, a Writer that can be used to send to
 * the client and a reader that enables the user to read from the client.
 */
public class Connection {

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    /**
     * Constructor
     * @param socket Socket of the Connection
     * @param output Writer which can be used to write to the client.
     * @param input Reader which can be used to read from client.
     */
    public Connection(Socket socket, PrintWriter output, BufferedReader input) {
        this.socket = socket;
        this.output = output;
        this.input = input;
    }

    /**
     * Getter for socket
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Getter for output
     * @return output
     */
    public PrintWriter getOutput() {
        return output;
    }

    /**
     * Getter for input
     * @return input
     */
    public BufferedReader getInput() {
        return input;
    }

}
