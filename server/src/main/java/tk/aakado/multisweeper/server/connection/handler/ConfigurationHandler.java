package tk.aakado.multisweeper.server.connection.handler;

import com.google.gson.Gson;
import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.server.game.Game;
import tk.aakado.multisweeper.server.game.GameManager;
import tk.aakado.multisweeper.server.game.Player;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.Connection;
import tk.aakado.multisweeper.shared.connection.dtos.GameConfigDTO;

import java.util.List;

/**
 * Contains all ActionHandlers concerning the configuration of the game.
 */
public class ConfigurationHandler {

    /**
     * Gets executed when admin sets password in gameConfigurationView
     * @param message The message sent by the user containing the new password
     */
    @ActionHandler(actionType = ActionType.SAVE_PASSWORD)
    public void onSavePassword(ServerMessage message) {
        // Extract password of message
        String password = message.getParams().getAsString();

        // Create objects
        GameManager gameManager = Server.getGameManager();
        Player player = gameManager.getPlayer(message.getSender())
                .orElseThrow(() -> new IllegalStateException("This connection is not associated with a player."));
        Game game = gameManager.getGameOf(player)
                .orElseThrow(() -> new IllegalStateException("The user is not currently playing a game."));

        // Set new password
        game.setPassword(player, password);
    }

    /**
     * Starts game
     */
    @ActionHandler(actionType = ActionType.START)
    public void onStart(ServerMessage message) {
        // Get objects
        Game game = Server.getGameManager().getGameOf(message.getSender()).orElseThrow(() ->
                new IllegalStateException("Connection does not belong to a game."));
        Player player = Server.getGameManager().getPlayer(message.getSender())
                .orElseThrow(() -> new IllegalStateException("Connection doesn't belong to a player."));
        GameConfigDTO gameConfigDTO = new Gson().fromJson(message.getParams(), GameConfigDTO.class);

        // Start game
        game.configure(player, gameConfigDTO);
        game.startNewRound(player);

        // Inform all clients
        Action action = new Action(ActionType.GAME_STARTED);
        List<Connection> connections = Server.getGameManager().getAllConnectionsOf(game);
        message.getConnector().sendTo(action, connections);
    }
}
