package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.shared.connection.Connection;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Game Manager manages all the games on this server.
 * This makes it possible to have multiple games on one server running simultaneously.
 */
public class GameManager {

    private final AtomicInteger idGenerator = new AtomicInteger();

    private Map<Integer, Game> allGames = new HashMap<>();
    private Map<Connection, Player> allPlayers = new HashMap<>();

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
     * Creates a player which is linked to the connection.
     * @param connection The connection the player is linked to.
     */
    public Player createPlayer(Connection connection) {
        Player player = new Player(connection.getSocket().getInetAddress().toString());
        this.allPlayers.put(connection, player);
        return player;
    }

    /**
     * Retrieve the player by a connection.
     * @param connection The connection which belongs to a player.
     * @return Optional of the player, empty if the connection has no mapping to a player.
     */
    public Optional<Player> getPlayer(Connection connection) {
        return Optional.ofNullable(this.allPlayers.get(connection));
    }

    /**
     * Retrieve the current game of a player.
     * @param player The player of which to receive the game.
     * @return Optional containing the game, empty if player is in no game.
     */
    public Optional<Game> getGameOf(Player player) {
        return this.allGames.values().stream()
                .filter(game -> game.hasPlayer(player))
                .findAny();
    }

    /**
     * Retrieves an existing game by its id. The id has to exist.
     * @param id The id of the game.
     * @return The game.
     */
    public Optional<Game> getGame(int id) {
        return Optional.ofNullable(allGames.get(id));
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
