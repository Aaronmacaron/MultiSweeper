package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.shared.connection.Connection;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * Removes the player which is linked to the connection.
     * If there is no mapping the method will return. If the player is still
     * part of a game he will be removed from that game.
     * @param connection The connection the player is linked to.
     */
    public void removePlayer(Connection connection) {
        if (this.allPlayers.containsKey(connection)) {
            // if no player is existent
            return;
        }
        Player player = this.allPlayers.get(connection);

        // If player is in a game, remove him
        getGameOf(player).ifPresent(game -> game.removePlayer(player));

        this.allPlayers.remove(connection);
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
     * Returns all players which are part of the given game.
     * @param game The game of which to get all players.
     * @return List of the players.
     */
    public Stream<Player> getAllPlayersOf(Game game) {
        return this.allPlayers.values().stream()
                .filter(game::hasPlayer);
    }

    /**
     * Retrieve connection by player.
     * @param player The player to get connection from.
     * @return The connection that the player belongs to.
     */
    public Optional<Connection> getConnection(Player player) {
        // Return connection of player if one exists else empty optional
        return allPlayers.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .findFirst()
                .map(Map.Entry::getKey);

    }

    /**
     * Retrieve all connections that belong to the players which are part of the given game.
     * @param game The game that has the players of which to get the connections.
     * @return List of connections.
     */
    public List<Connection> getAllConnectionsOf(Game game) {
        return getAllPlayersOf(game)
                .map(this::getConnection)
                .map(Optional::get)
                .collect(Collectors.toList());
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
