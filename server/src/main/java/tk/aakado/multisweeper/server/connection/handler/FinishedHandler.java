package tk.aakado.multisweeper.server.connection.handler;

import java.util.List;

import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerConnector;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.server.game.Game;
import tk.aakado.multisweeper.server.game.GameManager;
import tk.aakado.multisweeper.server.game.Player;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.Connection;

/**
 * Handles all requests coming from the finishedview
 */
public class FinishedHandler {
    /**
     * Restart the game with the current configuration
     * @param message The message
     */
    //TODO: onRestart method is frequently used. Outsource in a GameHandler class???
    @ActionHandler(actionType = ActionType.RESTART)
    public void onRestart(ServerMessage message) {
        Connection sender = message.getSender();
        ServerConnector connector = message.getConnector();

        GameManager gameManager = Server.getGameManager();

        // check if the player exists and get him
        if (!gameManager.getPlayer(sender).isPresent()) {
            Logger.get(this).warn("A not existing Connection sent a request: " + sender.toString());
            return;
        }

        Player player = gameManager.getPlayer(sender).get();

        // check if the game of the player exists and get it
        if (!gameManager.getGameOf(player).isPresent()) {
            Logger.get(this).warn("The requested game of the player " + player.toString() + " doesn't exist.");
            return;
        }

        Game game = gameManager.getGameOf(player).get();

        // start a new round
        game.startNewRound(player);

        // Inform all clients
        Action action = new Action(ActionType.GAME_STARTED);
        List<Connection> connections = Server.getGameManager().getAllConnectionsOf(game);
        message.getConnector().sendTo(action, connections);
    }
}