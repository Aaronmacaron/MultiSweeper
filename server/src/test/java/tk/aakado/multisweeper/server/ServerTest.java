package tk.aakado.multisweeper.server;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void testServerSetUp() {
        int port = 41813;
        int games = 4;
        Server.main(new String[] { String.valueOf(port), String.valueOf(games) });

        assertNotNull(Server.getConnector());

        assertNotNull(Server.getGameManager());
        assertEquals(games, Server.getGameManager().getAllGameIds().size());
    }

}
