package tk.aakado.multisweeper.server.game;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testUuidIsUnique() {
        Player playerOne = new Player("First Player");
        Player playerTwo = new Player("Second Player");

        assertNotEquals(playerOne.getUuid(), playerTwo.getUuid());
    }

    @Test
    public void equals() {
        // Players with same uuid are equal
        Player playerOne = new Player("First player");
        Player playerEqualToOne = new Player("Equal to first player", playerOne.getUuid());

        assertEquals(playerOne, playerEqualToOne);

        // Players with different uuid and same name or not equal
        String name = "Player Two";
        Player playerTwo = new Player(name);
        Player playerEqualToTwo = new Player(name);

        assertNotEquals(playerOne, playerTwo);
    }

    @Test
    public void testPlayerToString() {
        // Player as String contains its name and uuid
        String exampleName = "player123";
        String exampleUuid = "54522f38-0afc-4667-b7a1-e15b6b26afef";
        Player player = new Player(exampleName, UUID.fromString(exampleUuid));

        assertTrue(player.toString().contains(exampleName));
        assertTrue(player.toString().contains(exampleUuid));
    }
}