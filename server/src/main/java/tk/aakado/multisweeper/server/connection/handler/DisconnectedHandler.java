package tk.aakado.multisweeper.server.connection.handler;

import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.server.game.Game;
import tk.aakado.multisweeper.server.game.Player;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.Connection;
import tk.aakado.multisweeper.shared.connection.dtos.DisconnectDTO;

import java.util.Arrays;
import java.util.Optional;

public class DisconnectedHandler {

    /**
     * This method handles the disconnection of a player.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.DISCONNECT)
    public void onDisconnect(ServerMessage message) {
        Player disconnectedPlayer = Server.getGameManager().getPlayer(message.getSender())
                .orElseThrow(() -> new IllegalStateException("This connection is not associated with a player."));
        Game game = Server.getGameManager().getGameOf(disconnectedPlayer)
                .orElseThrow(() -> new IllegalStateException("Player is not playing game currently."));

        Server.getGameManager().removePlayer(message.getSender());

        // Close connection
        message.getSender().close();

        Logger.get(this).info("Client {} disconnected from server.", message.getSender().getSocket().getRemoteSocketAddress());

        // Inform every other player
        Optional<Player> optionalNewAdmin = game.getAdmin();
        if (!optionalNewAdmin.isPresent()) {
            // Do nothing if there are no players left
            Logger.get(this).info("There are no players left in game.");
            return;
        }

        Player newAdmin = optionalNewAdmin.get();

        // Send message to non-admin players
        Action nonAdminAction = new Action(
                ActionType.DISCONNECTED,
                new DisconnectDTO(disconnectedPlayer.getName(), false)
        );
        Connection adminConnection = Server.getGameManager().getConnection(newAdmin)
                .orElseThrow(() -> new IllegalStateException("The player doesn't belong to connection."));
        message.getConnector().sendExcept(nonAdminAction, Arrays.asList(adminConnection, message.getSender()));

        // Send message to new admin
        Action adminAction = new Action(
                ActionType.DISCONNECTED,
                new DisconnectDTO(disconnectedPlayer.getName(), true)
        );
        message.getConnector().sendTo(adminAction, adminConnection);
    }

}
