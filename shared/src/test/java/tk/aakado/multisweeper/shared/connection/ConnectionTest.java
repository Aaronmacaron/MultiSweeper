package tk.aakado.multisweeper.shared.connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.*;

public class ConnectionTest {

    private Connection connection;
    private File testFile;

    @Before
    public void setUp() throws IOException {
        testFile = new File("tesfile");
        testFile.createNewFile();
        connection = new Connection(new Socket(), new PrintWriter(testFile),
                new BufferedReader(new StringReader("")));
    }

    @After
    public void tearDown() {
        testFile.delete();
    }

    @Test
    public void getSocket() {
        assertNotNull(connection.getSocket());
    }

    @Test
    public void getOutput() {
        assertNotNull(connection.getInput());
    }

    @Test
    public void getInput() {
        assertNotNull(connection.getOutput());
    }
}