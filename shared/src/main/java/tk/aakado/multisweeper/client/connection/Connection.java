package tk.aakado.multisweeper.client.connection;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    public Connection(Socket socket, PrintWriter output, BufferedReader input) {
        this.socket = socket;
        this.output = output;
        this.input = input;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getOutput() {
        return output;
    }

    public BufferedReader getInput() {
        return input;
    }
}
