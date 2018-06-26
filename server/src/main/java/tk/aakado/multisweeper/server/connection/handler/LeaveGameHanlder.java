package tk.aakado.multisweeper.server.connection.handler;

import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.server.game.Game;
import tk.aakado.multisweeper.server.game.GameManager;
import tk.aakado.multisweeper.server.game.Player;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;

import java.util.Optional;

public class LeaveGameHanlder {

    @ActionHandler(actionType = ActionType.LEAVE_GAME)
    public void onLeaveGame(ServerMessage message) {
        GameManager gameManager = Server.getGameManager();

        Optional<Player> player = gameManager.getPlayer(message.getSender());
        if (!player.isPresent()) {
            // if no player exists for the connection return
            return;
        }

        Optional<Game> game = gameManager.getGameOf(player.get());
        if (!game.isPresent()) {
            // if player is not in a game he cannot leave one
            return;
        }

        game.get().removePlayer(player.get());
    }

}
