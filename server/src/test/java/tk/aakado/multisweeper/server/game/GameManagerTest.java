package tk.aakado.multisweeper.server.game;

import org.junit.Before;
import org.junit.Test;
import tk.aakado.multisweeper.shared.connection.Connection;

import java.net.Socket;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// TODO: Find out how to mock a socket as currently null pointers occur.
public class GameManagerTest {

    private static final Connection DUMMY_CONNECTION = new Connection(new Socket(), null, null);

    private GameManager gameManager;

    @Before
    public void setUp() {
        this.gameManager = new GameManager();
    }

    @Test
    public void testCreateGame() {
        int gameId = this.gameManager.createGame();

        // GameManager.getAllGameIds() has created game
        assertTrue(this.gameManager.getAllGameIds().contains(gameId));

        // GameManager.getAllGames() has created game
        assertTrue(this.gameManager.getAllGames().containsKey(gameId));

        //GameManager.getGame() returns created game
        assertTrue(this.gameManager.getGame(gameId).isPresent());
    }


//    @Test
//    public void testCreatePlayer() {
//        Player player = this.gameManager.createPlayer(DUMMY_CONNECTION);
//
//        Optional<Player> get = this.gameManager.getPlayer(DUMMY_CONNECTION);
//        assertTrue(get.isPresent());
//        assertEquals(player, get.get());
//    }

//    @Test
//    public void testGetGameOfPlayer() {
//        int gameId = this.gameManager.createGame();
//        Game game = this.gameManager.getGame(gameId).get();
//        Player player = this.gameManager.createPlayer(DUMMY_CONNECTION);
//
//        game.addPlayer(player, "");
//
//        Optional<Game> get = this.gameManager.getGameOf(player);
//        assertTrue(get.isPresent());
//        assertEquals(game, get.get());
//    }

}