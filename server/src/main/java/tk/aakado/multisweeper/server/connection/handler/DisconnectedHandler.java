package tk.aakado.multisweeper.server.connection.handler;

import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.server.game.Player;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;

import java.util.Optional;

public class DisconnectedHandler {

    /**
     * This method handles the disconnection of a player.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.DISCONNECT)
    public void onDisconnect(ServerMessage message) {
        Optional<Player> disconnectedPlayer = Server.getGameManager().getPlayer(message.getSender());

        if (disconnectedPlayer.isPresent()) {
            Server.getGameManager().removePlayer(message.getSender());
        }

        // Close connection
        message.getSender().close();

        Logger.get(this).info("Client {} disconnected from server.", message.getSender().getSocket().getRemoteSocketAddress());

    }

}
