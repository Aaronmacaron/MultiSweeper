package tk.aakado.multisweeper.server.game;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Game Manager manages all the games on this server.
 * This makes it possible to have multiple games on one server running simultaneously.
 */
public class GameManager {

    private final AtomicInteger idGenerator = new AtomicInteger();

    private Map<Integer, Game> allGames = new HashMap<>();

    /**
     * Creates a new game.
     * @return The id of the newly created game.
     */
    public int createGame() {
        Game game = new GameImpl();
        int id = idGenerator.incrementAndGet();
        this.allGames.put(id, game);
        return id;
    }

    /**
     * Retrieves an existing game by its id. The id has to exist.
     * @param id The id of the game.
     * @return The game.
     */
    public Game getGame(int id) {
        return allGames.get(id);
    }

    /**
     * Returns all game ids.
     * @return An unmodifiable set with all game ids.
     */
    public Set<Integer> getAllGameIds() {
        return Collections.unmodifiableSet(allGames.keySet());
    }

    /**
     * Returns all games.
     * @return An unmodifiable map with all games.
     */
    public Map<Integer, Game> getAllGames() {
        return Collections.unmodifiableMap(allGames);
    }

}
