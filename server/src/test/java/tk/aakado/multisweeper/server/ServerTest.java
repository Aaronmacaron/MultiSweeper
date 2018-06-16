package tk.aakado.multisweeper.server;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void portEmptyOnEmptyArgs() {
        Optional<Integer> port = Server.getPort(new String[]{});
        assertFalse(port.isPresent());
    }

    @Test
    public void portEmptyOnInvalidPort() {
        Optional<Integer> port = Server.getPort(new String[]{"0"});
        assertFalse(port.isPresent());

        port = Server.getPort(new String[]{"65536"});
        assertFalse(port.isPresent());
    }

    @Test
    public void portEmptyOnInvalidNumber() {
        Optional<Integer> port = Server.getPort(new String[]{""});
        assertFalse(port.isPresent());

        port = Server.getPort(new String[]{"Not a number"});
        assertFalse(port.isPresent());
    }

    @Test
    public void portAvailableOnValidPort() {
        String portString = "1";
        int portNum = 1;

        Optional<Integer> port = Server.getPort(new String[]{portString});
        assertTrue(port.isPresent());
        assertEquals((Integer) portNum, port.get());


        portString = "65535";
        portNum = 65535;

        port = Server.getPort(new String[]{portString});
        assertTrue(port.isPresent());
        assertEquals((Integer) portNum, port.get());
    }

}