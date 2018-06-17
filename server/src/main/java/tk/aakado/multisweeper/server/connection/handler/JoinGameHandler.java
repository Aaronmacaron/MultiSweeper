package tk.aakado.multisweeper.server.connection.handler;

import com.google.gson.JsonArray;
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
        Connection sender = message.getSender();

        Game game = gameManager.getGame(gameId);
        Player player = gameManager.getPlayer(sender).orElse(gameManager.createPlayer(sender));

        if (!game.hasPassword()) {
            game.addPlayer(player, "");
        }

        ServerConnector connector = (ServerConnector) message.getConnector();
        connector.sendTo(new Action(ActionType.PASSWORD_NEEDED, gameId), message.getSender());
    }

    /**
     * Authenticates the password submitted by the client.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.AUTHENTICATE)
    public void onAuthenticate(ServerMessage message) {
        JsonArray jsonArray = message.getParams().getAsJsonArray();
        int gameId = jsonArray.get(0).getAsInt();
        String password = jsonArray.get(1).getAsString();

        GameManager gameManager = Server.getGameManager();
        Game game = gameManager.getGame(gameId);
        Player player = gameManager.getPlayer(message.getSender())
                .orElseThrow(() -> new IllegalStateException("Action was called by connection that has never joined a game"));

        boolean wrongPassword = !game.addPlayer(player, password);

        if (wrongPassword) {
            ServerConnector connector = (ServerConnector) message.getConnector();
            connector.sendTo(new Action(ActionType.PASSWORD_WRONG, null), message.getSender());
        }
    }

}
