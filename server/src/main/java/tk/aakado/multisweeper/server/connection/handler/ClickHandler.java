package tk.aakado.multisweeper.server.connection.handler;

import com.google.gson.Gson;
import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.server.game.FieldCords;
import tk.aakado.multisweeper.server.game.Game;
import tk.aakado.multisweeper.server.game.GameManager;
import tk.aakado.multisweeper.server.game.Player;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.dtos.ClickDTO;

import java.util.Optional;

/**
 * Handles all click events from the client.
 */
public class ClickHandler {

    /**
     * When a client clicks a field, this action handler is responsible.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.CLICK)
    public void onClick(ServerMessage message) {
        ClickDTO clickDTO = new Gson().fromJson(message.getParams(), ClickDTO.class);
        GameManager gameManager = Server.getGameManager();

        Optional<Player> player = gameManager.getPlayer(message.getSender());
        // check if player is existent
        if (! player.isPresent()) {
            return;
        }

        Optional<Game> game = gameManager.getGameOf(player.get());
        // check if player is in a game
        if (! game.isPresent()) {
            return;
        }

        handleClick(clickDTO, player.get(), game.get());
    }

    /**
     * Handles a click from a client.
     * @param clickDTO The information about the click.
     * @param player The player that triggered the action.
     * @param game The game.
     */
    private void handleClick(ClickDTO clickDTO, Player player, Game game) {
        FieldCords fieldCords = new FieldCords(clickDTO.getX(), clickDTO.getY());
        if ("left".equalsIgnoreCase(clickDTO.getClickType())) {
            // on left click discover field
            game.discoverField(player, fieldCords);
        } else {
            // on right click flag field
            game.flagField(player, fieldCords);
        }
    }

}
