package tk.aakado.multisweeper.server.connection.handler;

import com.google.gson.Gson;
import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerConnector;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.server.game.Game;
import tk.aakado.multisweeper.server.game.GameManager;
import tk.aakado.multisweeper.server.game.Player;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.Connection;
import tk.aakado.multisweeper.shared.connection.dtos.AuthenticationDTO;
import tk.aakado.multisweeper.shared.connection.dtos.GameJoinedInfoDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This handler takes care of the joining process.
 */
public class JoinGameHandler {

    /**
     * Joins the player to the game.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.JOIN_GAME)
    public void onJoinGame(ServerMessage message) {
        int gameId = message.getParams().getAsInt();

        GameManager gameManager = Server.getGameManager();
        ServerConnector connector = message.getConnector();
        Connection sender = message.getSender();

        if (! validateGameId(gameId, gameManager, connector, sender)) {
            // no valid game id
            return;
        }

        //noinspection ConstantConditions as the game id is already verified
        Game game = gameManager.getGame(gameId).get();

        // Get player of the sender or if there is none create a new one.
        Player player = gameManager.getPlayer(sender).orElse(gameManager.createPlayer(sender));

        if (!game.hasPassword()) {
            game.addPlayer(player, "");
            playerJoinedFeedback(game, player, connector, sender);
            return;
        }

        connector.sendTo(new Action(ActionType.PASSWORD_NEEDED, gameId), message.getSender());
    }

    /**
     * Authenticates the password submitted by the client.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.AUTHENTICATE)
    public void onAuthenticate(ServerMessage message) {
        AuthenticationDTO authData = new Gson().fromJson(message.getParams(), AuthenticationDTO.class);
        int gameId = authData.getGameId();

        GameManager gameManager = Server.getGameManager();
        ServerConnector connector = message.getConnector();
        Connection sender = message.getSender();

        if (! validateGameId(gameId, gameManager, connector, sender)) {
            // no valid game id
            return;
        }

        //noinspection ConstantConditions as the game id is already verified
        Game game = gameManager.getGame(gameId).get();

        // Get the player of the sender. It has to already exist due to the joining mechanism
        Player player = gameManager.getPlayer(message.getSender())
                .orElseThrow(() -> new IllegalStateException("Action was called by connection that has never joined a game"));

        boolean wrongPassword = !game.addPlayer(player, authData.getPassword());

        if (wrongPassword) {
            connector.sendTo(new Action(ActionType.PASSWORD_WRONG), message.getSender());
        } else {
            playerJoinedFeedback(game, player, connector, sender);
        }
    }

    /**
     * Checks if a game id is valid and has a corresponding existing game.
     * If the game id is invalid the client get notified.
     * @param gameId The game id to verify.
     * @param manager GameManager
     * @param connector ServerConnector
     * @param sender The client which send the action and will also be notified on no success.
     * @return If the given game id is valid.
     */
    private boolean validateGameId(int gameId, GameManager manager, ServerConnector connector, Connection sender) {
        Optional<Game> game = Server.getGameManager().getGame(gameId);
        if (game.isPresent()) {
            return true;
        }

        // Send feedback
        Action action = new Action(ActionType.WRONG_GAME_ID);
        connector.sendTo(action, sender);
        return false;
    }

    /**
     * Sends a feedback to the client that he has joined the game.
     * @param game The game to which the player joined.
     * @param player Player
     * @param connector ServerConnector
     * @param sender The client which send the action and which is sent the feedback to.
     */
    private void playerJoinedFeedback(Game game, Player player, ServerConnector connector, Connection sender) {
        //noinspection ConstantConditions as game has minimum one player now
        boolean isAdmin = player.equals(game.getAdmin().get());
        List<String> playerNames = Server.getGameManager().getAllPlayersOf(game).map(Player::toString).collect(Collectors.toList());
        GameJoinedInfoDTO gameJoinedInfoDTO = new GameJoinedInfoDTO(isAdmin, game.isStarted(), playerNames);
        connector.sendTo(new Action(ActionType.GAME_JOINED, gameJoinedInfoDTO), sender);

        // Inform other player about the new player
        Action newPlayerAction = new Action(ActionType.PLAYER_CONNECTED, player.toString());
        connector.sendExcept(newPlayerAction, sender);
    }

}
